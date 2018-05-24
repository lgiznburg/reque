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
import ru.rsmu.reque.model.registration.ReceptionCampaign;
import ru.rsmu.reque.model.system.ApplianceType;

import javax.validation.Valid;
import java.util.Date;
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
        return campaign != null ? campaign : new ReceptionCampaign();
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
        campaignDao.saveEntity( campaign );
        return "redirect:/admin/Campaigns.htm";
    }

    @InitBinder
    public void initBinder( WebDataBinder binder ) {
        binder.registerCustomEditor( Date.class, new DateTimeEditor() );
        binder.registerCustomEditor( ApplianceType.class, new ApplianceTypeEditor( appointmentDao ) );
    }
}
