package ru.rsmu.reque.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.rsmu.reque.dao.AppointmentDao;
import ru.rsmu.reque.dao.SystemDao;
import ru.rsmu.reque.model.registration.Appointment;
import ru.rsmu.reque.model.system.HomePageAnnounce;
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

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private SystemDao systemDao;

    public Home() {
        setTitle( "Предварительная запись" );
        setContent( "/WEB-INF/pages/blocks/Home.jsp" );
    }

    @ModelAttribute("announce")
    public String getAnnounce() {
        Locale locale = LocaleContextHolder.getLocale();
        HomePageAnnounce announce = systemDao.findAnnounce( locale.getLanguage() );
        return  announce != null ? announce.getText() : "";
    }

    @RequestMapping( method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD})
    public String showHome( ModelMap model,
                            @ModelAttribute("appointment") Appointment appointment ) {
        Locale locale = LocaleContextHolder.getLocale();
        if ( appointment != null ) {
            SimpleDateFormat format = new SimpleDateFormat( "EEEE, dd MMMM", locale );
            SimpleDateFormat time = new SimpleDateFormat( "HH:mm" );
            String awaiting_msg = messageSource.getMessage( "home.awaiting",
                    new Object[] {format.format( appointment.getScheduledDate() ), time.format( appointment.getScheduledTime() )}, locale );
            model.addAttribute( "fullDate", RuDateHelper.genetiveDay( format.format( appointment.getScheduledDate() ) ) );
            model.addAttribute( "awaiting_msg", RuDateHelper.genetiveDay( awaiting_msg ) );
        }
        return buildModel( model );
    }
}
