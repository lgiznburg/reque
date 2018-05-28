package ru.rsmu.reque.model.registration;

import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.NotBlank;
import ru.rsmu.reque.model.system.ApplianceType;
import ru.rsmu.reque.model.system.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 * @author leonid.
 */
@Entity
@Table(name = "appointments")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class Appointment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "scheduled_date")
    @Temporal( TemporalType.DATE )
    @NotNull
    private Date scheduledDate;

    @Column(name = "scheduled_time")
    //@Temporal( TemporalType.TIME )
    @NotNull
    private Time scheduledTime;

    @Column(name = "online_number")
    @NotBlank
    private String onlineNumber;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "type_id")
    @NotNull
    private ApplianceType type;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    @NotNull
    private ReceptionCampaign campaign;

    @Formula( "(select case when count(app.id)>1 then 1 else 0 end from appointments app where app.client_id=client_id)" )
    private boolean repeated;

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate( Date scheduledDate ) {
        this.scheduledDate = scheduledDate;
    }

    public Time getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime( Time scheduledTime ) {
        this.scheduledTime = scheduledTime;
    }

    public String getOnlineNumber() {
        return onlineNumber;
    }

    public void setOnlineNumber( String onlineNumber ) {
        this.onlineNumber = onlineNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser( User user ) {
        this.user = user;
    }

    public ApplianceType getType() {
        return type;
    }

    public void setType( ApplianceType type ) {
        this.type = type;
    }

    public ReceptionCampaign getCampaign() {
        return campaign;
    }

    public void setCampaign( ReceptionCampaign campaign ) {
        this.campaign = campaign;
    }

    public boolean isRepeated() {
        return repeated;
    }

    public void setRepeated( boolean repeated ) {
        this.repeated = repeated;
    }
}
