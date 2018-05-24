package ru.rsmu.reque.actions.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.rsmu.reque.actions.BaseController;
import ru.rsmu.reque.dao.ServicePlaceTypeDao;
import ru.rsmu.reque.model.queue.ServicePlaceType;

import java.util.List;

/**
 * @author leonid.
 */
@Controller
@RequestMapping("/admin/ServicePlaceTypes.htm")
public class ServicePlaceTypes extends BaseController {

    @Autowired
    private ServicePlaceTypeDao servicePlaceTypeDao;

    public ServicePlaceTypes() {
        setTitle( "Service Place Types" );
        setContent( "/WEB-INF/pages/blocks/admin/ServicePlaceTypes.jsp" );
    }

    @ModelAttribute("servivePlaceTypes")
    public List<ServicePlaceType> getPlaceTypes() {
        return servicePlaceTypeDao.findAllEntities( ServicePlaceType.class );
    }

    @RequestMapping( method = {RequestMethod.GET, RequestMethod.POST})
    public String showPage( ModelMap model ) {
        return super.buildModel( model );
    }
}
