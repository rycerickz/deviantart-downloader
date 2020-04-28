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

    public static final String PATTERN_ILLEGAL_CHARACTERS = "[\\\\/:*?\"<>|]";

    /*----------------------------------------------------------------------------------------------------------------*/

    public static final String DEFAULT_CUSTOMER_ID = "11922";
    public static final String DEFAULT_CUSTOMER_SECRET = "9c7a17fad5344add0fa729f3373f4644";
    public static final String DEFAULT_TERM = "steamboy33";
    public static final String DEFAULT_DOWNLOAD_FOLDER = "descargas\\";

    public static final int DEFAULT_OFFSET_MAXIMUM = 500;
    public static final int DEFAULT_THREADS = 10;

    /*----------------------------------------------------------------------------------------------------------------*/


}

/*====================================================================================================================*/
