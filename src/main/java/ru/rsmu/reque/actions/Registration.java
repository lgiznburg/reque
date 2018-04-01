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
import ru.rsmu.reque.service.EmailService;
import ru.rsmu.reque.service.EmailType;
import ru.rsmu.reque.service.UserService;
import ru.rsmu.reque.validators.UserValidator;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    private EmailService emailService;

    public Registration() {
        setTitle( "Регистрация" );
        setContent( "/WEB-INF/pages/blocks/Registration.jsp" );
    }

    @ModelAttribute("userToReg")
    public User getUserToReg() {
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

        Map<String,Object> emailContext = new HashMap<>();
        emailContext.put( "user", userToReg );
        emailService.sendEmail( userToReg, EmailType.REGISTRATION_CONFIRM, emailContext );

        return "redirect:/SelectCampaign.htm";
    }

    @InitBinder
    public void initBinder( WebDataBinder binder ) {
        if ( binder.getTarget() instanceof User ) {
            binder.setValidator( userValidator );
        }
    }
}
