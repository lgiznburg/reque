package ru.rsmu.reque.actions.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.rsmu.reque.actions.BaseController;
import ru.rsmu.reque.dao.AppointmentDao;
import ru.rsmu.reque.dao.ReceptionCampaignDao;
import ru.rsmu.reque.editor.ApplianceTypeEditor;
import ru.rsmu.reque.editor.DateTimeEditor;
import ru.rsmu.reque.editor.ReceptionCampaignEditor;
import ru.rsmu.reque.model.registration.Appointment;
import ru.rsmu.reque.model.registration.ReceptionCampaign;
import ru.rsmu.reque.model.system.ApplianceType;
import ru.rsmu.reque.model.system.StoredPropertyName;
import ru.rsmu.reque.model.system.User;
import ru.rsmu.reque.service.EmailService;
import ru.rsmu.reque.service.EmailType;
import ru.rsmu.reque.service.StoredPropertyService;

import javax.validation.Valid;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author leonid.
 */
@Controller
@RequestMapping(value = "/CreateAppointment.htm")
public class CreateAppointment extends BaseController {

    @Autowired
    private StoredPropertyService propertyService;

    @Autowired
    private AppointmentDao appointmentDao;

    @Autowired
    private ReceptionCampaignDao campaignDao;

    @Autowired
    private EmailService emailService;

    public CreateAppointment() {
        setTitle( "Определить дату визита" );
        setContent( "/WEB-INF/pages/blocks/CreateAppointment.jsp" );
    }

    @ModelAttribute("availableDates")
    public List<Map<String, Object>> getAvailableDates( ModelMap model,
                                                        @RequestParam(value = "campaign", required = false) ReceptionCampaign campaign ) {
        Appointment appointment = (Appointment) model.get( "appointment" );
        if ( appointment == null ) {
            appointment = getAppointment( campaign );
        }
        if ( appointment == null || appointment.getCampaign() == null ) {
            return Collections.emptyList();
        }

        Date startDate = appointment.getCampaign().getStartDate();
        Date endDate = appointment.getCampaign().getEndDate();

        Calendar calendar = Calendar.getInstance();
        calendar.set( Calendar.HOUR_OF_DAY, 0 );
        calendar.set( Calendar.MINUTE, 0 );
        calendar.set( Calendar.SECOND, 0 );
        calendar.set( Calendar.MILLISECOND, 0 );

        Date startTime = propertyService.getPropertyAsDate( StoredPropertyName.SCHEDULE_START_TIME );
        Date endTime = propertyService.getPropertyAsDate( StoredPropertyName.SCHEDULE_END_TIME );
        long granularity = propertyService.getPropertyAsLong( StoredPropertyName.SCHEDULE_SERVICE_INTERVAL );
        int amount = propertyService.getPropertyAsInt( StoredPropertyName.SCHEDULE_SERVICE_AMOUNT );
        long dayAmount = (endTime.getTime() - startTime.getTime()) / (60000 * granularity) * amount;

        Map<Date,Long> countByDates = appointmentDao.findDates( startDate, endDate );

        if ( startDate.after( calendar.getTime() ) ) {
            calendar.setTime( startDate );
        }
        List<Map<String,Object>> dates = new ArrayList<>();
        while ( !calendar.getTime().after( endDate ) ) {
            if ( calendar.get( Calendar.DAY_OF_WEEK ) == Calendar.SUNDAY ) {
                Map<String,Object> map = new HashMap<>();
                map.put( "date", calendar.getTime() );
                map.put( "message", "Weekend" );
                dates.add( map );
            }
            if ( countByDates.get( calendar.getTime() ) != null && countByDates.get( calendar.getTime() ) >= dayAmount ) {
                Map<String,Object> map = new HashMap<>();
                map.put( "date", calendar.getTime() );
                map.put( "message", "Not available" );
                dates.add( map );
            }
            calendar.add( Calendar.DAY_OF_YEAR, 1 );
        }
        return dates;
    }

    @ModelAttribute("startDate")
    public Date getStartDate( ModelMap model,
                              @RequestParam(value = "campaign", required = false) ReceptionCampaign campaign ) {
        Calendar calendar = Calendar.getInstance();
        calendar.add( Calendar.DAY_OF_YEAR, 1 );

        Appointment appointment = (Appointment) model.get( "appointment" );
        if ( appointment == null ) {
            appointment = getAppointment( campaign );
        }
        if ( appointment == null || appointment.getCampaign() == null ) {
            return calendar.getTime();
        }
        Date startDate = appointment.getCampaign().getStartDate();
        return startDate.before( calendar.getTime() ) ? calendar.getTime() : startDate;
    }

