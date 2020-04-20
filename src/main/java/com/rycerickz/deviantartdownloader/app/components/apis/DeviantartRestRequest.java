/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.app.components.apis;

/*====================================================================================================================*/

import com.rycerickz.deviantartdownloader.app.schemes.entities.ResponseToken;
import com.rycerickz.deviantartdownloader.core.components.CoreRestRequest;
import com.rycerickz.deviantartdownloader.core.interfaces.RestRequestCallbackInterface;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/*====================================================================================================================*/

@Getter
@Setter
public class DeviantartRestRequest extends CoreRestRequest {

    /*----------------------------------------------------------------------------------------------------------------*/

    private static final String TAG = DeviantartRestRequest.class.getSimpleName();

    /*----------------------------------------------------------------------------------------------------------------*/

    private static DeviantartRestRequest deviantartRestRequest;

    private static ResponseToken responseToken;

    /*----------------------------------------------------------------------------------------------------------------*/

    public enum ENTITIES {

        TOKEN("token"),
        GALLERY_ALL("gallery/all"),
        BROWSE_NEWEST("browse/newest"),
        BROWSE_POPULAR("browse/popular"),
        BROWSE_RECOMMENDED("browse/recommended");

        private final String entity;

        ENTITIES(String entity) {
            this.entity = entity;
        }

        public String getEntity() {
            return entity;
        }
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public static DeviantartRestRequest getInstance() {
        if (deviantartRestRequest == null) {
            deviantartRestRequest = new DeviantartRestRequest();
        }
        return deviantartRestRequest;
    }

    public static void setResponseToken(ResponseToken responseToken) {
        if (getInstance() != null) {
            getInstance().responseToken = responseToken;
        }
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    @Override
    protected Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        if (responseToken != null) {
            headers.put("Authorization", "Bearer " +  responseToken.getAccessToken());
        }
        return headers;
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public static String getHost() {
        return "https://www.deviantart.com/";
    }

    public static String getApi() {
        return "api/v1/oauth2/";
    }

    public static String getApiAuthentication() {
        return "oauth2/";
    }

    public static String getUrlApi() {
        return getHost() + getApi();
    }

    public static String getUrlApiAuthentication() {
        return getHost() + getApiAuthentication();
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    protected void getAuthentication(String url, Map<String, String> params, RestRequestCallbackInterface restRequestCallbackInterface) {
        super.get(getUrlApiAuthentication() + url, params, restRequestCallbackInterface);
    }

    protected void get(String url, Map<String, String> params, RestRequestCallbackInterface restRequestCallbackInterface) {
        super.get(getUrlApi() + url, params, restRequestCallbackInterface);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public void getToken(Map<String, String> params, RestRequestCallbackInterface restRequestCallbackInterface) {
        getAuthentication(ENTITIES.TOKEN.getEntity(), params, restRequestCallbackInterface);
    }

    public void getGalleryAll(Map<String, String> params, RestRequestCallbackInterface restRequestCallbackInterface) {
        get(ENTITIES.GALLERY_ALL.getEntity(), params, restRequestCallbackInterface);
    }

    public void getBrowseNewest(Map<String, String> params, RestRequestCallbackInterface restRequestCallbackInterface) {
        get(ENTITIES.BROWSE_NEWEST.getEntity(), params, restRequestCallbackInterface);
    }

    public void getBrowsePopular(Map<String, String> params, RestRequestCallbackInterface restRequestCallbackInterface) {
        get(ENTITIES.BROWSE_POPULAR.getEntity(), params, restRequestCallbackInterface);
    }

    public void getBrowseRecommended(Map<String, String> params, RestRequestCallbackInterface restRequestCallbackInterface) {
        get(ENTITIES.BROWSE_RECOMMENDED.getEntity(), params, restRequestCallbackInterface);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

}

/*====================================================================================================================*/
