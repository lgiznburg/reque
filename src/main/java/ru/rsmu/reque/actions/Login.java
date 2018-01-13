package ru.rsmu.reque.actions;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author leonid.
 */
@Controller
public class Login {

    @RequestMapping(value = "/login.htm")
    public String login( ModelMap model,
                         @RequestParam(value = "error", required = false) String error,
                         @RequestParam(value = "logout", required = false) String logout ) {
        if ( error != null ) {
            model.addAttribute( "error", "Invalid username and password!" );
        }
        return "/login";
    }
}
