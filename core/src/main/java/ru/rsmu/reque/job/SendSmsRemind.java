package ru.rsmu.reque.job;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.rsmu.reque.dao.AppointmentDao;
import ru.rsmu.reque.model.registration.Appointment;
import ru.rsmu.reque.model.system.StoredPropertyName;
import ru.rsmu.reque.service.MegaphoneService;
import ru.rsmu.reque.service.StoredPropertyService;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
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

    @Autowired
    private StoredPropertyService propertyService;

    private Calendar reminderTime;

    private static Set<Long> alreadySent =  new HashSet<>();

    public void sendReminders() {
        Calendar calendar = Calendar.getInstance();
        calendar.add( Calendar.DAY_OF_YEAR, 1 );

        int activation = propertyService.getPropertyAsInt( StoredPropertyName.MEGAPHONE_SMS_ENABLED );

        if ( activation > 0 ) {
            int balancer = 0;
            for ( Appointment appointment : appointmentDao.findDayAppointmentsFetch( calendar.getTime() ) ) {
                if (  StringUtils.isBlank( appointment.getUser().getPhoneNumber() ) ) {
                    continue;
                }
                if ( alreadySent.contains( appointment.getId() ) ) {
                    continue;
                }
                alreadySent.add( appointment.getId() );
                megaphoneService.sendSms( appointment );

                //balancer++;
                //if ( balancer == 3 ) {
                    try {
                        TimeUnit.SECONDS.sleep( 5 ); // add timeout  to not exceed Megaphone max load
                    } catch (InterruptedException e) {
                        //
                    }

                //}
            }
        }
    }

    public void clearSentQueue() {
        alreadySent.clear();
    }

}
