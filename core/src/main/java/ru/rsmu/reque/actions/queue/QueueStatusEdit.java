package ru.rsmu.reque.actions.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rsmu.reque.actions.BaseController;
import ru.rsmu.reque.dao.QueueStatusDao;
import ru.rsmu.reque.model.queue.QueueStatus;

import javax.validation.Valid;

/**
 * @author leonid.
 */
@Controller
@RequestMapping("/admin/QueueStatusEdit.htm")
public class QueueStatusEdit extends BaseController {

    @Autowired
    private QueueStatusDao queueStatusDao;

    public QueueStatusEdit() {
        setTitle( "Edit Service Place Type" );
        setContent( "/WEB-INF/pages/blocks/admin/QueueStatusEdit.jsp" );
    }

    @ModelAttribute("queueStatus")
    public QueueStatus getQueueStatus( @RequestParam(value = "status_id", required = false) Long statusId ) {
        QueueStatus status = null;
        if ( statusId != null && statusId > 0 ) {
            status = queueStatusDao.findQueueStatus( statusId );
        }
        return status != null ? status : new QueueStatus();
    }

    @ModelAttribute("queueStatusTypes")
    public QueueStatus.StatusType[] getStatusTypes() {
        return new QueueStatus.StatusType[] { QueueStatus.StatusType.INITIAL, QueueStatus.StatusType.INNER };
    }

    @RequestMapping( method = RequestMethod.GET )
    public String getPage( ModelMap model ) {
        return buildModel( model );
    }

    @RequestMapping( method = RequestMethod.POST, params = "save")
    public String saveStatus( ModelMap modelMap,
                              @ModelAttribute("queueStatus") @Valid QueueStatus status,
                              BindingResult error ) {
        if ( !error.hasErrors() ) {
            queueStatusDao.saveEntity( status );
            // redirect
        }
        return buildModel( modelMap );
    }
}
