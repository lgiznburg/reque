package ru.rsmu.reque.actions.registration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rsmu.reque.dao.AppointmentDao;
import ru.rsmu.reque.editor.DateTimeEditor;
import ru.rsmu.reque.model.system.StoredPropertyName;
import ru.rsmu.reque.service.StoredPropertyService;

import java.util.*;

/**
 * @author leonid.
 */
@Controller
@RequestMapping(value = "/ajax/GetAppointmentTimes.htm")
public class GetAppointmentTimes {

    @Autowired
    private AppointmentDao appointmentDao;

    @Autowired
    private StoredPropertyService propertyService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> getTimes( @RequestParam(value = "date")Date date ) {

        int granularity = propertyService.getPropertyAsInt( StoredPropertyName.SCHEDULE_SERVICE_INTERVAL );
        long amount = propertyService.getPropertyAsLong( StoredPropertyName.SCHEDULE_SERVICE_AMOUNT );
        Map<Date, Long> times = appointmentDao.findHours( date );

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
                        oneInterval.add( String.format( "%d:%d", startInterval/60, startInterval%60 + granularity ) );
                        disableIntervals.add( oneInterval );
                        oneInterval = new ArrayList<>();
                    }
                    oneInterval.add( String.format( "%d:%d", hour, minute ) );
                    startInterval = timeNum;
                }
            }
        }
        if ( startInterval != 0 ) {
            oneInterval.add( String.format( "%d:%d", startInterval/60, startInterval%60 + granularity ) );
            disableIntervals.add( oneInterval );
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );
        Gson gson = new GsonBuilder().create();
        String result = gson.toJson( disableIntervals );
        return new ResponseEntity<String>( result, headers, HttpStatus.OK );
    }

    @InitBinder
    public void initBinder( WebDataBinder binder ) {
        binder.registerCustomEditor( Date.class, new DateTimeEditor() );
    }
}
