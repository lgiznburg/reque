package ru.rsmu.reque.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ru.rsmu.reque.model.registration.Appointment;
import ru.rsmu.reque.model.system.ApplianceType;
import ru.rsmu.reque.model.system.User;

import java.util.*;

/**
 * @author leonid.
 */
@Repository
public class AppointmentDao extends CommonDao {

    public Map<Date, Long> findDates( Date startDate, Date endDate ) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria( Appointment.class )
                .add( Restrictions.between( "scheduledDate", startDate, endDate ) )
                .setProjection(
                        Projections.projectionList()
                        .add( Projections.rowCount() )
                        .add( Projections.groupProperty( "scheduledDate" ) )
                );
        List result = criteria.list();
        Map<Date,Long> dates = new HashMap<>();
        for ( Object object : result ) {
            Object[] row = (Object[]) object;
            dates.put( (Date)row[1], (Long)row[0] );
        }
        return dates;
    }

    public Map<Date, Long> findHours( Date date ) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria( Appointment.class )
                .add( Restrictions.eq( "scheduledDate", date ) )
                .setProjection(
                        Projections.projectionList()
                                .add( Projections.rowCount() )
                                .add( Projections.groupProperty( "scheduledTime" ) )
                );
        List result = criteria.list();
        Map<Date,Long> hours = new HashMap<>();
        for ( Object object : result ) {
            Object[] row = (Object[]) object;
            hours.put( (Date) row[1], (Long) row[0] );
        }
        return hours;
    }

    public ApplianceType findApplianceType( Long id ) {
        return findEntity( ApplianceType.class, id );
    }

    public List<ApplianceType> findAllApplianceTypes() {
        return findAllEntities( ApplianceType.class );
    }

    public Map<Date, Map<ApplianceType, Long>> findStatistics() {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria( Appointment.class )
                .setProjection( Projections.projectionList()
                                .add( Projections.rowCount() )
                        .add( Projections.groupProperty( "scheduledDate" ) )
                        .add( Projections.groupProperty( "type" ) )
                );
        List result = criteria.list(); //query.list();
        Map<Date,Map<ApplianceType,Long>> stats = new HashMap<>();
        Date current = null;
        Map<ApplianceType,Long> day = new HashMap<>();
        for ( Object object : result ) {
            Object[] row = (Object[]) object;
            Date date = (Date) row[1];
            if ( !date.equals( current ) ) {
                if ( current != null ) {
                    stats.put( current, day );
                }
                day = new HashMap<>();
                current = date;
            }
            day.put( (ApplianceType) row[2] , (Long) row[0] );
        }
        if ( current != null ) {
            stats.put( current, day );
        }
        return stats;
    }

    public Appointment findAppointment( long id ) {
        return getHibernateTemplate().load( Appointment.class, id );
    }

    public Appointment findAppointment( User user, Date date ) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria( Appointment.class )
                .add( Restrictions.eq( "user", user ) )
                .add( Restrictions.gt( "scheduledDate", date ) )
                .setMaxResults( 1 );
        return (Appointment) criteria.uniqueResult();
    }
}
