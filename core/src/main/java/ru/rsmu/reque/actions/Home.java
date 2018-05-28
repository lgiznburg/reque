package ru.rsmu.reque.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.rsmu.reque.dao.AppointmentDao;
import ru.rsmu.reque.model.registration.Appointment;
import ru.rsmu.reque.utils.RuDateHelper;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author leonid.
 */
@Controller
@RequestMapping(value = "/home.htm")
public class Home extends BaseController {

    @Autowired
    private AppointmentDao appointmentDao;

    public Home() {
        setTitle( "Предварительная запись" );
        setContent( "/WEB-INF/pages/blocks/Home.jsp" );
    }

    @RequestMapping( method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD})
    public String showHome( ModelMap model,
                            @ModelAttribute("appointment") Appointment appointment ) {
        if ( appointment != null ) {
            SimpleDateFormat format = new SimpleDateFormat( "EEEE, dd MMMM", new Locale( "ru" ) );
            model.addAttribute( "fullDate", RuDateHelper.genetiveDay( format.format( appointment.getScheduledDate() ) ) );
        }
        return buildModel( model );
    }
}
