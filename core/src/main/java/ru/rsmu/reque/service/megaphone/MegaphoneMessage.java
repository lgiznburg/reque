package ru.rsmu.reque.service.megaphone;

/**
 * @author leonid.
 */
public class MegaphoneMessage {

    private String from = "RSMU";
    private Long to;
    private String message;

    public String getFrom() {
        return from;
    }

    public void setFrom( String from ) {
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo( Long to ) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }
}
