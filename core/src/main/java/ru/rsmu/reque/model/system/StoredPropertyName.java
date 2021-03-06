package ru.rsmu.reque.model.system;

/**
 * @author leonid.
 */
public enum StoredPropertyName {
    SCHEDULE_START_SERVICE_DATE("Schedule", "1.Start service date", "1-01-2018", StoredPropertyType.DATE),
    SCHEDULE_END_SERVICE_DATE("Schedule", "2.End service date", "1-09-2018", StoredPropertyType.DATE),
    SCHEDULE_START_TIME("Schedule", "3.Start time", "10:00", StoredPropertyType.TIME),
    SCHEDULE_END_TIME("Schedule", "4.End time", "16:00", StoredPropertyType.TIME),
    SCHEDULE_WORKING_ON_SATURDAY("Schedule", "5.Is Saturday working day", "1",StoredPropertyType.INTEGER),
    SCHEDULE_SATURDAY_END_TIME("Schedule", "6.End time on Saturday", "14:00", StoredPropertyType.TIME),
    SCHEDULE_SERVICE_INTERVAL("Schedule", "7.Service interval (min)", "15", StoredPropertyType.INTEGER, false),
    SCHEDULE_SERVICE_AMOUNT("Schedule", "8.How many customers could be served in the interval", "30", StoredPropertyType.INTEGER),
    SCHEDULE_ADMIN_ADD_AMOUNT("Schedule", "9.Дополнительные места для записи на сегодня", "3", StoredPropertyType.INTEGER),
    EMAIL_FROM_ADDRESS("Email","Обратный адрес для email", "prk@rsmu.ru", StoredPropertyType.STRING),
    EMAIL_FROM_SIGNATURE("Email","Название обратного адреса для email", "Приемная комиссия РНИМУ им.Пирогова", StoredPropertyType.STRING),
    MEGAPHONE_SERVICE_URL("Megaphone SMS", "1.Адрес URI для отправки SMS", "https://a2p-api.megalabs.ru", StoredPropertyType.STRING),
    MEGAPHONE_SERVICE_METHOD("Megaphone SMS", "2.Метод для отправки SMS", "sms/v1/sms", StoredPropertyType.STRING),
    MEGAPHONE_LOGIN("Megaphone SMS", "3.Логин", "MSK_prgvmn", StoredPropertyType.STRING),
    MEGAPHONE_PASSWORD("Megaphone SMS", "4.Пароль", "S3U4DJ1d", StoredPropertyType.STRING),
    MEGAPHONE_SMS_ENABLED("Megaphone SMS", "5.Включено", "1", StoredPropertyType.INTEGER)
    ;

    private String groupName;
    private String name;
    private String defaultValue;
    private StoredPropertyType type;
    private boolean editable = true;

    StoredPropertyName( String groupName, String name, String defaultValue, StoredPropertyType type ) {
        this.groupName = groupName;
        this.name = name;
        this.defaultValue = defaultValue;
        this.type = type;
    }

    StoredPropertyName( String groupName, String name, String defaultValue, StoredPropertyType type, boolean editable ) {
        this.groupName = groupName;
        this.name = name;
        this.defaultValue = defaultValue;
        this.type = type;
        this.editable = editable;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getName() {
        return name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public StoredPropertyType getType() {
        return type;
    }

    public boolean isEditable() {
        return editable;
    }
}
