package ru.rsmu.reque.actions.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.rsmu.reque.actions.BaseController;
import ru.rsmu.reque.dao.AppointmentDao;
import ru.rsmu.reque.model.system.ApplianceType;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author leonid.
 */
@Controller
@RequestMapping(value = "/admin/Statistics.htm")
public class Statistics extends BaseController {

    @Autowired
    private AppointmentDao appointmentDao;

    public Statistics() {
        setTitle( "Statistic" );
        setContent( "/WEB-INF/pages/blocks/admin/Statistics.jsp" );
    }

    @ModelAttribute("types")
    public List<ApplianceType> getTypes() {
        return appointmentDao.findAllApplianceTypes();
    }

    @ModelAttribute("stats")
    public Map<Date, Map<ApplianceType, Long>> getStatistics() {
        return appointmentDao.findStatistics();
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD})
    public String showStats( ModelMap model ) {
        return buildModel( model );
    }
}
