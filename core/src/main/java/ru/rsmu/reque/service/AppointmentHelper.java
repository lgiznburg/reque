package ru.rsmu.reque.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rsmu.reque.dao.AppointmentDao;
import ru.rsmu.reque.dao.ReceptionCampaignDao;
import ru.rsmu.reque.model.registration.Appointment;
import ru.rsmu.reque.model.registration.CampaignReserveDay;
import ru.rsmu.reque.model.registration.ReceptionCampaign;
import ru.rsmu.reque.model.system.StoredPropertyName;

import java.util.*;

/**
 * @author leonid.
 */
@Component
public class AppointmentHelper {
    @Autowired
    private StoredPropertyService propertyService;

    @Autowired
    private AppointmentDao appointmentDao;

    @Autowired
    private ReceptionCampaignDao campaignDao;


    public List<Map<String, Object>> getAvailableDates( Appointment appointment ) {

        ReceptionCampaign concurrentCampaign = campaignDao.findConcurrentCampaign( appointment.getCampaign(), new Date() );

        Date startDate = appointment.getCampaign().getStartDate();
        Date endDate = appointment.getCampaign().getEndDate();

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
        long concurrentDayAmount = (endTime.getTime() - startTime.getTime()) / (60000 * granularity) * appointment.getCampaign().getConcurrentAmount();
        long concurrentSaturdayAmount = (saturdayEndTime.getTime() - startTime.getTime()) / (60000 * granularity) * appointment.getCampaign().getConcurrentAmount();

        Map<Date,Long> countByDates = appointmentDao.findDates( startDate, endDate, appointment.getCampaign().getPriority() );

        if ( startDate.after( calendar.getTime() ) ) {
            calendar.setTime( startDate );
        }
        List<Map<String,Object>> dates = new ArrayList<>();
        while ( !calendar.getTime().after( endDate ) ) {
            CampaignReserveDay reserveDay = null;
            for ( CampaignReserveDay reverse : appointment.getCampaign().getReserveDays() ) {
                if ( calendar.getTime().equals( reverse.getReserveDay() ) ) {
                    /*Map<String,Object> map = new HashMap<>();
                    map.put( "date", calendar.getTime() );
                    map.put( "message", "Reserved" );
                    dates.add( map );*/
                    reserveDay = reverse;
                    break;
                }
            }

            long thisDayAmount = dayAmount;
            if ( reserveDay != null ) {
                if ( appointment.getCampaign().getPriority() > 0 && concurrentCampaign != null
                        && !calendar.getTime().after( concurrentCampaign.getEndDate() ) ) {
                    thisDayAmount = reserveDay.getDayAmount( appointment.getCampaign().getConcurrentAmount(), granularity );
                }
                else {
                    thisDayAmount = reserveDay.getDayAmount( amount, granularity );
                }
            } else {
                if ( calendar.get( Calendar.DAY_OF_WEEK ) == Calendar.SATURDAY ) {
                    thisDayAmount = saturdayAmount;
                }
                if ( appointment.getCampaign().getPriority() > 0 && concurrentCampaign != null
                        && !calendar.getTime().after( concurrentCampaign.getEndDate() ) ) {
                    thisDayAmount = concurrentDayAmount;
                    if ( calendar.get( Calendar.DAY_OF_WEEK ) == Calendar.SATURDAY ) {
                        thisDayAmount = concurrentSaturdayAmount;
                    }
                }
            }
            if ( calendar.get( Calendar.DAY_OF_WEEK ) == Calendar.SUNDAY
                    || ( calendar.get( Calendar.DAY_OF_WEEK ) == Calendar.SATURDAY
                    && propertyService.getPropertyAsInt( StoredPropertyName.SCHEDULE_WORKING_ON_SATURDAY ) <= 0 )  ) {
                Map<String,Object> map = new HashMap<>();
                map.put( "date", calendar.getTime() );
                map.put( "message", "Weekend" );
                dates.add( map );
            }
            else if ( thisDayAmount == 0 ||
                    ( countByDates.get( calendar.getTime() ) != null &&
                            countByDates.get( calendar.getTime() ) >= thisDayAmount )
            ) {
                Map<String,Object> map = new HashMap<>();
                map.put( "date", calendar.getTime() );
                map.put( "message", "Not available" );
                dates.add( map );
            }
            calendar.add( Calendar.DAY_OF_YEAR, 1 );
        }
        return dates;
    }

    public List<List<String>> getDateTimes( Date date, ReceptionCampaign campaign   ) {
        long amount = propertyService.getPropertyAsLong( StoredPropertyName.SCHEDULE_SERVICE_AMOUNT );
        ReceptionCampaign concurrent = campaignDao.findConcurrentCampaign( campaign, date );
        if ( concurrent!= null && campaign.getPriority() > 0 ) {
            amount = campaign.getConcurrentAmount();
        }
        List<List<String>> result = getReserveDaysTimes( date, campaign );
        result.addAll( calculateTimes( date, amount, campaign.getPriority() ));
        return result;
    }

