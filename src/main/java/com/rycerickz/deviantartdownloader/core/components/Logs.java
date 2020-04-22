/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.core.components;

/*====================================================================================================================*/

import com.rycerickz.deviantartdownloader.app.schemes.entities.Document;

import java.util.Map;

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

    public static void document(Document document) {
        if (document == null || document.getContent() == null || !Is.validString(document.getContent().getSrc())) {
            System.err.println("DOCUMENT => " + document.getIdDeviation());

        } else {
            System.out.println("DOCUMENT => " + document.getTitle() + " => " + document.getContent().getSrc());
        }
    }

    /*----------------------------------------------------------------------------------------------------------------*/

}

/*====================================================================================================================*/
