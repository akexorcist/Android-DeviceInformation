package app.akeorcist.deviceinformation.utility;

import android.content.Context;
import android.content.SharedPreferences;

import app.akeorcist.deviceinformation.constants.Constants;

/**
 * Created by Ake on 2/25/2015.
 */
public class DevicePreferences {
    private final static String PREFERENCE_DEVICE = "device_pref";

    private final static String KEY_DEVICE_EXIST = "is_exist";
    
    private final static String KEY_CURRENT_DEVICE = "current_device";
    private final static String KEY_DEVICE_NAME = "device_name";
    private final static String KEY_DEVICE_IMAGE = "device_image";
    private final static String KEY_DEVICE_MODEL = "device_model";
    private final static String KEY_OTHER_DEVICE_NAME = "other_device_name";
    private final static String KEY_OTHER_DEVICE_IMAGE = "other_device_image";
    private final static String KEY_OTHER_DEVICE_MODEL = "other_device_model";
    private final static String KEY_BRAND = "brand";
    private final static String KEY_MODEL = "model";
    private final static String KEY_VERSION = "version";
    private final static String KEY_FINGERPRINT = "fingerprint";

    private final static String KEY_NAME_AND_IMAGE = "name_and_image";

    private static void setDevicePreference(Context context, String key, String value) {
        SharedPreferences.Editor editor =  getDevicePreference(context).edit();
        editor.putString(key, value);
        editor.commit();
    }

    private static void setDevicePreference(Context context, String key, boolean value) {
        SharedPreferences.Editor editor =  getDevicePreference(context).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static SharedPreferences getDevicePreference(Context context) {
        return context.getSharedPreferences(PREFERENCE_DEVICE, Context.MODE_PRIVATE);
    }

    public static void setDeviceExist(Context context) {
        setDevicePreference(context, KEY_DEVICE_EXIST, true);
    }

    public static boolean isDeviceExist(Context context) {
        return getDevicePreference(context).getBoolean(KEY_DEVICE_EXIST, false);
    }

    public static void clearCurrentDevice(Context context) {
        setDevicePreference(context, KEY_CURRENT_DEVICE, Constants.FILE_DEVICE_INFO);
    }

    public static void setCurrentDevice(Context context, String fingerprint) {
        setDevicePreference(context, KEY_CURRENT_DEVICE, fingerprint);
    }

    public static String getCurrentDevice(Context context) {
        return getDevicePreference(context).getString(KEY_CURRENT_DEVICE, Constants.FILE_DEVICE_INFO);
    }

    public static void setDeviceBrand(Context context, String brand) {
        setDevicePreference(context, KEY_BRAND, brand);
    }

    public static String getDeviceBrand(Context context) {
        return getDevicePreference(context).getString(KEY_BRAND, "");
    }

    public static void setDeviceModel(Context context, String model) {
        setDevicePreference(context, KEY_MODEL, model);
    }

    public static String getDeviceModel(Context context) {
        return getDevicePreference(context).getString(KEY_MODEL, "");
    }

    public static void setDeviceVersion(Context context, String version) {
        setDevicePreference(context, KEY_VERSION, version);
    }

    public static String getDeviceVersion(Context context) {
        return getDevicePreference(context).getString(KEY_VERSION, "");
    }

    public static void setDeviceFingerprint(Context context, String fingerprint) {
        setDevicePreference(context, KEY_FINGERPRINT, fingerprint);
    }

    public static String getDeviceFingerprint(Context context) {
        return getDevicePreference(context).getString(KEY_FINGERPRINT, "");
    }

    public static void setDeviceName(Context context, String name) {
        setDevicePreference(context, KEY_DEVICE_NAME, name);
    }

    public static String getDeviceName(Context context) {
        return getDevicePreference(context).getString(KEY_DEVICE_NAME, "");
    }

    public static void setDeviceModelName(Context context, String name) {
        setDevicePreference(context, KEY_DEVICE_MODEL, name);
    }

    public static String getDeviceModelName(Context context) {
        return getDevicePreference(context).getString(KEY_DEVICE_MODEL, "");
    }

    public static void setDeviceImage(Context context, String name) {
        setDevicePreference(context, KEY_DEVICE_IMAGE, name);
    }

    public static String getDeviceImage(Context context) {
        return getDevicePreference(context).getString(KEY_DEVICE_IMAGE, "");
    }

    public static void setOtherDeviceName(Context context, String name) {
        setDevicePreference(context, KEY_OTHER_DEVICE_NAME, name);
    }

    public static String getOtherDeviceName(Context context) {
        return getDevicePreference(context).getString(KEY_OTHER_DEVICE_NAME, "");
    }

    public static void setOtherDeviceModelName(Context context, String name) {
        setDevicePreference(context, KEY_OTHER_DEVICE_MODEL, name);
    }

    public static String getOtherDeviceModelName(Context context) {
        return getDevicePreference(context).getString(KEY_OTHER_DEVICE_MODEL, "");
    }

    public static void setOtherDeviceImage(Context context, String name) {
        setDevicePreference(context, KEY_OTHER_DEVICE_IMAGE, name);
    }

    public static String getOtherDeviceImage(Context context) {
        return getDevicePreference(context).getString(KEY_OTHER_DEVICE_IMAGE, "");
    }

    public static void setNameAndImageDownloaded(Context context) {
        setDevicePreference(context, KEY_NAME_AND_IMAGE, true);
    }

    public static void clearNameAndImage(Context context) {
        setDevicePreference(context, KEY_NAME_AND_IMAGE, false);
    }

    public static boolean isNameAndImageDownloaded(Context context) {
        return getDevicePreference(context).getBoolean(KEY_NAME_AND_IMAGE, false);
    }

}

