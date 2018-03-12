package ru.rsmu.reque.service;

/**
 * @author leonid.
 */
public enum EmailType {
    REGISTRATION_CONFIRM("/emails/RegistrationConfirm.vm","Подтверждение регистрации"),
    REMINDER("/emails/Reminder.vm","Подтверждение предварительной записи"),
    PASSWORD_REMINDER("/emails/PasswordRemind.vm","");

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
