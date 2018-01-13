package ru.rsmu.reque.model.queue;

import ru.rsmu.reque.model.system.UserRole;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author leonid.
 */
@Entity
@Table(name = "service_place_types")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class ServicePlaceType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @ManyToMany
    @JoinTable( name = "place_type_statuses",
            joinColumns = {@JoinColumn(name = "place_type_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "status_id", nullable = false)}
    )
    private List<QueueStatus> assignedStatuses;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private UserRole assignedRole;

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

    public List<QueueStatus> getAssignedStatuses() {
        return assignedStatuses;
    }

    public void setAssignedStatuses( List<QueueStatus> assignedStatuses ) {
        this.assignedStatuses = assignedStatuses;
    }

    public UserRole getAssignedRole() {
        return assignedRole;
    }

    public void setAssignedRole( UserRole assignedRole ) {
        this.assignedRole = assignedRole;
    }
}
