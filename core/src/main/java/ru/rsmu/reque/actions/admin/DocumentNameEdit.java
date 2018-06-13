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
import ru.rsmu.reque.model.system.DocumentName;

import javax.validation.Valid;

/**
 * @author leonid.
 */
@Controller
@RequestMapping(value = "/admin/DocumentNameEdit.htm")
public class DocumentNameEdit extends BaseController {

    @Autowired
    private AppointmentDao appointmentDao;

    public DocumentNameEdit() {
        setTitle( "Edit document name" );
        setContent( "/WEB-INF/pages/blocks/admin/DocumentNameEdit.jsp" );
    }

    @ModelAttribute("documentName")
    public DocumentName getDocumentName( @RequestParam(value = "id", required = false, defaultValue = "0") Long id ) {
        DocumentName documentName = appointmentDao.findDocument( id );
        return documentName == null ? new DocumentName() : documentName;
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD})
    public String showPage( ModelMap model ) {
        return buildModel( model );
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveType( ModelMap model,
                            @ModelAttribute("documentName") @Valid DocumentName documentName,
                            BindingResult error ) {
        if ( error.hasErrors() ) {
            return buildModel( model );
        }
        appointmentDao.saveEntity( documentName );
        return "redirect:/admin/Documents.htm";
    }

}
