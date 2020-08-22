package ru.rsmu.reque.job;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.rsmu.reque.dao.AppointmentDao;
import ru.rsmu.reque.model.registration.Appointment;
import ru.rsmu.reque.model.system.QueuedSms;
import ru.rsmu.reque.model.system.StoredPropertyName;
import ru.rsmu.reque.service.MegaphoneApiConnector;
import ru.rsmu.reque.service.MegaphoneService;
import ru.rsmu.reque.service.StoredPropertyService;
import ru.rsmu.reque.service.megaphone.MegaphoneMessage;
import ru.rsmu.reque.service.megaphone.MegaphoneResponse;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author leonid.
 */
@Component
public class SendSmsFromQueue {

    @Autowired
    private AppointmentDao appointmentDao;

    @Autowired
    private MegaphoneApiConnector connector;

    @Autowired
    private StoredPropertyService propertyService;


    public void sendReminders() {

        int activation = propertyService.getPropertyAsInt( StoredPropertyName.MEGAPHONE_SMS_ENABLED );
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

        if ( activation > 0 ) {
            int balancer = 0;
            for ( QueuedSms smsToSend : appointmentDao.findSmsToSend() ) {
                if ( smsToSend.getPhone() != null && !smsToSend.getPhone().isEmpty() ) {
                    try {
                        Phonenumber.PhoneNumber phone = phoneNumberUtil.parse( smsToSend.getPhone(), "RU" );
                        if ( !phoneNumberUtil.isValidNumber( phone ) ) {
                            //modelMap.addAttribute( "phoneNumberMsg", "Wrong format" );
                            //return buildModel( modelMap );
                            smsToSend.setError( true );
                            appointmentDao.saveEntity( smsToSend );
                            continue;
                        }
                        String phoneNumber = phoneNumberUtil.format( phone, PhoneNumberUtil.PhoneNumberFormat.E164 );
                    } catch (NumberParseException e) {
                        //modelMap.addAttribute( "phoneNumberMsg", "Wrong format" );
                        //return buildModel( modelMap );
                        smsToSend.setError( true );
                        appointmentDao.saveEntity( smsToSend );
                        continue;
                    }

                    MegaphoneMessage message = new MegaphoneMessage();
                    message.setTo( Long.parseLong( smsToSend.getPhone().replaceAll( "\\D", "" ) ) );
                    message.setMessage( smsToSend.getText() );

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
                        smsToSend.setSend( true );
                        smsToSend.setError( true );  //"Ошибка соединения с сервером"
                        appointmentDao.saveEntity( smsToSend );
                        continue;
                    }
                    MegaphoneResponse response = gson.fromJson( jsonResponse.get( "result" ), MegaphoneResponse.class );
                    smsToSend.setSend( true );
                    smsToSend.setError( !response.getStatus().getDescription().contains( "ok" ) );  //"not OK"
                    appointmentDao.saveEntity( smsToSend );
                }
            }
        }
    }
}
