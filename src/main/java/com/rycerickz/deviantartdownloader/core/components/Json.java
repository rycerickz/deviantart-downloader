/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.core.components;

/*====================================================================================================================*/

import com.google.gson.*;

import java.util.ArrayList;
import java.util.Arrays;

/*====================================================================================================================*/

public class Json {

    /*----------------------------------------------------------------------------------------------------------------*/

    private static final String TAG = Json.class.getSimpleName();

    /*----------------------------------------------------------------------------------------------------------------*/

    public static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    /*----------------------------------------------------------------------------------------------------------------*/

    private static Gson gson;

    /*----------------------------------------------------------------------------------------------------------------*/

    public static GsonBuilder getGsonBuilder() {
        return new GsonBuilder().setDateFormat(DATE_FORMAT);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public static Gson getInstance() {
        if (gson == null) {
            gson = getGsonBuilder().create();
        }
        return gson;
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public static String toString(Object object) {
        return getInstance().toJson(object);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public static <T> T parse(Class<T> schema, String json) {
        return getInstance().fromJson(json, schema);
    }

    public static <T> ArrayList<T> parseList(final Class<T[]> schema, String json) {
        T[] list = parse(schema, json);
        return new ArrayList<>(Arrays.asList(list));
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public static JsonElement jsonParser(String json) {
        return new JsonParser().parse(json);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public static boolean isValid(String Json) {
        try {
            JsonParser jsonParser = new JsonParser();
            jsonParser.parse(Json);
            return true;

        } catch (JsonSyntaxException jsonSyntaxException) {
            return false;
        }
    }

    /*----------------------------------------------------------------------------------------------------------------*/

}

/*====================================================================================================================*/
