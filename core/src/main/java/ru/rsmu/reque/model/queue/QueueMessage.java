package ru.rsmu.reque.model.queue;

import ru.rsmu.reque.model.system.User;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author leonid.
 */
@Entity
@Table(name = "queue_messages")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class QueueMessage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "entry_id")
    private QueueEntry entry;

    @Column
    private String message;

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser( User user ) {
        this.user = user;
    }

    public QueueEntry getEntry() {
        return entry;
    }

    public void setEntry( QueueEntry entry ) {
        this.entry = entry;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }
}
