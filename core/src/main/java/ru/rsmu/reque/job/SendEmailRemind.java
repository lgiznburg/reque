package ru.rsmu.reque.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.rsmu.reque.dao.AppointmentDao;
import ru.rsmu.reque.model.registration.Appointment;
import ru.rsmu.reque.service.EmailService;
import ru.rsmu.reque.service.EmailType;
import ru.rsmu.reque.utils.RuDateHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author leonid.
 */
@Component
public class SendEmailRemind {

    @Autowired
    private AppointmentDao appointmentDao;

    @Autowired
    private EmailService emailService;

    public void sendReminders() {

        Calendar calendar = Calendar.getInstance();
        calendar.add( Calendar.DAY_OF_YEAR, 1 );

        for ( Appointment appointment : appointmentDao.findDayAppointmentsFetch( calendar.getTime() ) ) {
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

            emailService.sendEmail( appointment.getUser(), EmailType.REMINDER, emailContext );

        }
    }
}
