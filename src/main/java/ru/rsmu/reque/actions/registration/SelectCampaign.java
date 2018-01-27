package ru.rsmu.reque.actions.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.rsmu.reque.actions.BaseController;
import ru.rsmu.reque.dao.ReceptionCampaignDao;
import ru.rsmu.reque.model.registration.ReceptionCampaign;

import java.util.Date;
import java.util.List;

/**
 * @author leonid.
 */
@Controller
@RequestMapping(value = "/SelectCampaign.htm")
public class SelectCampaign extends BaseController {

    @Autowired
    private ReceptionCampaignDao campaignDao;

    public SelectCampaign() {
        setTitle( "Выбрать цель обращения" );
        setContent( "/WEB-INF/pages/blocks/SelectCampaign.jsp" );
    }

    @ModelAttribute("campaigns")
    public List<ReceptionCampaign> getCampaigns() {
        return campaignDao.findAvailableCampaigns( new Date() );
    }

    @RequestMapping( method = {RequestMethod.GET, RequestMethod.HEAD} )
    public String showPage( ModelMap model,
                            @ModelAttribute("campaigns") List<ReceptionCampaign> campaigns) {
        if ( campaigns.size() == 1 ) {
            return String .format( "redirect:/CreateAppointment.htm?campaign=%d", campaigns.get( 0 ).getId() );
        }
        return buildModel( model );
    }
}
