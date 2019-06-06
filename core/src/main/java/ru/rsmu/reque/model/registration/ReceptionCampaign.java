package ru.rsmu.reque.model.registration;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import ru.rsmu.reque.model.system.ApplianceType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author leonid.
 */
@Entity
@Table(name = "reception_campaigns")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class ReceptionCampaign implements Serializable {

    private static final long serialVersionUID = -7572972721835650570L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    @NotBlank
    private String name;

    @Column(name = "start_date")
    @Temporal( TemporalType.DATE )
    @NotNull
    private Date startDate;

    @Column(name = "end_date")
    @Temporal( TemporalType.DATE )
    @NotNull
    private Date endDate;

    @ManyToMany
    @JoinTable(name = "campaign_appliances",
            joinColumns = {@JoinColumn(name = "campaign_id")},
            inverseJoinColumns = {@JoinColumn(name = "appliance_type_id")}
    )
    @NotEmpty
    private List<ApplianceType> availableTypes;

    @Column
    private boolean active = true;

    @Column
    private int priority = 0;

    @Column(name = "concurrent_amount")
    private int concurrentAmount = 0;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "campaign")
    private List<CampaignReserveDay> reserveDays;

    public ReceptionCampaign() {
        availableTypes = new ArrayList<>();
        reserveDays = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate( Date startDate ) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate( Date endDate ) {
        this.endDate = endDate;
    }

    public List<ApplianceType> getAvailableTypes() {
        return availableTypes;
    }

    public void setAvailableTypes( List<ApplianceType> availableTypes ) {
        this.availableTypes = availableTypes;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive( boolean active ) {
        this.active = active;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority( int priority ) {
        this.priority = priority;
    }

    public int getConcurrentAmount() {
        return concurrentAmount;
    }

    public void setConcurrentAmount( int concurrentAmount ) {
        this.concurrentAmount = concurrentAmount;
    }

    public List<CampaignReserveDay> getReserveDays() {
        return reserveDays;
    }

    public void setReserveDays( List<CampaignReserveDay> reserveDays ) {
        this.reserveDays = reserveDays;
    }
}
