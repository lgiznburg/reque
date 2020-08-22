package ru.rsmu.reque.model.registration;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author leonid.
 */
@Entity
@Table(name = "campaign_reserve_days")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class CampaignReserveDay implements Serializable {
    private static final long serialVersionUID = 5287024143806393532L;

    public static final String WORKING_TIME_REGEXP = "(\\d{1,2}:\\d{2}\\-\\d{1,2}:\\d{2}[,;]?)+";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private ReceptionCampaign campaign;

    @Column(name = "reserve_day")
    @Temporal( TemporalType.DATE )
    @NotNull
    private Date reserveDay;

    @Column(name = "working_time")
    private String workingTime;

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public ReceptionCampaign getCampaign() {
        return campaign;
    }

    public void setCampaign( ReceptionCampaign campaign ) {
        this.campaign = campaign;
    }

    public Date getReserveDay() {
        return reserveDay;
    }

    public void setReserveDay( Date reserveDay ) {
        this.reserveDay = reserveDay;
    }

    public String getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime( String workingTime ) {
        this.workingTime = workingTime;
    }

    public long getDayAmount( int intervalAmount, long granularity ) {
        long amount = 0;

        if ( workingTime != null ) {
            String[] intervals = workingTime.split( "[,;]" );
            for ( String interval : intervals ) {
                int[] times = parseInterval( interval );
                if ( times != null ) {
                    amount += ( (times[2]-times[0]) * 60 + times[3] - times[1] ) / granularity * intervalAmount;
                }
            }
        }
        return amount;
    }

    public List<List<String>> getNotWorkingTime( Date startTime, Date endTime ) {
        SimpleDateFormat format = new SimpleDateFormat( "HH:mm" );
        List<List<String>> notWorking = new ArrayList<>();
        if ( workingTime != null ) {
            String dayTime = format.format( startTime ) + "-" + format.format( endTime );
            int[] daysHours = parseInterval( dayTime );

            if ( daysHours != null ) {
                int intervalHourStart = daysHours[0];
                int intervalMinStart = daysHours[1];
                String[] intervals = workingTime.split( "[,;]" );
                for ( String interval : intervals ) {
                    int[] times = parseInterval( interval );
                    if ( times != null ) {
                        if ( !(intervalHourStart == times[0] && intervalMinStart == times[1]) ) {
                            List<String> timeElement = new ArrayList<>();
                            timeElement.add( String.format( "%d:%02d" , intervalHourStart, intervalMinStart ) );
                            timeElement.add( String.format( "%d:%02d" , times[0], times[1] ) );
                            notWorking.add( timeElement );
                        }
                        intervalHourStart = times[2];
                        intervalMinStart = times[3];
                    }
                }
                if ( !(intervalHourStart == daysHours[3] && intervalMinStart == daysHours[3]) ) {
                    List<String> timeElement = new ArrayList<>();
                    timeElement.add( String.format( "%d:%02d" , intervalHourStart, intervalMinStart ) );
                    timeElement.add( String.format( "%d:%02d" , daysHours[2], daysHours[3] ) );
                    notWorking.add( timeElement );
                }
            }
        }
        else {
            List<String> timeElement = new ArrayList<>();
            timeElement.add( format.format( startTime ) );
            timeElement.add( format.format( endTime ) );
            notWorking.add( timeElement );
        }
        return notWorking;
    }

    private int[] parseInterval( String interval ) {
        Pattern pattern = Pattern.compile( "(\\d{1,2}):(\\d{2})-(\\d{1,2}):(\\d{2})" );
        Matcher matcher = pattern.matcher( interval );
        if ( matcher.matches() ) {
            int[] intervalNumbers = new int[4];
            intervalNumbers[0] = Integer.parseInt( matcher.group(1) );
            intervalNumbers[1] = Integer.parseInt( matcher.group(2) );
            intervalNumbers[2] = Integer.parseInt( matcher.group(3) );
            intervalNumbers[3] = Integer.parseInt( matcher.group(4) );
            return intervalNumbers;
        }
        return null;
    }
}
