package ru.rsmu.reque.model.system;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author leonid.
 */
@Entity
@Table(name = "document_names")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class DocumentName implements Serializable/*, Comparable<DocumentName>*/{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull
    private String name;

    @Column(name = "numb_order")
    private int order;

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

    public int getOrder() {
        return order;
    }

    public void setOrder( int order ) {
        this.order = order;
    }

/*    @Override
    public int compareTo( DocumentName o ) {
        return this.order - o.order;
    }*/
}
