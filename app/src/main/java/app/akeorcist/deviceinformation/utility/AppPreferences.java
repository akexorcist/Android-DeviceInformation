package app.akeorcist.deviceinformation.utility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import app.akeorcist.deviceinformation.constants.Constants;

/**
 * Created by Ake on 2/25/2015.
 */
public class AppPreferences {
    private final static String PREFERENCE_APP = "app_pref";

    private final static String KEY_APP_VALIDATED = "is_validated";
    private final static String KEY_APP_STARTED = "just_started";

    private static void setAppPreference(Context context, String key, String value) {
        SharedPreferences.Editor editor =  getAppPreference(context).edit();
        editor.putString(key, value);
        editor.commit();
    }

    private static void setAppPreference(Context context, String key, int value) {
        SharedPreferences.Editor editor =  getAppPreference(context).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    private static void setAppPreference(Context context, String key, boolean value) {
        SharedPreferences.Editor editor =  getAppPreference(context).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static SharedPreferences getAppPreference(Context context) {
        return context.getSharedPreferences(PREFERENCE_APP, Context.MODE_PRIVATE);
    }

    public static boolean isValidated(Context context) {
        return getAppPreference(context).getBoolean(KEY_APP_VALIDATED, false);
    }

    public static void setValidated(Context context) {
        setAppPreference(context, KEY_APP_VALIDATED, true);
    }

    public static boolean isJustStarted(Context context) {
        boolean justStarted = getAppPreference(context).getBoolean(KEY_APP_STARTED, true);
        if(justStarted)
            setAppPreference(context, KEY_APP_STARTED, false);
        return justStarted;
    }

    public static void clearJustStarted(Context context) {
        setAppPreference(context, KEY_APP_STARTED, true);
    }

}

