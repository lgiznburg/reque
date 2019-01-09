package ru.rsmu.reque.model.system;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author leonid.
 */
@Entity
@Table(name = "additional_users_info")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class AdditionalUserInfo implements Serializable {

    private static final long serialVersionUID = 4090686022870094848L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @Column(name = "middle_name")
    @NotBlank
    private String middleName;

    @Column(name = "citizenship")
    @NotBlank
    private String citizenship;

    @Column(name = "birth_date")
    @NotNull
    private Date birthDate;

    @Column(name = "document_number")
    @NotBlank
    private String documentNumber;

    @Column(name = "session_end_date")
    @NotNull
    private Date sessionEndDate;

    @Column(name = "representative_name")
    private String representativeFullName;

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser( User user ) {
        this.user = user;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName( String middleName ) {
        this.middleName = middleName;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship( String citizenship ) {
        this.citizenship = citizenship;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate( Date birthDate ) {
        this.birthDate = birthDate;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber( String documentNumber ) {
        this.documentNumber = documentNumber;
    }

    public Date getSessionEndDate() {
        return sessionEndDate;
    }

    public void setSessionEndDate( Date sessionEndDate ) {
        this.sessionEndDate = sessionEndDate;
    }

    public String getRepresentativeFullName() {
        return representativeFullName;
    }

    public void setRepresentativeFullName( String representativeFullName ) {
        this.representativeFullName = representativeFullName;
    }
}
