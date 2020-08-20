package com.upit.coex.user.constants;

/**
 * Created by chien.lx on 3/9/2020.
 */

public class CommonConstants {
    public static final String COMMON_DATE_FORMAT = "dd-MMM-yyyy h:mm a";

//    public static String BASE_URL = "http://192.168.0.6:3000/";
//    public static String BASE_URL = "https://coextest.herokuapp.com/";
    public static String BASE_URL = "http://34.87.108.104:3000/";

    public static final String PREFS_NAME = "coex_pref";

    public static final int NUMBER_OF_THREAD_IN_POOL = 5;

    public static final String COMMON_COEX_EXECUTOR_TAG = "COEX_EXECUTOR";

    public static final String COEX_FIREBASE = "COEX_FIREBASE";
    public static final String IMAGE_LINK_BASE = BASE_URL + "images/";

    public static enum SCREEN {
        LOGIN,

        FORGOTPASSWORD,
        MAP,
        PROFILE
    }
}
