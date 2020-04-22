/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.core.components;

/*====================================================================================================================*/

import com.rycerickz.deviantartdownloader.core.interfaces.RestRequestCallbackInterface;
import javafx.application.Platform;
import lombok.Getter;
import lombok.Setter;
import okhttp3.*;
import okhttp3.Request.Builder;

import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import static com.rycerickz.deviantartdownloader.MainConfiguration.IS_DEBUG;

/*====================================================================================================================*/

@Getter
@Setter
abstract public class CoreRestRequest {

    /*----------------------------------------------------------------------------------------------------------------*/

    private static final String TAG = CoreRestRequest.class.getSimpleName();

    /*----------------------------------------------------------------------------------------------------------------*/

    private OkHttpClient okHttpClient;

    private Map<String, String> headers;

    /*----------------------------------------------------------------------------------------------------------------*/

    public CoreRestRequest() {
        this.okHttpClient = new OkHttpClient();
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    protected Map<String, String> getHeaders() {
        if (headers == null) {
            headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
        }
        return headers;
    }

    protected void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    protected void get(String url, Map<String, String> params, RestRequestCallbackInterface restRequestCallbackInterface) {
        if (IS_DEBUG) {
            Logs.restUrl(url);
            Logs.restParams(params);
        }

        url += hashMapToQueryString(params);

        Request request = getBuilder(url).build();

        Call call = getOkHttpClient().newCall(request);

        callbackListener(call, restRequestCallbackInterface);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    private Builder getBuilder(String url) {
        Builder builder = new Builder()
                .url(url);

        Iterator iterator = getHeaders().entrySet().iterator();
        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            builder.addHeader(entry.getKey().toString(), entry.getValue().toString());
        }

        if (IS_DEBUG) {
            Logs.restHeaders(getHeaders());
        }

        return builder;
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    private void callbackListener(Call call, RestRequestCallbackInterface restRequestCallbackInterface) {
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException iOException) {
                Platform.runLater(() ->
                        restRequestCallbackInterface.error(call, iOException.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    byte[] bytes = response.body().bytes();
                    String bodyResponse = new String(bytes);

                    if (IS_DEBUG && Json.isValid(bodyResponse)) {
                        Logs.restResponse(bodyResponse);
                    }

                    restRequestCallbackInterface.response(call, bytes);

                    if (response.isSuccessful()) {
                        restRequestCallbackInterface.success(call, bodyResponse);

                    } else {
                        restRequestCallbackInterface.error(call, bodyResponse);
                    }

                } catch (IOException iOException) {
                    restRequestCallbackInterface.error(call, iOException.getMessage());
                }
            }
        });
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    protected static String hashMapToQueryString(Map<String, String> params) {
        StringBuilder stringBuilder = new StringBuilder();
        if (params.size() > 0) {
            stringBuilder.append("?");
        }
        int index = 0;
        for (Entry<String, String> entry : params.entrySet()) {
            stringBuilder.append(entry.getKey());
            stringBuilder.append("=");
            try {
                stringBuilder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            } catch (UnsupportedEncodingException unsupportedEncodingException) {
            }
            if (index++ != params.entrySet().size() - 1) {
                stringBuilder.append("&");
            }
        }
        return String.valueOf(stringBuilder);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

}

/*====================================================================================================================*/
