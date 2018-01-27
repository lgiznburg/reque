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

    public ReceptionCampaign() {
        availableTypes = new ArrayList<>();
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
}
