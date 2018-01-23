package ru.rsmu.reque.dao;

import ru.rsmu.reque.model.system.User;
import ru.rsmu.reque.model.system.UserRole;
import ru.rsmu.reque.model.system.UserRoleName;

/**
 * @author leonid.
 */
public interface IUserDao {
    public User findUser( String username );
    public UserRole findRole( UserRoleName roleName );
    public void saveUser( User user );
}
