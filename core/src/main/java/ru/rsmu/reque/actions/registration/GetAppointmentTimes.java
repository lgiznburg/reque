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
import org.springframework.web.bind.annotation.*;
import ru.rsmu.reque.dao.AppointmentDao;
import ru.rsmu.reque.dao.ReceptionCampaignDao;
import ru.rsmu.reque.editor.DateTimeEditor;
import ru.rsmu.reque.editor.ReceptionCampaignEditor;
import ru.rsmu.reque.model.registration.ReceptionCampaign;
import ru.rsmu.reque.model.system.StoredPropertyName;
import ru.rsmu.reque.service.AppointmentHelper;
import ru.rsmu.reque.service.StoredPropertyService;

import java.util.*;

/**
 * @author leonid.
 */
@Controller
@RequestMapping()
public class GetAppointmentTimes {

    @Autowired
    private ReceptionCampaignDao campaignDao;

    @Autowired
    private AppointmentDao appointmentDao;

    @Autowired
    private StoredPropertyService propertyService;

    @Autowired
    private AppointmentHelper appointmentHelper;


    @RequestMapping(value = "/ajax/{campaign}/GetAppointmentTimes.htm", method = RequestMethod.GET)
    public ResponseEntity<String> getTimes( @PathVariable("campaign") ReceptionCampaign campaign,
                                            @RequestParam(value = "date")Date date ) {

        List<List<String>> disableIntervals = appointmentHelper.getDateTimes( date, campaign );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );
        Gson gson = new GsonBuilder().create();
        String result = gson.toJson( disableIntervals );
        return new ResponseEntity<String>( result, headers, HttpStatus.OK );
    }

    @RequestMapping(value = "/ajax/{campaign}/GetTodayAppointmentTimes.htm", method = RequestMethod.GET)
    public ResponseEntity<String> getTodayTimes( @PathVariable("campaign") ReceptionCampaign campaign,
                                            @RequestParam(value = "date")Date date ) {

        List<List<String>> disableIntervals = appointmentHelper.getTodayTimes( date, campaign );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );
        Gson gson = new GsonBuilder().create();
        String result = gson.toJson( disableIntervals );
        return new ResponseEntity<String>( result, headers, HttpStatus.OK );
    }

    @InitBinder
    public void initBinder( WebDataBinder binder ) {
        binder.registerCustomEditor( Date.class, new DateTimeEditor() );
        binder.registerCustomEditor( ReceptionCampaign.class, new ReceptionCampaignEditor( campaignDao ) );
    }
}
