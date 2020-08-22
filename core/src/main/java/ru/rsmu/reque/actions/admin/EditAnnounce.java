package ru.rsmu.reque.actions.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.rsmu.reque.actions.BaseController;
import ru.rsmu.reque.dao.SystemDao;
import ru.rsmu.reque.model.system.HomePageAnnounce;

import javax.validation.Valid;
import java.util.Locale;

/**
 * @author leonid.
 */
@Controller
@RequestMapping(value = "/admin/EditAnnounce.htm")
public class EditAnnounce extends BaseController {

    @Autowired
    private SystemDao systemDao;

    public EditAnnounce() {
        setTitle( "Редактировать объявление" );
        setContent( "/WEB-INF/pages/blocks/admin/EditAnnounce.jsp" );
    }

    @ModelAttribute("announce")
    public HomePageAnnounce getAnnounce() {
        Locale locale = LocaleContextHolder.getLocale();
        HomePageAnnounce announce = systemDao.findAnnounce( locale.getLanguage() );
        return  announce != null ? announce : new HomePageAnnounce();
    }


    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD})
    public String showPage( ModelMap model ) {
        return buildModel( model );
    }

    @RequestMapping(method = RequestMethod.POST)
    public String showPage( ModelMap model,
                            @ModelAttribute("announce") @Valid HomePageAnnounce announce,
                            BindingResult errors ) {
        if ( announce == null ) {
            return "redirect:/home.htm";
        }
        if ( errors.hasErrors() ) {
            return buildModel( model );
        }
        Locale locale = LocaleContextHolder.getLocale();
        announce.setLocale( locale.getLanguage() );
        systemDao.saveEntity( announce );

        return "redirect:/home.htm";
    }
}
