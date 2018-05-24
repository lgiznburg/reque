package ru.rsmu.reque.actions;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.rsmu.reque.dao.IUserDao;
import ru.rsmu.reque.model.system.RemindPasswordKey;
import ru.rsmu.reque.model.system.User;
import ru.rsmu.reque.service.UserService;
import ru.rsmu.reque.validators.UserValidator;

import javax.validation.Valid;

/**
 * @author leonid.
 */
@Controller
@RequestMapping(value = "/ChangePassword.htm")
public class ChangePassword extends BaseController {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    public ChangePassword() {
        setTitle( "Измененить пароль" );
        setContent( "/WEB-INF/pages/blocks/ChangePassword.jsp" );
    }

    @ModelAttribute("userToReg")
    public User getUserToReg( @RequestParam(value = "key", required = false) String key ) {
        User current = getUser();
        if ( StringUtils.isNotBlank( key ) && current == null ) {
            RemindPasswordKey remindPasswordKey = userDao.findRemindPasswordKey( key );
            if ( remindPasswordKey != null ) {
                return remindPasswordKey.getUser();
            }
        }
        return current;
    }


    @RequestMapping( method = {RequestMethod.GET, RequestMethod.HEAD})
    public String showForm( ModelMap model,
                            @ModelAttribute("userToReg") User userToReg ) {
        if ( userToReg == null ) {
            return "redirect:/home.htm";
        }
        return buildModel( model );
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveUser( ModelMap model,
                            @ModelAttribute("userToReg") @Valid User userToReg,
                            BindingResult errors ) {
        if ( errors.hasErrors() ) {
            return buildModel( model );
        }
        userService.saveUser( userToReg );

        UsernamePasswordAuthenticationToken t = new UsernamePasswordAuthenticationToken(
                userToReg, userToReg.getPassword(), userToReg.getAuthorities() );
        SecurityContextHolder.getContext().setAuthentication( t );

        return "redirect:/home.htm";
    }

    @InitBinder
    public void initBinder( WebDataBinder binder ) {
        if ( binder.getTarget() instanceof User ) {
            binder.setValidator( userValidator );
        }
    }

}
