package ru.rsmu.reque.dao;

import org.springframework.stereotype.Repository;
import ru.rsmu.reque.model.queue.QueueStatus;

import java.util.List;

/**
 * @author leonid.
 */
@Repository
public class QueueStatusDao extends CommonDao {

    public QueueStatus findQueueStatus( Long statusId ) {
        return findEntity( QueueStatus.class, statusId );
    }


    public List<QueueStatus> findAllQueueStatus() {
        return findAllEntities( QueueStatus.class );
    }

}
