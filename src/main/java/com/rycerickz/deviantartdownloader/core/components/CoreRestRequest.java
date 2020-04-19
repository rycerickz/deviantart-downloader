/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.core.components;

/*====================================================================================================================*/

import com.rycerickz.deviantartdownloader.core.interfaces.RestRequestCallbackInterface;
import javafx.application.Platform;
import lombok.Getter;
import lombok.Setter;
import okhttp3.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

    /*----------------------------------------------------------------------------------------------------------------*/

    public CoreRestRequest() {
        this.okHttpClient = new OkHttpClient();
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public void get(String url, Map<String, String> params, RestRequestCallbackInterface restRequestCallbackInterface) {
        if (IS_DEBUG) {
            System.out.println(url);
        }

        Request request = new Request
                .Builder()
                .url(url + hashMapToQueryString(params))
                .build();

        Call call = getOkHttpClient().newCall(request);

        callbackListener(call, restRequestCallbackInterface);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    private void callbackListener(Call call, RestRequestCallbackInterface restRequestCallbackInterface) {
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException iOException) {
                Platform.runLater(() -> restRequestCallbackInterface.error(call, iOException));
            }

            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    try {
                        String bodyResponse = response.body().string();
                        Platform.runLater(() ->
                                restRequestCallbackInterface.success(call, bodyResponse));

                    } catch (IOException iOException) {
                        Platform.runLater(() ->
                                restRequestCallbackInterface.error(call, iOException));
                    }

                } else {
                    Platform.runLater(() ->
                            restRequestCallbackInterface.error(call, new Exception(response.message())));
                }
            }
        });
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public static String hashMapToQueryString(Map<String, String> params) {
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
