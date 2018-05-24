package ru.rsmu.reque.service;

/**
 * @author leonid.
 */
public enum EmailType {
    REGISTRATION_CONFIRM( "/web-reque/src/main/resources/emails/RegistrationConfirm.vm","Подтверждение регистрации"),
    REMINDER( "/web-reque/src/main/resources/emails/Reminder.vm","Подтверждение предварительной записи"),
    PASSWORD_REMINDER( "/web-reque/src/main/resources/emails/PasswordRemind.vm","");

    private String fileName;
    private String subject;

    EmailType( String fileName, String subject ) {
        this.fileName = fileName;
        this.subject = subject;
    }

    public String getFileName() {
        return fileName;
    }

    public String getSubject() {
        return subject;
    }
}
