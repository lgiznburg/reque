package ru.rsmu.reque.actions;

import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.rsmu.reque.model.system.User;
import ru.rsmu.reque.utils.SecurityContextHelper;

/**
 * @author leonid.
 */
public class BaseController {
    public static final String CONTENT = "content";
    public static final String TITLE = "title";

    protected String mainTemplate = "/templates/MainTemplate";

    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent( String content ) {
        this.content = content;
    }

    @ModelAttribute("user")
    public User getUser() {
        return SecurityContextHelper.getUser();
    }

    public String buildModel( ModelMap model ) {
        if ( StringUtils.isEmpty( model.get( CONTENT ) ) && !StringUtils.isEmpty( content ) ) {
            model.addAttribute( CONTENT, content );
        }
        if ( StringUtils.isEmpty( model.get( TITLE ) ) && !StringUtils.isEmpty( title ) ) {
            model.addAttribute( TITLE, title );
        }

        return mainTemplate;
    }
}
