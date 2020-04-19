/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.core.components;

/*====================================================================================================================*/

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.hildan.fxgson.FxGson;

import java.util.ArrayList;
import java.util.Arrays;

/*====================================================================================================================*/

public class Json {

    /*----------------------------------------------------------------------------------------------------------------*/

    private static final String TAG = Json.class.getSimpleName();

    /*----------------------------------------------------------------------------------------------------------------*/

    public static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    /*----------------------------------------------------------------------------------------------------------------*/

    public static GsonBuilder getGsonBuilder() {
        return new GsonBuilder().setDateFormat(DATE_FORMAT);
    }

    public static GsonBuilder getFxGsonBuilder() {
        return FxGson.addFxSupport(getGsonBuilder());
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public static Gson getGson() {
        return getGsonBuilder().create();
    }

    public static Gson getFxGson() {
        return getFxGsonBuilder().create();
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public static String toString(Object object) {
        return getGson().toJson(object);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public static <T> T parse(Class<T> schema, String json) {
        return getGson().fromJson(json, schema);
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

}

/*====================================================================================================================*/
