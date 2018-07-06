package ru.rsmu.reque.actions.admin;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rsmu.reque.actions.BaseController;
import ru.rsmu.reque.service.MegaphoneApiConnector;
import ru.rsmu.reque.service.megaphone.MegaphoneMessage;
import ru.rsmu.reque.service.megaphone.MegaphoneResponse;

/**
 * @author leonid.
 */
@Controller
@RequestMapping("/admin/TestMegaphone.htm")
public class TestMegaphone extends BaseController {

    @Autowired
    private MegaphoneApiConnector connector;

    public TestMegaphone() {
        setTitle( "Test SMS" );
        setContent( "/WEB-INF/pages/blocks/admin/TestMegaphone.jsp" );
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showForm( ModelMap modelMap ) {
        return buildModel( modelMap );
    }

    @RequestMapping(method = RequestMethod.POST)
    public String sendSms( @RequestParam(value = "phoneNumber") String phoneNumber,
                           @RequestParam(value = "text") String text,
                           ModelMap modelMap ) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        if ( phoneNumber != null && !phoneNumber.isEmpty() ) {
            try {
                Phonenumber.PhoneNumber phone = phoneNumberUtil.parse( phoneNumber, "RU" );
                if ( !phoneNumberUtil.isValidNumber( phone ) ) {
                    modelMap.addAttribute( "phoneNumberMsg", "Wrong format" );
                    return buildModel( modelMap );
                }
            } catch (NumberParseException e) {
                modelMap.addAttribute( "phoneNumberMsg", "Wrong format" );
                return buildModel( modelMap );
            }
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
        if ( jsonResponse == null ) {
            modelMap.addAttribute( "jsonResponse", "Ошибка соединения с сервером" );
            return buildModel( modelMap );
        }
        MegaphoneResponse response = gson.fromJson( jsonResponse.get( "result" ), MegaphoneResponse.class );
        modelMap.addAttribute( "megaphoneResponse", response );
        modelMap.addAttribute( "jsonResponse", jsonResponse.toString() );
        return buildModel( modelMap );
    }
}
