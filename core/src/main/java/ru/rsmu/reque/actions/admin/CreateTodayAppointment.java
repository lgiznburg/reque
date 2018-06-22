package ru.rsmu.reque.actions.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.rsmu.reque.actions.BaseController;
import ru.rsmu.reque.actions.admin.form.TodayAppointmentForm;
import ru.rsmu.reque.dao.AppointmentDao;
import ru.rsmu.reque.dao.IUserDao;
import ru.rsmu.reque.dao.ReceptionCampaignDao;
import ru.rsmu.reque.dao.UserDao;
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

import javax.validation.Valid;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author leonid.
 */
@Controller
@RequestMapping(value = "/admin/CreateTodayAppointment.htm")
public class CreateTodayAppointment extends BaseController {

    @Autowired
    private StoredPropertyService propertyService;

    @Autowired
    private AppointmentDao appointmentDao;

    @Autowired
    private ReceptionCampaignDao campaignDao;

    @Autowired
    private EmailService emailService;

    @Autowired
    private Validator validator;

    @Autowired
    private IUserDao userDao;

    public CreateTodayAppointment() {
        setTitle( "Назначить время на сегодня" );
        setContent( "/WEB-INF/pages/blocks/admin/CreateTodayAppointment.jsp" );
    }

   /* @ModelAttribute("availableDates")
    public List<Map<String, Object>> getAvailableDates( ModelMap model,
                                                        @RequestParam(value = "campaign") ReceptionCampaign campaign ) {

        Date startDate = campaign.getStartDate();
        Date endDate = campaign.getEndDate();

        Calendar calendar = Calendar.getInstance();
        calendar.set( Calendar.HOUR_OF_DAY, 0 );
        calendar.set( Calendar.MINUTE, 0 );
        calendar.set( Calendar.SECOND, 0 );
        calendar.set( Calendar.MILLISECOND, 0 );

        Date startTime = propertyService.getPropertyAsDate( StoredPropertyName.SCHEDULE_START_TIME );
        Date endTime = propertyService.getPropertyAsDate( StoredPropertyName.SCHEDULE_END_TIME );
        Date saturdayEndTime = propertyService.getPropertyAsDate( StoredPropertyName.SCHEDULE_SATURDAY_END_TIME );
        long granularity = propertyService.getPropertyAsLong( StoredPropertyName.SCHEDULE_SERVICE_INTERVAL );
        int amount = propertyService.getPropertyAsInt( StoredPropertyName.SCHEDULE_SERVICE_AMOUNT );
        long dayAmount = (endTime.getTime() - startTime.getTime()) / (60000 * granularity) * amount;
        long saturdayAmount = (saturdayEndTime.getTime() - startTime.getTime()) / (60000 * granularity) * amount;

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
            else if ( calendar.get( Calendar.DAY_OF_WEEK ) == Calendar.SATURDAY ) {
                if ( propertyService.getPropertyAsInt( StoredPropertyName.SCHEDULE_WORKING_ON_SATURDAY ) > 0 ) {
                    if ( countByDates.get( calendar.getTime() ) != null && countByDates.get( calendar.getTime() ) >= saturdayAmount ) {
                        Map<String,Object> map = new HashMap<>();
                        map.put( "date", calendar.getTime() );
                        map.put( "message", "Not available" );
                        dates.add( map );
                    }
                }
                else {
                    Map<String,Object> map = new HashMap<>();
                    map.put( "date", calendar.getTime() );
                    map.put( "message", "Weekend" );
                    dates.add( map );
                }
            }
            else if ( countByDates.get( calendar.getTime() ) != null && countByDates.get( calendar.getTime() ) >= dayAmount ) {
                Map<String,Object> map = new HashMap<>();
                map.put( "date", calendar.getTime() );
                map.put( "message", "Not available" );
                dates.add( map );
            }
            calendar.add( Calendar.DAY_OF_YEAR, 1 );
        }
        return dates;
    }
*/
    @ModelAttribute("startDate")
    public Date getStartDate( ModelMap model,
                              @RequestParam(value = "campaign") ReceptionCampaign campaign ) {
        Calendar calendar = Calendar.getInstance(); // today
        Date startDate = campaign.getStartDate();
        // it's too strict condition. today is always in selected campaign dates
        return startDate.before( calendar.getTime() ) ? calendar.getTime() : startDate;
    }

   @ModelAttribute("granularity")
    public int getGranularity() {
        return propertyService.getPropertyAsInt( StoredPropertyName.SCHEDULE_SERVICE_INTERVAL );
    }

