package ru.rsmu.reque.service;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.ToolManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.rsmu.reque.model.system.StoredPropertyName;
import ru.rsmu.reque.model.system.User;

import java.io.StringWriter;
import java.util.Calendar;
import java.util.Map;
import java.util.Objects;

/**
 * @author leonid.
 */
@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger( EmailService.class );

    // parameters for using with commons email

    @Value("${emailService.hostName:127.0.0.1}")
    private String hostName;
    @Value("${emailService.hostLogin:login}")
    private String hostLogin;
    @Value("${emailService.hostPassword:password}")
    private String hostPassword;
    @Value("${emailService.hostPort:0}")
    private int hostPort = 0;
    @Value("${emailService.hostSslPort:}")
    private String hostSslPort = null;
    @Value("${emailService.useSsl:false}")
    private boolean useSsl = false;
    @Value("${emailService.useTls:false}")
    private boolean useTls = false;


    private boolean debug = false;

    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    private StoredPropertyService propertyService;

    public void sendEmail( User user, EmailType emailType, Map<String,Object> model ) {
        try {

            HtmlEmail email = createHtmlEmail( emailType, model );
            email.addTo( user.getUsername(), String.format( "%s %s", user.getFirstName(), user.getLastName() ) );
            email.send();

        } catch (EmailException e) {
            log.error( "Email wasn't sent", e );
        }
    }

    public void sendEmail( String to, EmailType emailType, Map<String,Object> model ) {
        try {

            HtmlEmail email = createHtmlEmail( emailType, model );
            email.addTo( to );
            email.send();

        } catch (EmailException e) {
            log.error( "Email wasn't sent", e );
        }
    }


    private HtmlEmail createHtmlEmail( EmailType emailType, Map<String,Object> model) throws EmailException {
        final HtmlEmail htmlEmail = new HtmlEmail();
        htmlEmail.setHostName(hostName);
        if ( StringUtils.isNotBlank( hostLogin ) && StringUtils.isNotBlank(hostPassword)) {
            htmlEmail.setAuthentication(hostLogin, hostPassword);
        }

        if (hostPort > 0)
            htmlEmail.setSmtpPort(hostPort);

        htmlEmail.setStartTLSEnabled(useTls);
        htmlEmail.setSSLOnConnect( useSsl );
        htmlEmail.setSslSmtpPort( hostSslPort );

        htmlEmail.setFrom( propertyService.getProperty( StoredPropertyName.EMAIL_FROM_ADDRESS ),
                propertyService.getProperty( StoredPropertyName.EMAIL_FROM_SIGNATURE ),
                "UTF-8" );

        htmlEmail.setSubject( emailType.getSubject() );
        htmlEmail.setHtmlMsg( generateEmailMessage( emailType.getFileName(), model ) );

        return htmlEmail;
    }

    private String generateEmailMessage(final String template, final Map model) throws EmailException {

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
