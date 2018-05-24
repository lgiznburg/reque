package ru.rsmu.reque.actions.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rsmu.reque.actions.BaseController;
import ru.rsmu.reque.dao.ServicePlaceTypeDao;
import ru.rsmu.reque.model.queue.ServicePlaceType;

/**
 * @author leonid.
 */
@Controller
@RequestMapping("/admin/ServicePlaceTypeEdit.htm")
public class ServicePlaceTypeEdit extends BaseController {

    @Autowired
    private ServicePlaceTypeDao servicePlaceTypeDao;

    public ServicePlaceTypeEdit() {
        setTitle( "Edit Service Place Type" );
        setContent( "/WEB-INF/pages/blocks/admin/ServicePlaceTypeEdit.jsp" );
    }

    @ModelAttribute("servicePlaceType")
    public ServicePlaceType getServicePlaceType( @RequestParam(value = "type_id", required = false) Long typeId ) {
        ServicePlaceType servicePlaceType = null;
        if ( typeId != null && typeId > 0 ) {
            servicePlaceType = servicePlaceTypeDao.findEntity( ServicePlaceType.class, typeId );
        }
        return servicePlaceType != null ? servicePlaceType : new ServicePlaceType();
    }
}
