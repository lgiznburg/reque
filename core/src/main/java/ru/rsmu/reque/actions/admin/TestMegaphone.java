package ru.rsmu.reque.actions.admin;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import ru.rsmu.reque.actions.BaseController;
import ru.rsmu.reque.dao.AppointmentDao;
import ru.rsmu.reque.model.system.QueuedSms;
import ru.rsmu.reque.service.MegaphoneApiConnector;
import ru.rsmu.reque.service.megaphone.MegaphoneMessage;
import ru.rsmu.reque.service.megaphone.MegaphoneResponse;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author leonid.
 */
@Controller
@RequestMapping("/admin/SendSms.htm")
public class TestMegaphone extends BaseController {

    @Autowired
    private MegaphoneApiConnector connector;

    @Autowired
    private AppointmentDao appointmentDao;

    private static final String RESULT_MSG = "%s - %s";

    public TestMegaphone() {
        setTitle( "Test SMS" );
        setContent( "/WEB-INF/pages/blocks/admin/TestMegaphone.jsp" );
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showForm( ModelMap modelMap ) {
        return buildModel( modelMap );
    }

    @RequestMapping(method = RequestMethod.POST)
    public String sendSms( @RequestParam(value = "phoneNumber") String numbersInput,
                           @RequestParam(value = "text") String text,
                           ModelMap modelMap ) {
        String[] numbers = numbersInput.split( "[,;\n]" );
        List<String> results = new ArrayList<>();
        if ( numbers.length == 0 ) {
            modelMap.addAttribute( "jsonResponse", "No numbers submitted" );
            modelMap.addAttribute( "resultMsgs", results );
            return buildModel( modelMap );
        }
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        for( String phoneNumber : numbers ) {
            if ( phoneNumber != null && !phoneNumber.isEmpty() ) {
                try {
                    Phonenumber.PhoneNumber phone = phoneNumberUtil.parse( phoneNumber, "RU" );
                    if ( !phoneNumberUtil.isValidNumber( phone ) ) {
                        //modelMap.addAttribute( "phoneNumberMsg", "Wrong format" );
                        //return buildModel( modelMap );
                        results.add( String.format( RESULT_MSG, phoneNumber, "Ошибочный номер" ) );
                        continue;
                    }
                    phoneNumber = phoneNumberUtil.format( phone, PhoneNumberUtil.PhoneNumberFormat.E164 );
                } catch (NumberParseException e) {
                    //modelMap.addAttribute( "phoneNumberMsg", "Wrong format" );
                    //return buildModel( modelMap );
                    results.add( String.format( RESULT_MSG, phoneNumber, "Неправильный формат номера" ) );
                    continue;
                }

                MegaphoneMessage message = new MegaphoneMessage();
                message.setTo( Long.parseLong( phoneNumber.replaceAll( "\\D", "" ) ) );
                message.setMessage( text );

                Gson gson = new GsonBuilder()
                        .setFieldNamingPolicy( FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES )
                        //.setDateFormat( "MM/dd/yyyy" )
                        .create();

                String body = gson.toJson( message );
                JsonObject jsonResponse = connector.sendSms( body );
                try {
                    TimeUnit.SECONDS.sleep( 1 ); // add timeout  to not exceed Megaphone max load
                } catch (InterruptedException e) {
                    //
                }
                if ( jsonResponse == null ) {
                    //modelMap.addAttribute( "jsonResponse", "Ошибка соединения с сервером" );
                    //return buildModel( modelMap );
                    results.add( String.format( RESULT_MSG, phoneNumber, "Ошибка соединения с сервером" ) );
                    continue;
                }
                MegaphoneResponse response = gson.fromJson( jsonResponse.get( "result" ), MegaphoneResponse.class );
                //modelMap.addAttribute( "megaphoneResponse", response );
                //modelMap.addAttribute( "jsonResponse", jsonResponse.toString() );
                results.add( String.format( RESULT_MSG, phoneNumber, response.toString() ) );
            }

        }
        modelMap.addAttribute( "resultMsgs", results );
        return buildModel( modelMap );
    }

    @RequestMapping(method = RequestMethod.POST, params = "phonesListFile")
    public String sendSms( HttpServletRequest request, @RequestParam(value = "text") String text,
                           ModelMap modelMap ) {

        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        int created = 0;
        List<String> results = new ArrayList<>();
        try {
            if ( request instanceof MultipartHttpServletRequest ) {
                MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
                if ( multipart.getFileMap().containsKey( "phonesListFile" ) && !multipart.getFile( "phonesListFile" ).isEmpty() ) {
                    MultipartFile file = multipart.getFile( "phonesListFile" );
                    if ( file.getOriginalFilename().matches( ".*\\.xls" ) ) {
                        POIFSFileSystem fs = new POIFSFileSystem( file.getInputStream() );
                        HSSFWorkbook wb = new HSSFWorkbook( fs );

                        HSSFSheet sheet = wb.getSheetAt( 0 );  // main page

                        Iterator<HSSFRow> rIterator = sheet.rowIterator();
                        while ( rIterator.hasNext() ) {
                            HSSFRow row = rIterator.next();
                            String phoneNumber = getCellValue( row, (short) 1 );
                            if ( phoneNumber == null || !phoneNumber.matches( "[ +()\\-\\d]*" )) continue;
                            try {
                                Phonenumber.PhoneNumber phone = phoneNumberUtil.parse( phoneNumber, "RU" );
                                if ( !phoneNumberUtil.isValidNumber( phone ) ) {
                                    results.add( String.format( RESULT_MSG, phoneNumber, "Ошибочный номер" ) );
                                    continue;
                                }
                                phoneNumber = phoneNumberUtil.format( phone, PhoneNumberUtil.PhoneNumberFormat.E164 );
                            } catch (NumberParseException e) {
                                results.add( String.format( RESULT_MSG, phoneNumber, "Неправильный формат номера" ) );
                                continue;
                            }
                            QueuedSms sms = new QueuedSms();
                            sms.setPhone( phoneNumber );
                            sms.setText( text );
                            appointmentDao.saveEntity( sms );
                            created++;
                        }

                    }
                }
            }
        } catch ( IOException e ) {
            //logger.error( "Can't upload information from Excel file", e );
        }

        results.add( String.format( "Всего создано: %d", created ) );
        modelMap.addAttribute( "resultMsgs", results );
        return buildModel( modelMap );

    }

    private String getCellValue( HSSFRow row, short cellN ) {
        HSSFCell cell = row.getCell( cellN );
        if ( cell != null ) {
            String value;
            switch ( cell.getCellType() ) {
                case HSSFCell.CELL_TYPE_STRING:
                    value = cell.getRichStringCellValue().getString().trim();
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC:
                    value = Long.toString( Math.round( cell.getNumericCellValue() ) );
                    break;
                default:
                    return null;
            }
            return value;
        }
        return null;
    }

}
