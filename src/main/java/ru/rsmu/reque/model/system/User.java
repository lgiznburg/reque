package ru.rsmu.reque.model.system;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author leonid.
 */
@Entity
@Table(name = "users")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String username;

    @Column
    private String password;

    @ManyToMany
    @JoinTable( name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private List<UserRole> userRoles;

    @Column
    private boolean enabled = true;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "last_updated")
    private Date lastUpdated;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setUsername( String username ) {
        this.username = username;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    public void setEnabled( boolean enabled ) {
        this.enabled = enabled;
    }

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles( List<UserRole> userRoles ) {
        this.userRoles = userRoles;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime( Date createdTime ) {
        this.createdTime = createdTime;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated( Date lastUpdated ) {
        this.lastUpdated = lastUpdated;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName( String firstName ) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName( String lastName ) {
        this.lastName = lastName;
    }
}
