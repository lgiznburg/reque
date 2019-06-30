package ru.rsmu.reque.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ru.rsmu.reque.model.registration.ReceptionCampaign;

import java.util.Date;
import java.util.List;

/**
 * @author leonid.
 */
@Repository
public class ReceptionCampaignDao extends CommonDao {

    public ReceptionCampaign findCampaign( long id ) {
        return findEntity( ReceptionCampaign.class, id );
    }

    public List<ReceptionCampaign> findAvailableCampaigns( Date date ) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria( ReceptionCampaign.class )
                .add( Restrictions.gt( "endDate", date ) )
                .add( Restrictions.eq( "active", true ) )
                .setResultTransformer( CriteriaSpecification.DISTINCT_ROOT_ENTITY );
        return  criteria.list();
    }

    public List<ReceptionCampaign> findAll() {
        return findAllEntities( ReceptionCampaign.class );
    }

    public ReceptionCampaign findConcurrentCampaign( ReceptionCampaign campaign, Date date ) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria( ReceptionCampaign.class )
                .add( Restrictions.lt( "priority", campaign.getPriority() ) )
                .add( Restrictions.ge( "endDate", date ) )
                .add( Restrictions.eq( "active", true ) )
                .addOrder( Order.desc( "endDate" ) )
                .setResultTransformer( CriteriaSpecification.DISTINCT_ROOT_ENTITY );
        List<ReceptionCampaign> campaigns = criteria.list();
        if ( campaigns.size() > 0 ) {
            return campaigns.get( 0 );
        }
        return null;
    }
}
