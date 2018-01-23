package ru.rsmu.reque.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.rsmu.reque.dao.IUserDao;
import ru.rsmu.reque.dao.UserDao;
import ru.rsmu.reque.model.system.User;
import ru.rsmu.reque.model.system.UserRole;
import ru.rsmu.reque.model.system.UserRoleName;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author leonid.
 */
@Service
public class UserService {

    @Autowired
    private IUserDao userDao;


    public void saveUser( User user ) {
        user.setPassword( encrypt( user.getPassword() ) );
        UserRole role = userDao.findRole( UserRoleName.ROLE_CLIENT );
        user.setUserRoles( new ArrayList<>() );
        user.getUserRoles().add( role );
        user.setEnabled( true );
        user.setLastUpdated( new Date() );
        userDao.saveUser( user );
    }


    public static String encrypt( String password ) {
        try {
            MessageDigest md = MessageDigest.getInstance( "MD5" );
            md.update( password.getBytes() );
            BigInteger hash = new BigInteger( 1, md.digest() );
            String passwordHash = hash.toString( 16 );
            while ( passwordHash.length() < 32 ) passwordHash = "0" + passwordHash;
            return passwordHash;
        } catch ( NoSuchAlgorithmException e ) {
            throw new RuntimeException( e );
        }
    }

    public static String generatePassword() {
        StringBuilder password = new StringBuilder();
        final int PASSWORD_LENGHT = 6;

        for ( int i = 0; i < PASSWORD_LENGHT; ) {
            char c = (char) (Math.random() * 255);
            if ( (c >= 'A' && c <= 'Z' && c != 'I' && c != 'O') || (c >= 'a' && c <= 'z' && c != 'l' && c != 'o') || (c >= '2' && c <= '9') ) {
                password.append( c );
                i++;
            }
        }
        if ( !password.toString().matches( ".*[0-9].*[0-9].*" ) ) {
            int[] ind = new int[2];
            ind[1] = 1 + (int) (Math.random() * PASSWORD_LENGHT - 2);
            ind[0] = (int) (Math.random() * (ind[1] - 1));
            for ( int i = 0; i < 2; i++ ) {
                char c = 0;
                while ( !(c >= '2' && c <= '9') ) {
                    c = (char) (Math.random() * 255);
                }
                password.setCharAt( ind[i], c );
            }
        }
        if ( !password.toString().matches( ".*[a-zA-Z].*[a-zA-Z].*" ) ) {
            int[] ind = new int[2];
            ind[1] = 1 + (int) (Math.random() * PASSWORD_LENGHT - 2);
            ind[0] = (int) (Math.random() * (ind[1] - 1));
            for ( int i = 0; i < 2; i++ ) {
                char c = 0;
                while ( !((c >= 'A' && c <= 'Z' && c != 'I' && c != 'O') || (c >= 'a' && c <= 'z' && c != 'l' && c != 'o')) ) {
                    c = (char) (Math.random() * 255);
                }
                password.setCharAt( ind[i], c );
            }
        }
        return password.toString();
    }
}
