package ru.rsmu.reque.editor;

import ru.rsmu.reque.dao.AppointmentDao;
import ru.rsmu.reque.model.system.ApplianceType;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;

/**
 * @author leonid.
 */
public class ApplianceTypeEditor extends PropertyEditorSupport implements PropertyEditor {

    private AppointmentDao appointmentDao;

    public ApplianceTypeEditor( AppointmentDao appointmentDao ) {
        super();
        this.appointmentDao = appointmentDao;
    }

    @Override
    public String getAsText() {
        Object type = getValue();
        return (type != null && type instanceof ApplianceType) ? Long.toString( ((ApplianceType) type).getId() ) : "0" ;
    }

    @Override
    public void setAsText( String text ) throws IllegalArgumentException {
        if (text == null || text.trim().isEmpty()) {
            setValue(null);
            return;
        }
        ApplianceType type = null;
        try {
            Long id = Long.parseLong( text );
            type = appointmentDao.findApplianceType( id );
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException( e );
        }
        setValue( type );
    }
}
