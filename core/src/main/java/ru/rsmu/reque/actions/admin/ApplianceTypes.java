package ru.rsmu.reque.actions.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.rsmu.reque.actions.BaseController;
import ru.rsmu.reque.dao.AppointmentDao;
import ru.rsmu.reque.model.system.ApplianceType;

import java.util.List;

/**
 * @author leonid.
 */
@Controller
@RequestMapping(value = "/admin/ApplianceTypes.htm")
public class ApplianceTypes extends BaseController {

    @Autowired
    private AppointmentDao appointmentDao;

    public ApplianceTypes() {
        setTitle( "List of types" );
        setContent( "/WEB-INF/pages/blocks/admin/ApplianceTypes.jsp" );
    }

    @ModelAttribute("applianceTypes")
    public List<ApplianceType> getTypes() {
        return appointmentDao.findAllApplianceTypes();
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD})
    public String showPage( ModelMap model ) {
        return buildModel( model );
    }

}
