package ru.rsmu.reque.actions.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.rsmu.reque.actions.BaseController;
import ru.rsmu.reque.dao.ReceptionCampaignDao;
import ru.rsmu.reque.model.registration.ReceptionCampaign;

import java.util.List;

/**
 * @author leonid.
 */
@Controller
@RequestMapping(value = "/admin/Campaigns.htm")
public class Campaigns extends BaseController {

    @Autowired
    private ReceptionCampaignDao campaignDao;

    public Campaigns() {
        setTitle( "Список приемных кампаний" );
        setContent( "/WEB-INF/pages/blocks/admin/Campaigns.jsp" );
    }

    @ModelAttribute("campaigns")
    public List<ReceptionCampaign> getCampaigns() {
        return campaignDao.findAll();
    }

    @RequestMapping( method = {RequestMethod.GET, RequestMethod.HEAD})
    public String showPage( ModelMap model ) {
        return buildModel( model );
    }
}
