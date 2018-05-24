package ru.rsmu.reque.model.queue;

import ru.rsmu.reque.model.system.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author leonid.
 */
@Entity
@Table(name = "queue_entries")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class QueueEntry implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String uid;

    @Column(name = "coming_time")
    private Date comingTime;

    @ManyToOne
    @JoinColumn(name = "service_status_id")
    private QueueStatus serviceStatus;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    @Column(name = "service_key")
    private String serviceKey;

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid( String uid ) {
        this.uid = uid;
    }

    public Date getComingTime() {
        return comingTime;
    }

    public void setComingTime( Date comingTime ) {
        this.comingTime = comingTime;
    }

    public QueueStatus getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus( QueueStatus serviceStatus ) {
        this.serviceStatus = serviceStatus;
    }

    public User getClient() {
        return client;
    }

    public void setClient( User client ) {
        this.client = client;
    }

    public String getServiceKey() {
        return serviceKey;
    }

    public void setServiceKey( String serviceKey ) {
        this.serviceKey = serviceKey;
    }
}
