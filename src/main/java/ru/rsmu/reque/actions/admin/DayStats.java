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

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @InitBinder
    public void initBinder( WebDataBinder binder ) {
        binder.registerCustomEditor( Date.class, new DateTimeEditor() );
    }
}
