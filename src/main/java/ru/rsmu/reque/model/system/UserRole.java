package ru.rsmu.reque.model.system;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author leonid.
 */

@Entity
@Table(name = "roles")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class UserRole implements GrantedAuthority, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private UserRoleName roleName;

    @Override
    public String getAuthority() {
        return roleName.name();
    }

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public UserRoleName getRoleName() {
        return roleName;
    }

    public void setRoleName( UserRoleName roleName ) {
        this.roleName = roleName;
    }
}
