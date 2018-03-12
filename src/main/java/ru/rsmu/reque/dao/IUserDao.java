package ru.rsmu.reque.dao;

import ru.rsmu.reque.model.system.RemindPasswordKey;
import ru.rsmu.reque.model.system.User;
import ru.rsmu.reque.model.system.UserRole;
import ru.rsmu.reque.model.system.UserRoleName;

/**
 * @author leonid.
 */
public interface IUserDao {
    User findUser( String username );
    UserRole findRole( UserRoleName roleName );
    void saveUser( User user );

    RemindPasswordKey findRemindPasswordKey( String uniqueKey );

    void savePasswordKey( RemindPasswordKey key );
}
