package ru.rsmu.reque.model.system;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author leonid.
 */
@Entity
@Table(name = "password_keys")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class RemindPasswordKey implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "unique_key")
    private String uniqueKey;

    @Column(name = "expired_time")
    private Date expiredTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey( String uniqueKey ) {
        this.uniqueKey = uniqueKey;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime( Date expiredTime ) {
        this.expiredTime = expiredTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser( User user ) {
        this.user = user;
    }
}
