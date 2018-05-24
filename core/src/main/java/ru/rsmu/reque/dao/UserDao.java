package ru.rsmu.reque.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import ru.rsmu.reque.model.system.RemindPasswordKey;
import ru.rsmu.reque.model.system.User;
import ru.rsmu.reque.model.system.UserRole;
import ru.rsmu.reque.model.system.UserRoleName;

import java.util.Date;

/**
 * @author leonid.
 */
@Repository("userDao")
public class UserDao extends CommonDao implements UserDetailsService, IUserDao {

    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria( User.class )
                .add( Restrictions.eq( "username", username ) )
                .setMaxResults( 1 );
        User user = (User) criteria.uniqueResult();
        if ( user == null ) {
            throw new UsernameNotFoundException( "" );
        }
        return user;
    }

    public User findUser( String username ) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria( User.class )
                .add( Restrictions.eq( "username", username ) )
                .setMaxResults( 1 );
        return  (User) criteria.uniqueResult();
    }

    public UserRole findRole( UserRoleName roleName ) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria( UserRole.class )
                .add( Restrictions.eq( "roleName", roleName ) )
                .setMaxResults( 1 );
        return (UserRole) criteria.uniqueResult();
    }

    public void saveUser( User user ) {
        saveEntity( user );
    }

    @Override
    public RemindPasswordKey findRemindPasswordKey( String uniqueKey ) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria( RemindPasswordKey.class )
                .add( Restrictions.eq( "uniqueKey", uniqueKey ) )
                .add( Restrictions.gt( "expiredTime", new Date()) )
                .setMaxResults( 1 );
        return  (RemindPasswordKey) criteria.uniqueResult();
    }

    @Override
    public void savePasswordKey( RemindPasswordKey key ) {
        saveEntity( key );
    }
}
