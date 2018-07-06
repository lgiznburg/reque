package ru.rsmu.reque.job;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.rsmu.reque.dao.AppointmentDao;
import ru.rsmu.reque.model.registration.Appointment;
import ru.rsmu.reque.service.MegaphoneService;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * @author leonid.
 */
@Component
public class SendSmsRemind {

    @Autowired
    private AppointmentDao appointmentDao;

    @Autowired
    private MegaphoneService megaphoneService;

    private Calendar reminderTime;

    public void sendReminders() {
        Calendar calendar = Calendar.getInstance();
        calendar.add( Calendar.DAY_OF_YEAR, 1 );

        int balancer = 0;
        for ( Appointment appointment : appointmentDao.findDayAppointmentsFetch( calendar.getTime() ) ) {
            if (  StringUtils.isBlank( appointment.getUser().getPhoneNumber() ) ) {
                continue;
            }
            megaphoneService.sendSms( appointment );
            balancer++;
            if ( balancer == 3 ) {
                try {
                    TimeUnit.SECONDS.sleep( 10 ); // add timeout  to not exceed Megaphone max load
                } catch (InterruptedException e) {
                    //
                }

            }
        }
    }

}
