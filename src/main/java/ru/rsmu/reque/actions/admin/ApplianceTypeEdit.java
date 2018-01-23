package ru.rsmu.reque.actions.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rsmu.reque.actions.BaseController;
import ru.rsmu.reque.dao.AppointmentDao;
import ru.rsmu.reque.model.system.ApplianceType;

import javax.validation.Valid;

/**
 * @author leonid.
 */
@Controller
@RequestMapping(value = "/admin/ApplianceTypeEdit.htm")
public class ApplianceTypeEdit extends BaseController {

    @Autowired
    private AppointmentDao appointmentDao;

    public ApplianceTypeEdit() {
        setTitle( "Edit type" );
        setContent( "/WEB-INF/pages/blocks/admin/ApplianceTypeEdit.jsp" );
    }

    @ModelAttribute("applianceType")
    public ApplianceType getApplianceType( @RequestParam(value = "id", required = false, defaultValue = "0") Long id ) {
        ApplianceType type = appointmentDao.findApplianceType( id );
        return type == null ? new ApplianceType() : type;
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD})
    public String showPage( ModelMap model ) {
        return buildModel( model );
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveType( ModelMap model,
                            @ModelAttribute("applianceType") @Valid ApplianceType type,
                            BindingResult error ) {
        if ( error.hasErrors() ) {
            return buildModel( model );
        }
        appointmentDao.saveEntity( type );
        return "redirect:/admin/ApplianceTypes.htm";
    }

}
