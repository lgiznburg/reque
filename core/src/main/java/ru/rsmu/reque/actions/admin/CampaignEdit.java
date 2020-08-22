package ru.rsmu.reque.actions.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.rsmu.reque.actions.BaseController;
import ru.rsmu.reque.dao.AppointmentDao;
import ru.rsmu.reque.dao.ReceptionCampaignDao;
import ru.rsmu.reque.editor.ApplianceTypeEditor;
import ru.rsmu.reque.editor.DateTimeEditor;
import ru.rsmu.reque.model.registration.CampaignReserveDay;
import ru.rsmu.reque.model.registration.ReceptionCampaign;
import ru.rsmu.reque.model.system.ApplianceType;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author leonid.
 */
@Controller
@RequestMapping(value = "/admin/CampaignEdit.htm")
public class CampaignEdit extends BaseController {

    @Autowired
    private ReceptionCampaignDao campaignDao;

    @Autowired
    private AppointmentDao appointmentDao;

    public CampaignEdit() {
        setTitle( "Редактирование кампании" );
        setContent( "/WEB-INF/pages/blocks/admin/CampaignEdit.jsp" );
    }

    @ModelAttribute("campaign")
    public ReceptionCampaign getCampaign( @RequestParam(value = "id", required = false) Long id ) {
        ReceptionCampaign campaign = null;
        if ( id != null ) {
            campaign = campaignDao.findCampaign( id );
        }
        if ( campaign == null ) {
            campaign = new ReceptionCampaign();
        }
        for ( int i = 0; i < 5; i++ ) {
            campaign.getReserveDays().add( new CampaignReserveDay() );
        }
        return campaign;
    }

    @ModelAttribute("applianceTypes")
    public List<ApplianceType> getApplianceTypes() {
        return appointmentDao.findAllApplianceTypes();
    }


    @RequestMapping( method = {RequestMethod.GET, RequestMethod.HEAD})
    public String showPage( ModelMap model ) {
        return buildModel( model );
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveCampaign( ModelMap model,
                                @ModelAttribute("campaign") @Valid ReceptionCampaign campaign,
                                BindingResult errors ) {
        if ( errors.hasErrors() ) {
            return buildModel( model );
        }
        List<CampaignReserveDay> toDelete = new ArrayList<>();
        for ( Iterator<CampaignReserveDay> dayIterator = campaign.getReserveDays().iterator(); dayIterator.hasNext(); ) {
            CampaignReserveDay day = dayIterator.next();
            if ( day.getReserveDay() != null ) {
                day.setCampaign( campaign );
                if ( !day.getWorkingTime().matches( CampaignReserveDay.WORKING_TIME_REGEXP ) ) {
                    day.setWorkingTime( null ); // error message ??
                }
            }
            else {
                dayIterator.remove();
                if ( day.getId() > 0 ) {
                    day.setReserveDay( new Date() );  // to prevent exception, day should not be null
                    toDelete.add( day );
                }
            }
        }
        campaignDao.saveEntity( campaign );
        for( CampaignReserveDay day : toDelete ) {
            campaignDao.deleteEntity( day );
        }
        for ( CampaignReserveDay day : campaign.getReserveDays() ) {
            campaignDao.saveEntity( day );
        }
        //return "redirect:/admin/Campaigns.htm";
        model.addAttribute( "campaign", getCampaign( campaign.getId() ) );
        return buildModel( model );
    }

    @InitBinder
    public void initBinder( WebDataBinder binder ) {
        binder.registerCustomEditor( Date.class, new DateTimeEditor() );
        binder.registerCustomEditor( ApplianceType.class, new ApplianceTypeEditor( appointmentDao ) );
    }
}
