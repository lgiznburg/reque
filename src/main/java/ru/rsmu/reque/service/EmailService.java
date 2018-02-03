package ru.rsmu.reque.service;

import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author leonid.
 */
@Service
public class EmailService {

    // parameters for using with commons email

    @Value("${emailService.hostName:127.0.0.1}")
    private String hostName;
    @Value("${emailService.hostLogin}")
    private String hostLogin;
    @Value("${emailService.hostPassword}")
    private String hostPassword;
    private int hostPort = 0;
    private String hostSslPort = null;
    private boolean useSsl = false;
    private boolean useTls = false;

    @Value("${emailService.fromEmail:}")
    private String fromEmail;

    private boolean debug = false;

}
