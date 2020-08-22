package ru.rsmu.reque.model.system;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author leonid.
 */
@Entity
@Table(name = "homepage_announce")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class HomePageAnnounce implements Serializable {
    private static final long serialVersionUID = 3623210058466444931L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "announce_text")
    @NotBlank
    private String text;

    /**
     * Locale name
     */
    @Column
    private String locale;

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText( String text ) {
        this.text = text;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale( String locale ) {
        this.locale = locale;
    }
}
