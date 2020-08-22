package ru.rsmu.reque.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ru.rsmu.reque.model.system.HomePageAnnounce;

/**
 * @author leonid.
 */
@Repository
public class SystemDao extends CommonDao {
    public HomePageAnnounce findAnnounce( String language ) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria( HomePageAnnounce.class )
                .add( Restrictions.eq( "locale", language ) )
                .setMaxResults( 1 );
        return (HomePageAnnounce) criteria.uniqueResult();
    }
}
