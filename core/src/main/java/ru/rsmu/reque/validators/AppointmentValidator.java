package ru.rsmu.reque.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.rsmu.reque.model.registration.Appointment;
import ru.rsmu.reque.model.system.StoredPropertyName;
import ru.rsmu.reque.service.StoredPropertyService;

import java.util.Calendar;
import java.util.Date;

/**
 * @author leonid.
 */
@Component
public class AppointmentValidator implements Validator {

    @Autowired
    private StoredPropertyService propertyService;

    @Autowired
    private Validator validator;


    @Override
    public boolean supports( Class<?> clazz ) {
        return Appointment.class.isAssignableFrom( clazz );
    }

    @Override
    public void validate( Object target, Errors errors ) {
        ValidationUtils.invokeValidator( validator, target, errors );

        Appointment appointment = (Appointment) target;

        Calendar scheduledDate = Calendar.getInstance();
        if ( !errors.hasFieldErrors( "scheduledDate" ) ) {
            Date today = new Date();
            if ( !today.before( appointment.getScheduledDate() ) || appointment.getScheduledDate().after( appointment.getCampaign().getEndDate() ) ) {
                errors.rejectValue( "scheduledDate", "appointment.wrong_date" );
            }
            scheduledDate.setTime( appointment.getScheduledDate() );
            if ( scheduledDate.get( Calendar.DAY_OF_WEEK ) == Calendar.SUNDAY ) {
                errors.rejectValue( "scheduledDate", "appointment.wrong_time" );
            }
            if ( scheduledDate.get( Calendar.DAY_OF_WEEK ) == Calendar.SATURDAY
                    && propertyService.getPropertyAsInt( StoredPropertyName.SCHEDULE_WORKING_ON_SATURDAY ) <= 0 ) {
                //weekend
                errors.rejectValue( "scheduledDate", "appointment.wrong_time" );
            }
        }

        if ( !errors.hasFieldErrors( "scheduledTime" ) ) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime( appointment.getScheduledTime() );
            long scheduledTimeNum = getTimeNum( calendar );
            calendar.setTime( propertyService.getPropertyAsDate( StoredPropertyName.SCHEDULE_START_TIME ) );
            long startTimeNum = getTimeNum( calendar );
            if ( scheduledDate.get( Calendar.DAY_OF_WEEK ) == Calendar.SATURDAY ) {
                calendar.setTime( propertyService.getPropertyAsDate( StoredPropertyName.SCHEDULE_SATURDAY_END_TIME ) );
            }
            else {
                calendar.setTime( propertyService.getPropertyAsDate( StoredPropertyName.SCHEDULE_END_TIME ) );
            }
            long endTimeNum = getTimeNum( calendar );
            if ( startTimeNum > scheduledTimeNum || endTimeNum <= scheduledTimeNum ) {
                errors.reject( "scheduledTime", "appointment.wrong_time" );
            }
        }

    }

    private long getTimeNum( Calendar calendar ) {
        return 60 * calendar.get( Calendar.HOUR_OF_DAY ) + calendar.get( Calendar.MINUTE );
    }

}
