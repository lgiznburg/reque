package ru.rsmu.reque.model.registration;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author leonid.
 */
@Entity
@Table(name = "campaign_reserve_days")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class CampaignReserveDay implements Serializable {
    private static final long serialVersionUID = 5287024143806393532L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private ReceptionCampaign campaign;

    @Column(name = "reserve_day")
    @Temporal( TemporalType.DATE )
    @NotNull
    private Date reserveDay;

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public ReceptionCampaign getCampaign() {
        return campaign;
    }

    public void setCampaign( ReceptionCampaign campaign ) {
        this.campaign = campaign;
    }

    public Date getReserveDay() {
        return reserveDay;
    }

    public void setReserveDay( Date reserveDay ) {
        this.reserveDay = reserveDay;
    }
}
