/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader.core.components;

/*====================================================================================================================*/

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;

/*====================================================================================================================*/

public class Is {

    /*----------------------------------------------------------------------------------------------------------------*/

    private static final String TAG = Is.class.getSimpleName();

    /*----------------------------------------------------------------------------------------------------------------*/

    public static boolean validString(String string) {
        return !StringUtils.isEmpty(string);
    }

    public static boolean validList(List list) {
        return (list != null) && (!list.isEmpty());
    }

    public static boolean validHashMap(HashMap<?, ?> hashMap) {
        return (hashMap != null) && (!hashMap.isEmpty());
    }

    /*----------------------------------------------------------------------------------------------------------------*/

}

/*====================================================================================================================*/
