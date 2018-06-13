package ru.rsmu.reque.editor;

import ru.rsmu.reque.dao.AppointmentDao;
import ru.rsmu.reque.model.system.DocumentName;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;

/**
 * @author leonid.
 */
public class DocumentNameEditor extends PropertyEditorSupport implements PropertyEditor {

    private AppointmentDao appointmentDao;

    public DocumentNameEditor( AppointmentDao appointmentDao ) {
        super();
        this.appointmentDao = appointmentDao;
    }

    @Override
    public String getAsText() {
        Object doc = getValue();
        return (doc != null && doc instanceof DocumentName) ? Long.toString( ((DocumentName)doc).getId() ) : "0";
    }

    @Override
    public void setAsText( String text ) throws IllegalArgumentException {
        if (text == null || text.trim().isEmpty()) {
            setValue(null);
            return;
        }
        DocumentName doc = null;
        try {
            Long id = Long.parseLong( text );
            doc = appointmentDao.findDocument( id );
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException( e );
        }
        setValue( doc );
    }
}
