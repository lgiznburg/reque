package ru.rsmu.reque.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import ru.rsmu.reque.model.system.User;

/**
 * @author leonid.
 */
@Repository
public class UserDao extends CommonDao implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria( User.class )
                .add( Restrictions.eq( "username", username ) )
                .setMaxResults( 1 );
        return (UserDetails) criteria.uniqueResult();
    }
}
