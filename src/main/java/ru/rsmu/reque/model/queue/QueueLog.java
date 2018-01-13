package ru.rsmu.reque.model.queue;

import ru.rsmu.reque.model.system.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author leonid.
 */
@Entity
@Table(name = "queue_log")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class QueueLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "entry_id")
    private QueueEntry entry;

    @ManyToOne
    @JoinColumn(name = "queue_status_id")
    private QueueStatus queueStatus;

    @ManyToOne
    @JoinColumn( name = "service_place_id")
    private ServicePlace servicePlace;

    @Column(name = "update_time")
    private Date timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public QueueEntry getEntry() {
        return entry;
    }

    public void setEntry( QueueEntry entry ) {
        this.entry = entry;
    }

    public QueueStatus getQueueStatus() {
        return queueStatus;
    }

    public void setQueueStatus( QueueStatus queueStatus ) {
        this.queueStatus = queueStatus;
    }

    public ServicePlace getServicePlace() {
        return servicePlace;
    }

    public void setServicePlace( ServicePlace servicePlace ) {
        this.servicePlace = servicePlace;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp( Date timestamp ) {
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser( User user ) {
        this.user = user;
    }
}
