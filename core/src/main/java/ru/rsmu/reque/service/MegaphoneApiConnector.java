package ru.rsmu.reque.service;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.rsmu.reque.model.system.StoredPropertyName;
import ru.rsmu.reque.model.system.StoredPropertyType;

import java.util.Base64;

/**
 * @author leonid.
 */
@Component
public class MegaphoneApiConnector extends BaseRestfullApiConnector {

    private static final Logger log = LoggerFactory.getLogger( MegaphoneApiConnector.class );

    @Autowired
    private StoredPropertyService propertyService;

    @Override
    public String getServiceUrl() {
        return propertyService.getProperty( StoredPropertyName.MEGAPHONE_SERVICE_URL );
    }

    @Override
    public String getAuthorizationToken() {
        String login = propertyService.getProperty( StoredPropertyName.MEGAPHONE_LOGIN );
        String password = propertyService.getProperty( StoredPropertyName.MEGAPHONE_PASSWORD );
        String token = Base64.getEncoder().encodeToString( String.format( "%s:%s", login, password ).getBytes() );

        return String.format( "Basic %s", token ) ;
    }

    @Override
    public Logger getLogger() {
        return log;
    }

    public JsonObject sendSms( String body ) {
        return callMethod( propertyService.getProperty( StoredPropertyName.MEGAPHONE_SERVICE_METHOD ), body );
    }
}
