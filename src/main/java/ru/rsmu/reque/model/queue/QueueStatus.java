package ru.rsmu.reque.model.queue;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author leonid.
 */
@Entity
@Table(name = "queue_statuses")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class QueueStatus implements Serializable {

    public enum StatusType {
        INITIAL,
        INNER,
        ASSIGNED,
        PROCESSING,
        FINISHED,
        CANCELED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotEmpty
    private String name;

    @Column
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull
    private StatusType type;

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

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public StatusType getType() {
        return type;
    }

    public void setType( StatusType type ) {
        this.type = type;
    }

    public boolean isEditable() {
        return type == StatusType.INITIAL || type == StatusType.INNER;
    }
}
