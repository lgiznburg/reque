package ru.rsmu.reque.model.system;

/**
 * @author leonid.
 */
public enum StoredPropertyName {
    SCHEDULE_START_SERVICE_DATE("Schedule", "1.Start service date", "1-01-2018", StoredPropertyType.DATE),
    SCHEDULE_END_SERVICE_DATE("Schedule", "2.End service date", "1-09-2018", StoredPropertyType.DATE),
    SCHEDULE_START_TIME("Schedule", "3.Start time", "10:00", StoredPropertyType.TIME),
    SCHEDULE_END_TIME("Schedule", "4.End time", "16:00", StoredPropertyType.TIME),
    SCHEDULE_SERVICE_INTERVAL("Schedule", "5.Service interval (min)", "15", StoredPropertyType.INTEGER),
    SCHEDULE_SERVICE_AMOUNT("Schedule", "6.How many customers could be served in the interval", "30", StoredPropertyType.INTEGER);

    private String groupName;
    private String name;
    private String defaultValue;
    private StoredPropertyType type;

    StoredPropertyName( String groupName, String name, String defaultValue, StoredPropertyType type ) {
        this.groupName = groupName;
        this.name = name;
        this.defaultValue = defaultValue;
        this.type = type;
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
}
