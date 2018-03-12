package ru.rsmu.reque.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.rsmu.reque.dao.AppointmentDao;
import ru.rsmu.reque.model.registration.Appointment;
import ru.rsmu.reque.model.system.User;
import ru.rsmu.reque.utils.SecurityContextHelper;

import java.util.Date;

/**
 * @author leonid.
 */
@ControllerAdvice
public class CommonElements {

    @Autowired
    private AppointmentDao appointmentDao;

    @ModelAttribute("user")
    public User getUser() {
        return SecurityContextHelper.getUser();
    }

    @ModelAttribute("appointment")
    public Appointment getAppointment() {
        User user = getUser();
        if ( user != null ) {
            Appointment appointment = appointmentDao.findAppointment( user, new Date() );
            return appointment;
        }
        return null;
    }


}
