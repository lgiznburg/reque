package ru.rsmu.reque.actions.admin.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import ru.rsmu.reque.model.registration.ReceptionCampaign;
import ru.rsmu.reque.model.system.ApplianceType;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.sql.Time;

/**
 * @author leonid.
 */
public class TodayAppointmentForm {

    @NotNull
    private Time scheduledTime;

    @NotBlank
    private String onlineNumber;

    @NotBlank
    @Email
    private String userEmail;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private ApplianceType type;

    @NotNull
    private ReceptionCampaign campaign;

    public Time getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime( Time scheduledTime ) {
        this.scheduledTime = scheduledTime;
    }

    public String getOnlineNumber() {
        return onlineNumber;
    }

    public void setOnlineNumber( String onlineNumber ) {
        this.onlineNumber = onlineNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail( String userEmail ) {
        this.userEmail = userEmail;
    }

    public ApplianceType getType() {
        return type;
    }

    public void setType( ApplianceType type ) {
        this.type = type;
    }

    public ReceptionCampaign getCampaign() {
        return campaign;
    }

    public void setCampaign( ReceptionCampaign campaign ) {
        this.campaign = campaign;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName( String firstName ) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName( String lastName ) {
        this.lastName = lastName;
    }
}
