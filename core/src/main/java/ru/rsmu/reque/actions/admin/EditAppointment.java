package ru.rsmu.reque.actions.admin;

import javafx.scene.input.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.rsmu.reque.actions.BaseController;
import ru.rsmu.reque.dao.AppointmentDao;
import ru.rsmu.reque.dao.IUserDao;
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
import ru.rsmu.reque.utils.RuDateHelper;
import ru.rsmu.reque.validators.AppointmentValidator;

import javax.validation.Valid;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author leonid.
 */
@Controller
@RequestMapping(value = "/admin/EditAppointment.htm")
public class EditAppointment extends BaseController {
    @Autowired
    private StoredPropertyService propertyService;

    @Autowired
    private AppointmentDao appointmentDao;

    @Autowired
    private ReceptionCampaignDao campaignDao;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AppointmentValidator appointmentValidator;

    @Autowired
    private IUserDao userDao;

    public EditAppointment() {
        setTitle( "Изменить запись" );
        setContent( "/WEB-INF/pages/blocks/admin/EditAppointment.jsp" );
    }


    @ModelAttribute("appointmentToCreate")
    public Appointment getAppointment( @RequestParam(value = "id") Long id ) {
        if ( id != null ) {
            return appointmentDao.findAppointment( id );
        }
        return null;
    }

    @ModelAttribute("availableDates")
    public List<Map<String, Object>> getAvailableDates( ModelMap model,
                                                        @RequestParam(value = "id") Long id ) {

        Appointment appointment = (Appointment) model.get( "appointmentToCreate" );
        if ( appointment == null && id != null ) {
            appointment = getAppointment( id );
        }
        if ( appointment == null || appointment.getCampaign() == null ) {
            return Collections.emptyList();
        }
        ReceptionCampaign campaign = appointment.getCampaign();
        Date startDate = campaign.getStartDate();
        Date endDate = campaign.getEndDate();

        Calendar calendar = Calendar.getInstance();
        calendar.set( Calendar.HOUR_OF_DAY, 0 );
        calendar.set( Calendar.MINUTE, 0 );
        calendar.set( Calendar.SECOND, 0 );
        calendar.set( Calendar.MILLISECOND, 0 );

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
            else if ( calendar.get( Calendar.DAY_OF_WEEK ) == Calendar.SATURDAY ) {
                if ( propertyService.getPropertyAsInt( StoredPropertyName.SCHEDULE_WORKING_ON_SATURDAY ) <= 0 ) {
                    Map<String,Object> map = new HashMap<>();
                    map.put( "date", calendar.getTime() );
                    map.put( "message", "Weekend" );
                    dates.add( map );
                }
            }
            calendar.add( Calendar.DAY_OF_YEAR, 1 );
        }
        return dates;
    }

    @ModelAttribute("startDate")
    public Date getStartDate( ModelMap model,
                              @RequestParam(value = "id") Long id  ) {
        Calendar calendar = Calendar.getInstance();
        calendar.add( Calendar.DAY_OF_YEAR, 1 );

        Appointment appointment = (Appointment) model.get( "appointmentToCreate" );
        if ( appointment == null && id != null ) {
            appointment = getAppointment( id );
        }
        if ( appointment == null || appointment.getCampaign() == null ) {
            return calendar.getTime();
        }
        Date startDate = appointment.getCampaign().getStartDate();
        return startDate.before( calendar.getTime() ) ? calendar.getTime() : startDate;
    }

    @ModelAttribute("endDate")
    public Date getEndDate( ModelMap model,
                            @RequestParam(value = "id") Long id  ) {
        Appointment appointment = (Appointment) model.get( "appointmentToCreate" );
        if ( appointment == null && id != null ) {
            appointment = getAppointment( id );
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
        Calendar lastTime = Calendar.getInstance();
        lastTime.setTime( propertyService.getPropertyAsDate( StoredPropertyName.SCHEDULE_START_TIME ) );
        Date endTime = propertyService.getPropertyAsDate( StoredPropertyName.SCHEDULE_END_TIME );
        int interval = propertyService.getPropertyAsInt( StoredPropertyName.SCHEDULE_SERVICE_INTERVAL );
        while ( endTime.after( lastTime.getTime() ) ) {
            lastTime.add( Calendar.MINUTE, interval );
        }
        lastTime.add( Calendar.MINUTE, - interval );
        return lastTime.getTime();
    }

    @ModelAttribute("applianceTypes")
    public List<ApplianceType> getApplianceTypes( ModelMap model,
                                                  @RequestParam(value = "id") Long id ) {
        Appointment appointment = (Appointment) model.get( "appointmentToCreate" );
        if ( appointment == null ) {
            appointment = getAppointment( id );
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
        if ( binder.getTarget() instanceof Appointment ) {
            binder.setValidator( appointmentValidator );
        }
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD})
    public String showPage( ModelMap model,
                            @ModelAttribute("appointmentToCreate") Appointment appointment) {
        if ( appointment == null ) {
            return "redirect:/admin/Statistics.htm";
        }
        return buildModel( model );
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveAppointment( ModelMap model,
                                   @ModelAttribute("appointmentToCreate") @Valid Appointment appointment,
                                   BindingResult errors ) {
        if ( appointment == null ) {
            return "redirect:/admin/Statistics.htm";
        }
        if ( errors.hasErrors() ) {
            return buildModel( model );
        }

        appointmentDao.saveEntity( appointment );

        Calendar appointmentDate = Calendar.getInstance();
        appointmentDate.setTime( appointment.getScheduledDate() );
        Calendar time = Calendar.getInstance();
        time.setTime( appointment.getScheduledTime() );
        appointmentDate.set( Calendar.HOUR_OF_DAY, time.get( Calendar.HOUR_OF_DAY ) );
        appointmentDate.set( Calendar.MINUTE, time.get( Calendar.MINUTE ) );

        SimpleDateFormat format = new SimpleDateFormat( "EEEE, dd MMMM в HH:mm", new Locale( "ru" ) );
        Map<String,Object> emailContext = new HashMap<>();
        emailContext.put( "fullDate", RuDateHelper.genetiveDay( format.format( appointmentDate.getTime() ) ) );
        emailContext.put( "user", appointment.getUser() );
        emailContext.put( "appointment", appointment );
        emailContext.put( "changedByAdmin", true );

        emailService.sendEmail( appointment.getUser(), EmailType.EDIT_BY_ADMIN_REMINDER, emailContext );


        SimpleDateFormat forRedirect = new SimpleDateFormat("dd.MM.yyyy");
        return "redirect:/admin/DayStats.htm?testDate=" + forRedirect.format( appointment.getScheduledDate() );
    }

    @RequestMapping(method = RequestMethod.POST, params = "delete")
    public String deleteAppointment( ModelMap model,
                                     @ModelAttribute("appointmentToCreate") Appointment appointment ) {

        //appointmentDao.deleteEntity( appointment );
        appointment.setEnabled( false );
        appointmentDao.saveEntity( appointment );

        Map<String,Object> emailContext = new HashMap<>();
        emailContext.put( "user", appointment.getUser() );
        emailContext.put( "changedByAdmin", true );

        emailService.sendEmail( appointment.getUser(), EmailType.REMOVE_APPOINTMENT, emailContext );
        return "redirect:/home.htm";
    }

}
