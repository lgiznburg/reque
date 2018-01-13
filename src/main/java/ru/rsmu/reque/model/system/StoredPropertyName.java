package ru.rsmu.reque.model.system;

/**
 * @author leonid.
 */
public enum StoredPropertyName {
    ;

    private String groupName;
    private String name;
    private String defaultValue;
    private StoredPropertyType type;

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
