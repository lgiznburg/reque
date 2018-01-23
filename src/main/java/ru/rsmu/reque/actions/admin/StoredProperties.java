package ru.rsmu.reque.actions.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rsmu.reque.actions.BaseController;
import ru.rsmu.reque.dao.StoredPropertyDao;
import ru.rsmu.reque.model.system.StoredProperty;
import ru.rsmu.reque.model.system.StoredPropertyName;
import ru.rsmu.reque.service.StoredPropertyService;

import java.util.*;

/**
 * @author leonid.
 */
@Controller
@RequestMapping(value = "/admin/StoredProperties.htm")
public class StoredProperties extends BaseController {

    @Autowired
    private StoredPropertyDao storedPropertyDao;

    public StoredProperties() {
        setTitle( "Stored Properties" );
        setContent( "/WEB-INF/pages/blocks/admin/StoredProperties.jsp" );
    }

    @ModelAttribute("storedProperties")
    public List<StoredProperty> getProperties() {
        Map<StoredPropertyName,StoredProperty> properties = new HashMap<StoredPropertyName, StoredProperty>();
        for ( StoredProperty property : storedPropertyDao.findAll() ) {
            properties.put( property.getPropertyName(), property );
        }
        for ( StoredPropertyName names : StoredPropertyName.values() ) {
            if ( !properties.containsKey( names ) ) {
                properties.put( names, new StoredProperty( names, names.getDefaultValue() ) );
            }
        }
        List<StoredProperty> result = new LinkedList<StoredProperty>( properties.values() );
        Collections.sort( result );

        return result;
    }

    @RequestMapping()
    public String showPage( ModelMap model ) {
        return buildModel( model );
    }

}
