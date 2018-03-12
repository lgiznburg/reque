package ru.rsmu.reque.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rsmu.reque.dao.IUserDao;
import ru.rsmu.reque.model.system.RemindPasswordKey;
import ru.rsmu.reque.model.system.User;
import ru.rsmu.reque.service.EmailService;
import ru.rsmu.reque.service.EmailType;
import ru.rsmu.reque.service.UserService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author leonid.
 */
@Controller
@RequestMapping(value = "/RemindPassword.htm")
public class RemindPassword {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    private static final String VIEW_JSP = "/RemindPassword";


    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD})
    public String showPage( ModelMap model ) {
        return VIEW_JSP;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String sendMessage( ModelMap model,
                               @RequestParam(value = "email",required = false, defaultValue = "") String email ) {
        User user = userDao.findUser( email );
        if ( user != null ) {
            RemindPasswordKey key = userService.createRemindKey( user );

            userDao.savePasswordKey( key );

            Map<String,Object> emailContext = new HashMap<>();
            emailContext.put( "user", user );
            emailContext.put( "key", key );
            emailService.sendEmail( user, EmailType.REGISTRATION_CONFIRM, emailContext );

            model.put( "success", true );
        }
        else {
            model.put( "userNotFound", true );
        }
        return VIEW_JSP;
    }
}