    @ModelAttribute("startTime")
    public Date getStartTime() {
        Calendar startTime = Calendar.getInstance();
        Calendar rowStartTime = Calendar.getInstance();
        rowStartTime.setTime( propertyService.getPropertyAsDate( StoredPropertyName.SCHEDULE_START_TIME ) );
        startTime.set( Calendar.HOUR_OF_DAY, rowStartTime.get( Calendar.HOUR_OF_DAY ) );
        startTime.set( Calendar.MINUTE, rowStartTime.get( Calendar.MINUTE ) );

        int interval = propertyService.getPropertyAsInt( StoredPropertyName.SCHEDULE_SERVICE_INTERVAL );
        Date current = new Date();
        while ( current.after( startTime.getTime() ) ) {
            startTime.add( Calendar.MINUTE, interval );
        }
        return startTime.getTime();
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

    @ModelAttribute("appointmentToCreate")
    public TodayAppointmentForm getAppointment( @RequestParam(value = "campaign") ReceptionCampaign campaign ) {
        TodayAppointmentForm appointmentForm = new TodayAppointmentForm();
        appointmentForm.setCampaign( campaign );
        return appointmentForm;
    }

    @ModelAttribute("applianceTypes")
    public List<ApplianceType> getApplianceTypes( ModelMap model,
                                                  @RequestParam(value = "campaign") ReceptionCampaign campaign ) {
        return campaign.getAvailableTypes();
    }

    @InitBinder
    public void initBinder( WebDataBinder binder ) {
        binder.registerCustomEditor( Date.class, new DateTimeEditor() );
        binder.registerCustomEditor( Time.class, new DateTimeEditor( true ) );
        binder.registerCustomEditor( ApplianceType.class, new ApplianceTypeEditor( appointmentDao ) );
        binder.registerCustomEditor( ReceptionCampaign.class, new ReceptionCampaignEditor( campaignDao ) );
        if ( binder.getTarget() instanceof TodayAppointmentForm ) {
            binder.setValidator( validator );
        }
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD})
    public String showPage( ModelMap model,
                            @ModelAttribute("appointmentToCreate") TodayAppointmentForm appointmentForm) {
        return buildModel( model );
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveAppointment( ModelMap model,
                                   @ModelAttribute("appointmentToCreate") @Valid TodayAppointmentForm appointmentForm,
                                   BindingResult errors ) {
        if ( errors.hasErrors() ) {
            return buildModel( model );
        }
        User user = userDao.findUser( appointmentForm.getUserEmail() );
        if ( user == null ) {
            user = createUser( appointmentForm );
            userDao.saveUser( user );
        }
        Appointment appointment = appointmentDao.findAppointment( user, new Date() );
        if ( appointment == null ) {
            appointment = new Appointment();
            appointment.setUser( user );
        }
        appointment.setCampaign( appointmentForm.getCampaign() );
        appointment.setOnlineNumber( appointmentForm.getOnlineNumber() );
        appointment.setScheduledDate( new Date() ); //today
        appointment.setScheduledTime( appointmentForm.getScheduledTime() );
        appointment.setType( appointmentForm.getType() );

        appointmentDao.saveEntity( appointment );

        /*Calendar appointmentDate = Calendar.getInstance();
        appointmentDate.setTime( appointment.getScheduledDate() );
        Calendar time = Calendar.getInstance();
        time.setTime( appointment.getScheduledTime() );
        appointmentDate.set( Calendar.HOUR_OF_DAY, time.get( Calendar.HOUR_OF_DAY ) );
        appointmentDate.set( Calendar.MINUTE, time.get( Calendar.MINUTE ) );

        SimpleDateFormat format = new SimpleDateFormat( "EEEE, dd MMMM в HH:mm", new Locale( "ru" ) );
        Map<String,Object> emailContext = new HashMap<>();
        emailContext.put( "fullDate", RuDateHelper.genetiveDay( format.format( appointmentDate.getTime() ) ) );
        emailContext.put( "user", user );
        emailContext.put( "appointment", appointment );

        emailService.sendEmail( user, EmailType.REMINDER, emailContext );*/

        model.addAttribute( "createdAppointment", appointment );
        String view = buildModel( model );
        model.addAttribute( CONTENT, "/WEB-INF/pages/blocks/admin/CreateTodayAppointmentSuccess.jsp" );
        return view;
    }

    private User createUser( TodayAppointmentForm appointmentForm ) {
        User user = new User();
        user.setUsername( appointmentForm.getUserEmail() );
        user.setFirstName( appointmentForm.getFirstName() );
        user.setLastName( appointmentForm.getLastName() );
        user.setPassword( "undefined" );
        user.setPhoneNumber( "" );
        user.setCreatedTime( new Date() );
        user.setLastUpdated( new Date() );

        Map<String,Object> emailContext = new HashMap<>();
        emailContext.put( "user", user );
        emailService.sendEmail( user, EmailType.REGISTRATION_BY_ADMIN, emailContext );


        return user;
    }

}
