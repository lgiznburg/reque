package ru.rsmu.reque.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import ru.rsmu.reque.model.registration.Appointment;
import ru.rsmu.reque.model.system.ApplianceType;
import ru.rsmu.reque.model.system.DocumentName;
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
                .add( Restrictions.eq( "enabled", true ) )
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

    public Map<Date, Long> findHours( Date date, int priority ) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria( Appointment.class )
                .createAlias( "campaign", "campaign" )
                .add( Restrictions.eq( "campaign.priority", priority ) )
                .add( Restrictions.eq( "scheduledDate", date ) )
                .add( Restrictions.eq( "enabled", true ) )
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
        return findStatistics( null );
    }

    public Map<Date, Map<ApplianceType, Long>> findStatistics( Date date ) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria( Appointment.class )
                .add( Restrictions.eq( "enabled", true ) )
                .setProjection( Projections.projectionList()
                            .add( Projections.rowCount() )
                            .add( Projections.groupProperty( "scheduledDate" ) )
                            .add( Projections.groupProperty( "type" ) )
            );
        if ( date != null ) {
            criteria.add( Restrictions.eq( "scheduledDate", date ) );
        }
        criteria.addOrder( Order.asc( "scheduledDate" ) );
        List result = criteria.list(); //query.list();
        Map<Date,Map<ApplianceType,Long>> stats = new TreeMap<>();
        Date current = null;
        Map<ApplianceType,Long> day = new HashMap<>();
        for ( Object object : result ) {
            Object[] row = (Object[]) object;
            Date dateRes = (Date) row[1];
            if ( !dateRes.equals( current ) ) {
                if ( current != null ) {
                    stats.put( current, day );
                }
                day = new HashMap<>();
                current = dateRes;
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
                .add( Restrictions.eq( "enabled", true ) )
                .add( Restrictions.gt( "scheduledDate", date ) )
                .setMaxResults( 1 );
        return (Appointment) criteria.uniqueResult();
    }

    public List<Appointment> findDayAppointments( Date date ) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria( Appointment.class )
                .add( Restrictions.eq( "scheduledDate", date ) )
                .add( Restrictions.eq( "enabled", true ) )
                .addOrder( Order.asc( "scheduledTime" ) );
        return criteria.list();
    }

    public List<Appointment> findDayAppointmentsFetch( Date date ) {
        /*Criteria criteria = getSessionFactory().getCurrentSession().createCriteria( Appointment.class )
                .add( Restrictions.eq( "scheduledDate", date ) )
                .add( Restrictions.eq( "enabled", true ) )
                .addOrder( Order.asc( "scheduledTime" ) );*/
        Query query = getSessionFactory().getCurrentSession().createQuery(
                "select ap from Appointment ap join fetch ap.type tp join fetch tp.documents " +
                        "where ap.scheduledDate = ? " +
                        "and ap.enabled = true " +
                        "order by ap.scheduledTime asc"
        );
        query.setDate( 0, date )
        .setResultTransformer( Criteria.DISTINCT_ROOT_ENTITY );
        return query.list();
    }

    public List<DocumentName> findAllDocuments() {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria( DocumentName.class )
                .addOrder( Order.asc( "order" ) );
        return criteria.list();
    }

    public DocumentName findDocument( long id ) {
        return (DocumentName) getSessionFactory().getCurrentSession().get( DocumentName.class, id );
    }

    public void initializeAssociations( Appointment appointment ) {
        //getHibernateTemplate().initialize( appointment.getCampaign() );
        //getHibernateTemplate().initialize( appointment.getType() );
        getHibernateTemplate().initialize( appointment.getType().getDocuments() );
    }
}
