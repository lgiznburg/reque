package ru.rsmu.reque.actions.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.rsmu.reque.actions.BaseController;
import ru.rsmu.reque.dao.ReceptionCampaignDao;
import ru.rsmu.reque.model.registration.ReceptionCampaign;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author leonid.
 */
@Controller
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

    @RequestMapping(value = "/SelectCampaign.htm", method = {RequestMethod.GET, RequestMethod.HEAD} )
    public String showPage( ModelMap model,
                            @ModelAttribute("campaigns") List<ReceptionCampaign> campaigns) {
        if ( campaigns.size() == 1 ) {
            return String .format( "redirect:/CreateAppointment.htm?campaign=%d", campaigns.get( 0 ).getId() );
        }
        return buildModel( model );
    }

    @RequestMapping(value = "/admin/SelectCampaign.htm", method = {RequestMethod.GET, RequestMethod.HEAD} )
    public String showPageServiceman( ModelMap model,
                            @ModelAttribute("campaigns") List<ReceptionCampaign> campaigns) {
        Calendar date = Calendar.getInstance();
        date.add( Calendar.DAY_OF_YEAR, -1 );  // it should find campaign on its last day
        campaigns = campaignDao.findAvailableCampaigns( date.getTime() );
        model.addAttribute( "campaigns", campaigns );
        if ( campaigns.size() == 1 ) {
            return String .format( "redirect:/admin/CreateTodayAppointment.htm?campaign=%d", campaigns.get( 0 ).getId() );
        }
        String view = buildModel( model );
        model.addAttribute( CONTENT, "/WEB-INF/pages/blocks/admin/SelectTodayCampaign.jsp" );
        return view;
    }

}
