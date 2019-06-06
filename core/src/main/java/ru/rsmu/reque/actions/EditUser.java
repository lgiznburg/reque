package ru.rsmu.reque.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import ru.rsmu.reque.editor.DateTimeEditor;
import ru.rsmu.reque.model.system.AdditionalUserInfo;
import ru.rsmu.reque.model.system.User;
import ru.rsmu.reque.service.UserService;
import ru.rsmu.reque.validators.UserValidator;

import javax.validation.Valid;
import java.util.Date;

/**
 * @author leonid.
 */
@Controller
@RequestMapping(value = "/EditUser.htm")
public class EditUser extends BaseController {

    @Value("${system.useAdditionalInfo:0}")
    private int useAdditionalInfo;

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
        User user = getUser();
        if ( useAdditionalInfo > 0 && user.getAdditionalUserInfo() == null ) {
            user.setAdditionalUserInfo( new AdditionalUserInfo() );
            user.getAdditionalUserInfo().setUser( user );
        }

        return user;
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
        binder.registerCustomEditor( Date.class, new DateTimeEditor() );
        if ( binder.getTarget() instanceof User ) {
            binder.setValidator( userValidator );
        }
    }


}
