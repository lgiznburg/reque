package ru.rsmu.reque.actions.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.rsmu.reque.actions.BaseController;
import ru.rsmu.reque.dao.AppointmentDao;
import ru.rsmu.reque.model.system.DocumentName;

import java.util.List;

/**
 * @author leonid.
 */
@Controller
@RequestMapping("/admin/Documents.htm")
public class DocumentNames extends BaseController {
    @Autowired
    private AppointmentDao appointmentDao;

    public DocumentNames() {
        setTitle( "List of Document names" );
        setContent( "/WEB-INF/pages/blocks/admin/DocumentNames.jsp" );
    }

    @ModelAttribute("documentNames")
    public List<DocumentName> getDocuments() {
        return appointmentDao.findAllDocuments();
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD})
    public String showPage( ModelMap model ) {
        return buildModel( model );
    }

}
