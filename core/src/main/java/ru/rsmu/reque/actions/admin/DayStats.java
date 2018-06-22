package ru.rsmu.reque.actions.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.rsmu.reque.actions.BaseController;
import ru.rsmu.reque.dao.AppointmentDao;
import ru.rsmu.reque.editor.DateTimeEditor;
import ru.rsmu.reque.model.registration.Appointment;
import ru.rsmu.reque.model.system.ApplianceType;

import java.util.*;

/**
 * @author leonid.
 */
@Controller
@RequestMapping("/admin/DayStats.htm")
public class DayStats extends BaseController {

    @Autowired
    private AppointmentDao appointmentDao;

    public DayStats() {
        setTitle( "Данные на день" );
        setContent( "/WEB-INF/pages/blocks/admin/DayStats.jsp" );
    }
    @ModelAttribute("types")
    public List<ApplianceType> getTypes() {
        return appointmentDao.findAllApplianceTypes();
    }

    @ModelAttribute("testDate")
    public Date getSelectedDate( @RequestParam(value = "testDate", required = false) Date date ) {
        if ( date == null ) {
            return getNextDay();
        }
        return date;
    }

    private Date getNextDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add( Calendar.DAY_OF_YEAR, 1 );

        return calendar.getTime();
    }

    @ModelAttribute("stats")
    public Map<Date, Map<ApplianceType, Long>> getStatistics( @RequestParam(value = "testDate", required = false) Date date ) {
        if ( date == null ) {
            date = getNextDay();
        }
        return appointmentDao.findStatistics( date );
    }

    @ModelAttribute("appointments")
    public List<Appointment> getDayAppointments( @RequestParam(value = "testDate", required = false) Date date ) {
        if ( date == null ) {
            date = getNextDay();
        }
        return appointmentDao.findDayAppointments( date );
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD, RequestMethod.POST})
    public String showStats( ModelMap model ) {
        return buildModel( model );
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD, RequestMethod.POST}, params = "print")
    public String showStatsForPrint( ModelMap model ) {
        buildModel( model );
        return "/blocks/admin/PrintDayStats";
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD, RequestMethod.POST}, params = "ticket")
    public String showStatsForTicketPrint( ModelMap model ) {
        buildModel( model );
        return "/blocks/admin/PrintDayTickets";
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD, RequestMethod.POST}, params = {"ticket","appointment"})
    public String showCreatedForTicketPrint( ModelMap model,
                                             @RequestParam("appointment") Long appointmentId) {
        Appointment appointment = appointmentDao.findAppointment( appointmentId );
        List<Appointment> appointments = new ArrayList<>();
        appointments.add( appointment );
        model.addAttribute( "appointments", appointments );
        buildModel( model );
        return "/blocks/admin/PrintDayTickets";
    }


    @InitBinder
    public void initBinder( WebDataBinder binder ) {
        binder.registerCustomEditor( Date.class, new DateTimeEditor() );
    }
}
