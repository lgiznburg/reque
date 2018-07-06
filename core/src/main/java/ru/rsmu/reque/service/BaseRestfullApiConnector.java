package ru.rsmu.reque.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author leonid.
 */
public abstract class BaseRestfullApiConnector {
    abstract public String getServiceUrl();
    abstract public String getAuthorizationToken();
    abstract public Logger getLogger();

    // return formatted JSON object from the API
    private boolean debugFormattedJSON;

    // always return the latest version of the data (ignore cache)
    private boolean debugNoCache;


    public boolean isDebugFormattedJSON() {
        return debugFormattedJSON;
    }

    public void setDebugFormattedJSON(boolean debugFormattedJSON) {
        this.debugFormattedJSON = debugFormattedJSON;
    }

    public boolean isDebugNoCache() {
        return debugNoCache;
    }

    public void setDebugNoCache(boolean debugNoCache) {
        this.debugNoCache = debugNoCache;
    }


    /**
     * Do RESTFul API call
     * @param methodPath Method path
     * @param body Body for post call
     * @return JsonObject if there is no error
     */
    public JsonObject callMethod(String methodPath, String body ) {


        JsonObject result = null;

        UriComponentsBuilder callingUrl = UriComponentsBuilder.fromHttpUrl( getServiceUrl() );

        callingUrl.path( "/" + methodPath );

        PostMethod method = new PostMethod( callingUrl.build().encode().toUriString() );
        try {
            method.setRequestEntity( new StringRequestEntity( body, "application/json", "UTF-8" ) );
        } catch (UnsupportedEncodingException e) {
            getLogger().error( "Cant create POST request body", e );
        }
        method.addRequestHeader( "Authorization", getAuthorizationToken() );
        if (this.debugFormattedJSON) {
            method.addRequestHeader( "Accept", "application/json; indent=4" );
        } else {
            method.addRequestHeader( "Accept", "application/json" );
        }

        if (this.debugNoCache) {
            method.addRequestHeader( "Cache-Control", "no-cache" );
        }

        HttpClient httpClient = new HttpClient();

        int statusCode = 0;
        try {
            statusCode = httpClient.executeMethod( method );

            JsonParser parser = new JsonParser();
            if ( statusCode == HttpStatus.SC_OK ) {
                result = parser.parse( new InputStreamReader( method.getResponseBodyAsStream(), "UTF-8" ) ).getAsJsonObject();
            } else {
                getLogger().error( String.format( "Unable to get response from RESTfull API; HTTP status \"%d\";  Requested: \"%s\"", statusCode, method.getURI().toString() ) );
            }
        } catch (IOException e) {
            getLogger().error( "Connection error with RESTfull API", e );
        } catch (JsonParseException e) {
            getLogger().error( String.format( "Unable to get response from RESTfull API; HTTP status \"%d\"", statusCode ), e );
        }
        return result;
    }
}
