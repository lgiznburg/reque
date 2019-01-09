package ru.rsmu.reque.validators;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.rsmu.reque.dao.IUserDao;
import ru.rsmu.reque.model.system.User;

/**
 * @author leonid.
 */
@Component
public class UserValidator implements Validator {

    @Value("${system.useAdditionalInfo:0}")
    private int useAdditionalInfo;

    @Autowired
    private Validator validator;

    @Autowired
    private IUserDao userDao;

    @Override
    public boolean supports( Class<?> aClass ) {
        return User.class.isAssignableFrom( aClass );
    }

    @Override
    public void validate( Object object, Errors errors ) {
        ValidationUtils.invokeValidator( validator, object, errors );

        User user = (User) object;

        if ( useAdditionalInfo > 0 ) {
            errors.pushNestedPath( "additionalUserInfo" );
            ValidationUtils.invokeValidator( validator, user.getAdditionalUserInfo(), errors );
            errors.popNestedPath();
            ValidationUtils.rejectIfEmptyOrWhitespace( errors, "phoneNumber", "NotBlank" );
        }

        if ( !errors.hasFieldErrors( "username" ) && user.getId() == 0 ) {
            User existed = userDao.findUser( user.getUsername() );
            if ( existed != null ) {
                errors.rejectValue( "username", "user.registration.already_exist", "User already exist." );
            }
        }

        if ( user.getPassword().length() < 32
                && !user.getPassword().equals( user.getPasswordConfirmation() )) {
            errors.rejectValue( "passwordConfirmation", "user.registration.passwConfirm", "Password confirmation should match password" );
        }

        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        if ( user.getPhoneNumber() != null && !user.getPhoneNumber().isEmpty() ) {
            try {
                Phonenumber.PhoneNumber phone = phoneNumberUtil.parse( user.getPhoneNumber(), "RU" );
                if ( !phoneNumberUtil.isValidNumber( phone ) ) {
                    errors.rejectValue( "phoneNumber", "user.registration.phone.wrong_format", "Wrong format" );
                }
            } catch (NumberParseException e) {
                errors.rejectValue( "phoneNumber", "user.registration.phone.wrong_format", "Wrong format" );
            }
        }
    }
}
