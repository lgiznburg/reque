package ru.rsmu.reque.editor;

import ru.rsmu.reque.dao.ReceptionCampaignDao;
import ru.rsmu.reque.model.registration.ReceptionCampaign;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;

/**
 * @author leonid.
 */

public class ReceptionCampaignEditor extends PropertyEditorSupport implements PropertyEditor {

    private ReceptionCampaignDao campaignDao;

    public ReceptionCampaignEditor( ReceptionCampaignDao campaignDao ) {
        this.campaignDao = campaignDao;
    }

    @Override
    public String getAsText() {
        Object campaign = getValue();
        return (campaign != null && campaign instanceof ReceptionCampaign) ? Long.toString( ((ReceptionCampaign) campaign).getId() ) : "0";
    }

    @Override
    public void setAsText( String text ) throws IllegalArgumentException {
        if ( text == null || text.trim().isEmpty() ) {
            setValue( null );
            return;
        }
        ReceptionCampaign campaign = null;
        try {
            Long id = Long.parseLong( text );
            campaign = campaignDao.findCampaign( id );
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException( e );
        }
        setValue( campaign );
    }

}
