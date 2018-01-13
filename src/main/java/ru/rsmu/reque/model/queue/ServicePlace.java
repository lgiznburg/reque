package ru.rsmu.reque.model.queue;

import ru.rsmu.reque.model.system.User;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author leonid.
 */
@Entity
@Table(name = "service_places")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class ServicePlace implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User attendant;

    @ManyToOne
    @JoinColumn(name = "place_type_id", nullable = false)
    private ServicePlaceType placeType;

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

    public User getAttendant() {
        return attendant;
    }

    public void setAttendant( User attendant ) {
        this.attendant = attendant;
    }

    public ServicePlaceType getPlaceType() {
        return placeType;
    }

    public void setPlaceType( ServicePlaceType placeType ) {
        this.placeType = placeType;
    }
}
