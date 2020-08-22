package ru.rsmu.reque.service.megaphone;

/**
 * @author leonid.
 */
public class MegaphoneResponse {
    private MegaphoneStatus status;
    private String msgId;

    public MegaphoneStatus getStatus() {
        return status;
    }

    public void setStatus( MegaphoneStatus status ) {
        this.status = status;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId( String msgId ) {
        this.msgId = msgId;
    }

    @Override
    public String toString() {
        return msgId + ": " + status.getDescription();
    }
}
