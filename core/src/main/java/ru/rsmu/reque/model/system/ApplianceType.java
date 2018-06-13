package ru.rsmu.reque.model.system;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author leonid.
 */
@Entity
@Table(name = "appliance_types")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class ApplianceType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String description;

    @ManyToMany
    @JoinTable(name = "appliance_documents",
            joinColumns = {@JoinColumn(name = "appliance_type_id")},
            inverseJoinColumns = {@JoinColumn(name = "document_id")}
    )
    private List<DocumentName> documents;

    public ApplianceType() {
        documents = new ArrayList<>();
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

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public List<DocumentName> getDocuments() {
        return documents;
    }

    public void setDocuments( List<DocumentName> documents ) {
        this.documents = documents;
    }
}
