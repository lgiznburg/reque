package ru.rsmu.reque.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.rsmu.reque.dao.UserDao;
import ru.rsmu.reque.model.system.User;
import ru.rsmu.reque.service.UserService;
import ru.rsmu.reque.validators.UserValidator;

import javax.validation.Valid;

/**
 * @author leonid.
 */
@Controller
@RequestMapping(value = "/Registration.htm")
public class Registration extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    public Registration() {
        setTitle( "Registration" );
        setContent( "/WEB-INF/pages/blocks/Registration.jsp" );
    }

    @ModelAttribute("userToReg")
    public User getUser() {
        return new User();
    }

    @RequestMapping( method = {RequestMethod.GET, RequestMethod.HEAD})
    public String showForm( ModelMap model ) {
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

        return "redirect:/SelectCampaign.htm";
    }

    @InitBinder
    public void initBinder( WebDataBinder binder ) {
        if ( binder.getTarget() instanceof User ) {
            binder.setValidator( userValidator );
        }
    }
}
