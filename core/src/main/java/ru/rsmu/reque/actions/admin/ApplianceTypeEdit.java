package ru.rsmu.reque.actions.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.rsmu.reque.actions.BaseController;
import ru.rsmu.reque.dao.AppointmentDao;
import ru.rsmu.reque.editor.DocumentNameEditor;
import ru.rsmu.reque.model.system.ApplianceType;
import ru.rsmu.reque.model.system.DocumentName;

import javax.validation.Valid;
import java.util.List;

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

    @ModelAttribute("documentNames")
    public List<DocumentName> getDocuments() {
        return appointmentDao.findAllDocuments();
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

    @InitBinder
    public void initBinder( WebDataBinder binder ) {
        binder.registerCustomEditor( DocumentName.class, new DocumentNameEditor( appointmentDao ) );
    }
}
