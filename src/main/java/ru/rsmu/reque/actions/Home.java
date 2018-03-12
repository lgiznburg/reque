package ru.rsmu.reque.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.rsmu.reque.dao.AppointmentDao;
import ru.rsmu.reque.model.registration.Appointment;
import ru.rsmu.reque.model.system.User;

import java.util.Date;

/**
 * @author leonid.
 */
@Controller
@RequestMapping(value = "/home.htm")
public class Home extends BaseController {

    @Autowired
    private AppointmentDao appointmentDao;

    public Home() {
        setTitle( "Home" );
        setContent( "/WEB-INF/pages/blocks/Home.jsp" );
    }

    @RequestMapping( method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD})
    public String showHome( ModelMap model ) {
        return buildModel( model );
    }
}
