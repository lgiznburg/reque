package ru.rsmu.reque.service;

import com.google.gson.*;
import org.apache.commons.mail.EmailException;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.ToolManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.rsmu.reque.model.registration.Appointment;
import ru.rsmu.reque.service.megaphone.MegaphoneMessage;
import ru.rsmu.reque.service.megaphone.MegaphoneResponse;
import ru.rsmu.reque.utils.RuDateHelper;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author leonid.
 */
@Component
public class MegaphoneService {

    private static final Logger log = LoggerFactory.getLogger( MegaphoneService.class );

    @Autowired
    private MegaphoneApiConnector megaphoneApiConnector;

    @Autowired
    private VelocityEngine velocityEngine;

    private Gson gson;

    public MegaphoneService() {
        gson = new GsonBuilder()
                .setFieldNamingPolicy( FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES )
                //.setDateFormat( "MM/dd/yyyy" )
                .create();
    }

    public String sendSms( Appointment appointment ) {
        MegaphoneMessage message = new MegaphoneMessage();
        try {
            message.setTo( Long.parseLong( appointment.getUser().getPhoneNumber().replaceAll( "\\D", "" ) ) );
        } catch (NumberFormatException e) {
            return null;  // cant send sms, wrong phone
        }

        Calendar appointmentDate = Calendar.getInstance();
        appointmentDate.setTime( appointment.getScheduledDate() );
        Calendar time = Calendar.getInstance();
        time.setTime( appointment.getScheduledTime() );
        appointmentDate.set( Calendar.HOUR_OF_DAY, time.get( Calendar.HOUR_OF_DAY ) );
        appointmentDate.set( Calendar.MINUTE, time.get( Calendar.MINUTE ) );

        SimpleDateFormat format = new SimpleDateFormat( "dd.MM.yyyy в HH:mm", new Locale( "ru" ) );
        Map<String,Object> emailContext = new HashMap<>();
        emailContext.put( "fullDate", format.format( appointmentDate.getTime() )  );

        try {
            message.setMessage( generateMessage( EmailType.SMS_REMINDER.getFileName(), emailContext ) );
        } catch (EmailException e) {
            log.error( "Can't create SMS message" );
            return null;
        }

        String body = gson.toJson( message );
        JsonObject jsonResponse = megaphoneApiConnector.sendSms( body );
        if ( jsonResponse == null ) {
            log.warn( String.format( "User: %s, phone: %s, SMS was not send with no API response", appointment.getUser().getUsername(), appointment.getUser().getPhoneNumber() ) );
            return null;
        }
        MegaphoneResponse response = gson.fromJson( jsonResponse.get( "result" ), MegaphoneResponse.class );
        if ( response.getStatus().getCode() != 0 ) {
            log.warn( String.format( "SMS was not sent. Code: %d, %s", response.getStatus().getCode(), response.getStatus().getDescription() ) );
        }
        return response.getMsgId();
    }


    private String generateMessage(final String template, final Map model) throws EmailException {

        try {
            final StringWriter message = new StringWriter();
            final ToolManager toolManager = new ToolManager();
            final ToolContext toolContext = toolManager.createContext();
            final VelocityContext context = new VelocityContext(model, toolContext);

            velocityEngine.mergeTemplate( template, "UTF-8", context, message );
            return message.getBuffer().toString();

        } catch (Exception e) {
            throw new EmailException("Can't create email body", e);
        }
    }
}
