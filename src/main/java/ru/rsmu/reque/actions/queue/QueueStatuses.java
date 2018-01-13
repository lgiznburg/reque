package ru.rsmu.reque.actions.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.rsmu.reque.actions.BaseController;
import ru.rsmu.reque.dao.QueueStatusDao;
import ru.rsmu.reque.model.queue.QueueStatus;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author leonid.
 */
@Controller
@RequestMapping("/admin/QueueStatuses.htm")
public class QueueStatuses extends BaseController {

    private class StatusComparator implements Comparator<QueueStatus> {
        @Override
        public int compare( QueueStatus status1, QueueStatus status2 ) {
            if ( ( status1.isEditable() && status2.isEditable() )
                    || ( !status1.isEditable() && !status2.isEditable() ) ) {
                return status1.getName().compareTo( status2.getName() );
            }
            else if ( status1.isEditable() ) {
                return -1;
            }
            else return 1;
        }
    }

    @Autowired
    private QueueStatusDao queueStatusDao;

    public QueueStatuses() {
        setContent( "/WEB-INF/pages/blocks/admin/QueueStatuses.jsp" );
        setTitle( "List of Queue Status" );
    }

    @ModelAttribute("queueStatuses")
    public List<QueueStatus> getStatuses() {
        List<QueueStatus> statuses = queueStatusDao.findAllQueueStatus();
        Collections.sort( statuses, new StatusComparator() );
        return statuses;
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD})
    public String showPage( ModelMap modelMap ) {
        return buildModel( modelMap );
    }
}
