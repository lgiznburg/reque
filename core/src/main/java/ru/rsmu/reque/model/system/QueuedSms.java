package ru.rsmu.reque.model.system;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author leonid.
 */
@Entity
@Table(name = "sms_queue")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class QueuedSms  implements Serializable {
    private static final long serialVersionUID = 4459140878041598938L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "announce_text")
    @NotBlank
    private String text;

    @Column(name = "phone_number")
    @NotBlank
    private String phone;

    @Column(name = "send_status")
    private boolean send = false;

    @Column(name = "error_status")
    private boolean error = false;

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

    public boolean isSend() {
        return send;
    }

    public void setSend( boolean send ) {
        this.send = send;
    }

    public boolean isError() {
        return error;
    }

    public void setError( boolean error ) {
        this.error = error;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone( String phone ) {
        this.phone = phone;
    }
}
