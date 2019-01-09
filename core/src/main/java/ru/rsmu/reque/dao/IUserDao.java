package ru.rsmu.reque.dao;

import ru.rsmu.reque.model.system.*;

/**
 * @author leonid.
 */
public interface IUserDao {
    User findUser( String username );
    UserRole findRole( UserRoleName roleName );
    void saveUser( User user );

    RemindPasswordKey findRemindPasswordKey( String uniqueKey );

    void savePasswordKey( RemindPasswordKey key );

    void saveAdditionalInfo( AdditionalUserInfo additionalUserInfo );
}
