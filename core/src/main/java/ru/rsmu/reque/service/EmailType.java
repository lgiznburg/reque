package ru.rsmu.reque.service;

/**
 * @author leonid.
 */
public enum EmailType {
    REGISTRATION_CONFIRM( "/emails/RegistrationConfirm.vm","Подтверждение регистрации"),
    REMINDER( "/emails/Reminder.vm","Подтверждение предварительной записи"),
    PASSWORD_REMINDER( "/emails/PasswordRemind.vm","Восстановление пароля"),
    REGISTRATION_BY_ADMIN("/emails/RegistrationByAdmin.vm","Подтверждение регистрации сотрудником приемной комиссии"),
    SMS_REMINDER("/emails/SmsReminder.vm","SMS"),
    EDIT_BY_ADMIN_REMINDER( "/emails/AdminEditReminder.vm","Изменение предварительной записи");

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
