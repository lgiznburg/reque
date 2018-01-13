package ru.rsmu.reque.actions;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author leonid.
 */
@Controller
@RequestMapping("/home.htm")
public class Home extends BaseController {

    public Home() {
        setTitle( "Home" );
        setContent( "/WEB-INF/pages/blocks/Home.jsp" );
    }

    @RequestMapping( method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD})
    public String showHome( ModelMap model ) {
        return buildModel( model );
    }
}
