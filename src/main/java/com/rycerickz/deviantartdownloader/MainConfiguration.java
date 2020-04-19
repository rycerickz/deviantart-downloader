/*====================================================================================================================*/

package com.rycerickz.deviantartdownloader;

/*====================================================================================================================*/

public class MainConfiguration {

    /*----------------------------------------------------------------------------------------------------------------*/

    private static final String TAG = MainConfiguration.class.getSimpleName();

    /*----------------------------------------------------------------------------------------------------------------*/

    // Set the environment in which the app will launch.
    public enum ENVIROMENT {
        DEVELOPMENT,
        PRODUCTION
    }

    public static ENVIROMENT enviroment = ENVIROMENT.PRODUCTION;

    /*----------------------------------------------------------------------------------------------------------------*/

    // Enable debug mode in the app (variable).
    public static final boolean IS_DEBUG = true;

    /*----------------------------------------------------------------------------------------------------------------*/

    public static final String CUSTOMER_ID = "11922";
    public static final String CUSTOMER_SECRET = "9c7a17fad5344add0fa729f3373f4644";
    public static final String DEFAULT_USER = "steamboy33";
    public static final String DEFAULT_TERM = "Sprite mvc";

    /*----------------------------------------------------------------------------------------------------------------*/


}

/*====================================================================================================================*/
