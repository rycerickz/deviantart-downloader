/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.app.components.apis;

/*====================================================================================================================*/

import com.rycerickz.deviantartdownloader.core.components.CoreRestRequest;
import com.rycerickz.deviantartdownloader.core.interfaces.RestRequestCallbackInterface;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/*====================================================================================================================*/

@Getter
@Setter
public class DeviantartRestRequest extends CoreRestRequest {

    /*----------------------------------------------------------------------------------------------------------------*/

    private static final String TAG = DeviantartRestRequest.class.getSimpleName();

    /*----------------------------------------------------------------------------------------------------------------*/

    private static DeviantartRestRequest deviantartRestRequest;

    /*----------------------------------------------------------------------------------------------------------------*/

    public enum ENTITIES {

        TOKEN("token"),
        GALLERY_ALL("gallery/all");

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

    /*----------------------------------------------------------------------------------------------------------------*/

    public static String getHost() {
        return "https://www.deviantart.com/";
    }

    public static String getApi() {
        return "oauth2/";
    }

    public static String getUrlApi() {
        return getHost() + getApi();
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public void get(String url, Map<String, String> params, RestRequestCallbackInterface restRequestCallbackInterface) {
        super.get(getUrlApi() + url, params, restRequestCallbackInterface);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public void getToken(Map<String, String> params, RestRequestCallbackInterface restRequestCallbackInterface) {
        get(ENTITIES.TOKEN.getEntity(), params, restRequestCallbackInterface);
    }

    public void getGalleryAll(Map<String, String> params, RestRequestCallbackInterface restRequestCallbackInterface) {
        get(ENTITIES.GALLERY_ALL.getEntity(), params, restRequestCallbackInterface);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

}

/*====================================================================================================================*/
