package ru.rsmu.reque.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.rsmu.reque.model.system.User;
import ru.rsmu.reque.service.UserService;
import ru.rsmu.reque.validators.UserValidator;

import javax.validation.Valid;

/**
 * @author leonid.
 */
@Controller
@RequestMapping(value = "/EditUser.htm")
public class EditUser extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    public EditUser() {
        setTitle( "Изменить данные" );
        setContent( "/WEB-INF/pages/blocks/EditUser.jsp" );
    }

    @ModelAttribute("userToReg")
    public User getUserToReg() {
        return getUser();
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