    public List<List<String>> getTodayTimes( Date date, ReceptionCampaign campaign   ) {
        long amount = propertyService.getPropertyAsLong( StoredPropertyName.SCHEDULE_SERVICE_AMOUNT );
        ReceptionCampaign concurrent = campaignDao.findConcurrentCampaign( campaign, date );
        if ( concurrent!= null && campaign.getPriority() > 0 ) {
            amount = campaign.getConcurrentAmount();
        }
        int addAmount = propertyService.getPropertyAsInt( StoredPropertyName.SCHEDULE_ADMIN_ADD_AMOUNT );
        List<List<String>> result = getReserveDaysTimes( date, campaign );
        result.addAll( calculateTimes( date, amount + addAmount, campaign.getPriority() ));
        return result;
    }

    private List<List<String>> getReserveDaysTimes( Date date, ReceptionCampaign campaign ) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( date );
        calendar.set( Calendar.HOUR_OF_DAY, 0 );
        calendar.set( Calendar.MINUTE, 0 );
        calendar.set( Calendar.SECOND, 0 );
        calendar.set( Calendar.MILLISECOND, 0 );
        Date startTime = propertyService.getPropertyAsDate( StoredPropertyName.SCHEDULE_START_TIME );
        Date endTime = propertyService.getPropertyAsDate( StoredPropertyName.SCHEDULE_END_TIME );
        if ( calendar.get( Calendar.DAY_OF_WEEK ) == Calendar.SATURDAY ) {
            endTime = propertyService.getPropertyAsDate( StoredPropertyName.SCHEDULE_SATURDAY_END_TIME );
        }
        CampaignReserveDay reserveDay = null;
        for ( CampaignReserveDay reverse : campaign.getReserveDays() ) {
            if ( calendar.getTime().equals( reverse.getReserveDay() ) ) {
                reserveDay = reverse;
                break;
            }
        }
        if ( reserveDay != null ) {
            return reserveDay.getNotWorkingTime( startTime, endTime );
        }
        return new ArrayList<>();
    }

    private List<List<String>> calculateTimes( Date date, long amount, int priority ) {
        int granularity = propertyService.getPropertyAsInt( StoredPropertyName.SCHEDULE_SERVICE_INTERVAL );
        Map<Date, Long> times = appointmentDao.findHours( date, priority );

        List<List<String>> disableIntervals = new ArrayList<>();
        List<String> oneInterval = new ArrayList<>();
        int startInterval = 0;
        for ( Date time : times.keySet() ) {
            if ( times.get( time ) >= amount ) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime( time );
                int hour = calendar.get( Calendar.HOUR_OF_DAY );
                int minute = calendar.get( Calendar.MINUTE );
                int timeNum = hour * 60 + minute;
                if ( startInterval + granularity == timeNum ) {
                    startInterval = timeNum;
                }
                else {
                    if ( startInterval != 0 ) {
                        int endInterval = startInterval + granularity;
                        oneInterval.add( String.format( "%d:%02d", endInterval/60, endInterval%60 ) );
                        disableIntervals.add( oneInterval );
                        oneInterval = new ArrayList<>();
                    }
                    oneInterval.add( String.format( "%d:%02d", hour, minute ) );
                    startInterval = timeNum;
                }
            }
        }
        if ( startInterval != 0 ) {
            int endInterval = startInterval + granularity;
            oneInterval.add( String.format( "%d:%02d", endInterval/60, endInterval%60 ) );
            disableIntervals.add( oneInterval );
        }
        Date endTime = propertyService.getPropertyAsDate( StoredPropertyName.SCHEDULE_END_TIME );
        Date saturdayEndTime = propertyService.getPropertyAsDate( StoredPropertyName.SCHEDULE_SATURDAY_END_TIME );

        Calendar checkingDay = Calendar.getInstance();
        checkingDay.setTime( date );
        if ( propertyService.getPropertyAsInt( StoredPropertyName.SCHEDULE_WORKING_ON_SATURDAY ) > 0
                && checkingDay.get( Calendar.DAY_OF_WEEK ) == Calendar.SATURDAY
                && endTime.after( saturdayEndTime ) ) {
            Calendar saturday = Calendar.getInstance();
            saturday.setTime( saturdayEndTime );
            int hour1 = saturday.get( Calendar.HOUR_OF_DAY );
            int minute1 = saturday.get( Calendar.MINUTE );

            Calendar normalDay = Calendar.getInstance();
            normalDay.setTime( endTime );
            int hour2 = normalDay.get( Calendar.HOUR_OF_DAY );
            int minute2 = normalDay.get( Calendar.MINUTE );

            oneInterval = new ArrayList<>();
            oneInterval.add( String.format( "%d:%02d", hour1, minute1 ) );
            oneInterval.add( String.format( "%d:%02d", hour2, minute2 ) );
            disableIntervals.add( oneInterval );
        }
        return disableIntervals;

    }

}
