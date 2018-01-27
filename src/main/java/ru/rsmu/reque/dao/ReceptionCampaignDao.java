package ru.rsmu.reque.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ru.rsmu.reque.model.registration.ReceptionCampaign;

import java.awt.image.RescaleOp;
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
                .add( Restrictions.gt( "endDate", date ) );
        return  criteria.list();
    }

    public List<ReceptionCampaign> findAll() {
        return findAllEntities( ReceptionCampaign.class );
    }

}
