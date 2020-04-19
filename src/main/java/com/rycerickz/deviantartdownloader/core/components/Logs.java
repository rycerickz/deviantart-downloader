/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.core.components;

/*====================================================================================================================*/

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/*====================================================================================================================*/

public class Logs {

    /*----------------------------------------------------------------------------------------------------------------*/

    private static final String TAG = Logs.class.getSimpleName();

    /*----------------------------------------------------------------------------------------------------------------*/

    protected static void restUrl(String url) {
      System.out.println("REST URL => " + url);
    }

    protected static void restParams(Map<String, String> params) {
        System.out.println("REST PARAMS => " + params.toString());
    }

    protected static void restHeaders(Map<String, String> headers) {
        System.out.println("REST HEADERS => " + headers.toString());
    }

    protected static void restResponse(String response) {
        System.out.println("REST RESPONSE => " + response);
    }

    public static void exception(Exception exception) {
        System.err.println("EXCEPTION => " + exception.getLocalizedMessage());
    }

    public static void error(String error) {
        System.err.println("ERROR => " + error);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

}

/*====================================================================================================================*/