    @ModelAttribute("endDate")
    public Date getEndDate( ModelMap model,
                            @RequestParam(value = "campaign", required = false) ReceptionCampaign campaign ) {
        Appointment appointment = (Appointment) model.get( "appointment" );
        if ( appointment == null ) {
            appointment = getAppointment( campaign );
        }
        if ( appointment == null || appointment.getCampaign() == null ) {
            return new Date();
        }
        return appointment.getCampaign().getEndDate();
    }

    @ModelAttribute("granularity")
    public int getGranularity() {
        return propertyService.getPropertyAsInt( StoredPropertyName.SCHEDULE_SERVICE_INTERVAL );
    }

    @ModelAttribute("startTime")
    public Date getStartTime() {
        return propertyService.getPropertyAsDate( StoredPropertyName.SCHEDULE_START_TIME );
    }

    @ModelAttribute("endTime")
    public Date getEndTime() {
        return propertyService.getPropertyAsDate( StoredPropertyName.SCHEDULE_END_TIME );
    }

    @ModelAttribute("appointment")
    public Appointment getAppointment( @RequestParam(value = "campaign", required = false) ReceptionCampaign campaign ) {
        User user = getUser();
        Date dateNow = new Date();
        Appointment appointment = appointmentDao.findAppointment( user, dateNow );
        if ( appointment == null ) {
            appointment = new Appointment();
            appointment.setCampaign( campaign );
        }

        return appointment;
    }

    @ModelAttribute("applianceTypes")
    public List<ApplianceType> getApplianceTypes( ModelMap model,
                                                  @RequestParam(value = "campaign", required = false) ReceptionCampaign campaign ) {
        Appointment appointment = (Appointment) model.get( "appointment" );
        if ( appointment == null ) {
            appointment = getAppointment( campaign );
        }
        if ( appointment == null || appointment.getCampaign() == null ) {
            return Collections.emptyList();
        }
        return appointment.getCampaign().getAvailableTypes();
    }

    @InitBinder
    public void initBinder( WebDataBinder binder ) {
        binder.registerCustomEditor( Date.class, new DateTimeEditor() );
        binder.registerCustomEditor( Time.class, new DateTimeEditor( true ) );
        binder.registerCustomEditor( ApplianceType.class, new ApplianceTypeEditor( appointmentDao ) );
        binder.registerCustomEditor( ReceptionCampaign.class, new ReceptionCampaignEditor( campaignDao ) );
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD})
    public String showPage( ModelMap model,
                            @ModelAttribute("appointment") Appointment appointment) {
        if ( appointment.getCampaign() == null ) {
            return "redirect:/SelectCampaign.htm";
        }
        return buildModel( model );
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveAppointment( ModelMap model,
                                   @ModelAttribute("appointment") @Valid Appointment appointment,
                                   BindingResult errors ) {
        if ( errors.hasErrors() ) {
            return buildModel( model );
        }
        User user = getUser();
        appointment.setUser( user );
        appointmentDao.saveEntity( appointment );

        Calendar appointmentDate = Calendar.getInstance();
        appointmentDate.setTime( appointment.getScheduledDate() );
        Calendar time = Calendar.getInstance();
        time.setTime( appointment.getScheduledTime() );
        appointmentDate.set( Calendar.HOUR_OF_DAY, time.get( Calendar.HOUR_OF_DAY ) );
        appointmentDate.set( Calendar.MINUTE, time.get( Calendar.MINUTE ) );

        SimpleDateFormat format = new SimpleDateFormat( "EEEE, dd MMMM в HH:mm", new Locale("ru") );
        Map<String,Object> emailContext = new HashMap<>();
        emailContext.put( "fullDate", format.format( appointmentDate.getTime() ) );
        emailContext.put( "user", user );
        emailContext.put( "appointment", appointment );

        emailService.sendEmail( user, EmailType.REMINDER, emailContext );

        return "redirect:/home.htm";
    }

    @RequestMapping(method = RequestMethod.POST, params = "delete")
    public String deleteAppointment( ModelMap model,
                                     @ModelAttribute("appointment") Appointment appointment ) {

        appointmentDao.deleteEntity( appointment );
        return "redirect:/home.htm";
    }
}
