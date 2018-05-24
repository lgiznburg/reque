package ru.rsmu.reque.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.rsmu.reque.model.system.User;

/**
 * @author leonid.
 */
public class SecurityContextHelper {
    public static User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object user = authentication.getPrincipal();
            if (user instanceof User)
                return (User) user;
        }
        return null;
    }

    public static void authenticateUser( User user ) {

        UsernamePasswordAuthenticationToken t = new UsernamePasswordAuthenticationToken(
                user, user.getPassword(), user.getAuthorities() );
        SecurityContextHolder.getContext().setAuthentication( t );

    }
}
