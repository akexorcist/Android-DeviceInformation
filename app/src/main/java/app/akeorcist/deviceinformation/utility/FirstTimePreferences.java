package app.akeorcist.deviceinformation.utility;

import android.content.Context;
import android.content.SharedPreferences;

import app.akeorcist.deviceinformation.constants.Constants;

/**
 * Created by Ake on 2/25/2015.
 */
public class FirstTimePreferences {
    private final static String PREFERENCE_FIRST_TIME = "first_time_pref";

    private final static String KEY_FIRST_RUN = "first_run";
    private final static String KEY_FIRST_PAGE = "first_page";
    private final static String KEY_DEVICE_LIST = "device_list";
    private final static String KEY_SUB_DEVICE_LIST = "sub_device_list";
    private final static String KEY_SENT_SD_INFO = "sent_sd_info";

    private static void setFirstTimePreference(Context context, String key, boolean value) {
        SharedPreferences.Editor editor =  getFirstTimePreference(context).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static SharedPreferences getFirstTimePreference(Context context) {
        return context.getSharedPreferences(PREFERENCE_FIRST_TIME, Context.MODE_PRIVATE);
    }

    public static boolean isFirstRun(Context context) {
        return getFirstTime(context, KEY_FIRST_RUN);
    }

    public static boolean isDeviceList(Context context) {
        return getFirstTime(context, KEY_DEVICE_LIST);
    }

    public static boolean isFirstPage(Context context) {
        return getFirstTime(context, KEY_FIRST_PAGE);
    }

    public static void clearFirstPage(Context context) {
        setFirstTimePreference(context, KEY_FIRST_PAGE, true);
    }

    public static boolean isSubDeviceList(Context context) {
        return getFirstTime(context, KEY_SUB_DEVICE_LIST);
    }

    public static boolean hasSentSDInfo(Context context) {
        return getFirstTime(context, KEY_SENT_SD_INFO);
    }

    private static boolean getFirstTime(Context context, String key) {
        boolean isFirst = getFirstTimePreference(context).getBoolean(key, true);
        if(isFirst)
            setFirstTimePreference(context, key, false);
        return isFirst;
    }



}

