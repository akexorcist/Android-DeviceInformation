package app.akeorcist.deviceinformation.constants;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.Arrays;
import java.util.List;

import app.akeorcist.deviceinformation.R;
import app.akeorcist.deviceinformation.model.FeatureData;

/**
 * Created by Ake on 2/28/2015.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class Features {

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private static List<FeatureData> featureList = null;

    public static void initialFeatureList() {
        featureList = Arrays.asList(
                new FeatureData("App Widget", PackageManager.FEATURE_APP_WIDGETS, Build.VERSION_CODES.JELLY_BEAN_MR2, R.string.detail_app_widget),
                new FeatureData("Audio Low Latency", PackageManager.FEATURE_AUDIO_LOW_LATENCY, Build.VERSION_CODES.GINGERBREAD, R.string.detail_audio_low_latency),
                new FeatureData("Audio Output", PackageManager.FEATURE_AUDIO_OUTPUT, Build.VERSION_CODES.LOLLIPOP, R.string.detail_audio_output),
                new FeatureData("Backup", PackageManager.FEATURE_BACKUP, Build.VERSION_CODES.KITKAT_WATCH, R.string.detail_backup),
                new FeatureData("Bluetooth", PackageManager.FEATURE_BLUETOOTH, Build.VERSION_CODES.FROYO, R.string.detail_bluetooth),
                new FeatureData("Bluetooth Low Energy", PackageManager.FEATURE_BLUETOOTH_LE, Build.VERSION_CODES.JELLY_BEAN_MR2, R.string.detail_bluetooth_low_energy),
                new FeatureData("Camera", PackageManager.FEATURE_CAMERA, Build.VERSION_CODES.ECLAIR_MR1, R.string.detail_camera),
                new FeatureData("Camera Any", PackageManager.FEATURE_CAMERA_ANY, Build.VERSION_CODES.JELLY_BEAN_MR1, R.string.detail_camera_any),
                new FeatureData("Camera Autofocus", PackageManager.FEATURE_CAMERA_AUTOFOCUS, Build.VERSION_CODES.ECLAIR_MR1, R.string.detail_camera_autofocus),
                new FeatureData("Camera Capability Manual Post Processing", PackageManager.FEATURE_CAMERA_CAPABILITY_MANUAL_POST_PROCESSING, Build.VERSION_CODES.LOLLIPOP, R.string.detail_camera_capability_manual_post_processing),
                new FeatureData("Camera Capability Manual Sensor", PackageManager.FEATURE_CAMERA_CAPABILITY_MANUAL_SENSOR, Build.VERSION_CODES.LOLLIPOP, R.string.detail_camera_capability_manual_sensor),
                new FeatureData("Camera Capability RAW", PackageManager.FEATURE_CAMERA_CAPABILITY_RAW, Build.VERSION_CODES.LOLLIPOP, R.string.detail_camera_capability_raw),
                new FeatureData("Camera External", PackageManager.FEATURE_CAMERA_EXTERNAL, Build.VERSION_CODES.KITKAT_WATCH, R.string.detail_camera_external),
                new FeatureData("Camera Flash", PackageManager.FEATURE_CAMERA_FLASH, Build.VERSION_CODES.ECLAIR_MR1, R.string.detail_camera_flash),
                new FeatureData("Camera Front", PackageManager.FEATURE_CAMERA_FRONT, Build.VERSION_CODES.GINGERBREAD, R.string.detail_camera_front),
                new FeatureData("Camera Level Full", PackageManager.FEATURE_CAMERA_LEVEL_FULL, Build.VERSION_CODES.LOLLIPOP, R.string.detail_camera_level_full),
                new FeatureData("Connection Service", PackageManager.FEATURE_CONNECTION_SERVICE, Build.VERSION_CODES.LOLLIPOP, R.string.detail_connection_service),
                new FeatureData("Consumer IR", PackageManager.FEATURE_CONSUMER_IR, Build.VERSION_CODES.KITKAT, R.string.detail_comsumer_ir),
                new FeatureData("Device Admin", PackageManager.FEATURE_DEVICE_ADMIN, Build.VERSION_CODES.KITKAT, R.string.detail_device_admin),
                new FeatureData("Faketouch", PackageManager.FEATURE_FAKETOUCH, Build.VERSION_CODES.HONEYCOMB, R.string.detail_faketouch),
                new FeatureData("Faketouch Multitouch Distinct", PackageManager.FEATURE_FAKETOUCH_MULTITOUCH_DISTINCT, Build.VERSION_CODES.HONEYCOMB_MR2, R.string.detail_faketouch_multitouch_distinct),
                new FeatureData("Faketouch Multitouch Jazzhand", PackageManager.FEATURE_FAKETOUCH_MULTITOUCH_JAZZHAND, Build.VERSION_CODES.HONEYCOMB_MR2, R.string.detail_faketouch_multitouch_jazzhand),
                new FeatureData("Gamepad", PackageManager.FEATURE_GAMEPAD, Build.VERSION_CODES.LOLLIPOP, R.string.detail_gamepad),
                new FeatureData("Home Screen", PackageManager.FEATURE_HOME_SCREEN, Build.VERSION_CODES.JELLY_BEAN_MR2, R.string.detail_home_screen),
                new FeatureData("Input Methods", PackageManager.FEATURE_INPUT_METHODS, Build.VERSION_CODES.JELLY_BEAN_MR2, R.string.detail_input_methods),
                new FeatureData("Leanback", PackageManager.FEATURE_LEANBACK, Build.VERSION_CODES.LOLLIPOP, R.string.detail_leanback),
                new FeatureData("Live TV", PackageManager.FEATURE_LIVE_TV, Build.VERSION_CODES.LOLLIPOP, R.string.detail_live_tv),
                new FeatureData("Live Wallpaper", PackageManager.FEATURE_LIVE_WALLPAPER, Build.VERSION_CODES.ECLAIR_MR1, R.string.detail_live_wallpaper),
                new FeatureData("Location", PackageManager.FEATURE_LOCATION, Build.VERSION_CODES.FROYO, R.string.detail_location),
                new FeatureData("Location GPS", PackageManager.FEATURE_LOCATION_GPS, Build.VERSION_CODES.FROYO, R.string.detail_location_gps),
                new FeatureData("Location Network", PackageManager.FEATURE_LOCATION_NETWORK, Build.VERSION_CODES.FROYO, R.string.detail_location_network),
                new FeatureData("Managed Users", PackageManager.FEATURE_MANAGED_USERS, Build.VERSION_CODES.LOLLIPOP, R.string.detail_managed_users),
                new FeatureData("Microphone", PackageManager.FEATURE_MICROPHONE, Build.VERSION_CODES.FROYO, R.string.detail_microphone),
                new FeatureData("NFC", PackageManager.FEATURE_NFC, Build.VERSION_CODES.GINGERBREAD, R.string.detail_nfc),
                new FeatureData("NFC Host Card Emulation", PackageManager.FEATURE_NFC_HOST_CARD_EMULATION, Build.VERSION_CODES.KITKAT, R.string.detail_nfc_host_card_emulation),
                new FeatureData("OpenGL ES Extension Pack", PackageManager.FEATURE_OPENGLES_EXTENSION_PACK, Build.VERSION_CODES.LOLLIPOP, R.string.detail_opengles_extension_pack),
                new FeatureData("Printing", PackageManager.FEATURE_PRINTING, Build.VERSION_CODES.KITKAT_WATCH, R.string.detail_printing),
                new FeatureData("Screen Landscape", PackageManager.FEATURE_SCREEN_LANDSCAPE, Build.VERSION_CODES.HONEYCOMB_MR2, R.string.detail_screen_landscape),
                new FeatureData("Screen Portrait", PackageManager.FEATURE_SCREEN_PORTRAIT, Build.VERSION_CODES.HONEYCOMB_MR2, R.string.detail_screen_portrait),
                new FeatureData("Securely Removes Users", PackageManager.FEATURE_SECURELY_REMOVES_USERS, Build.VERSION_CODES.LOLLIPOP, R.string.detail_securely_removes_users),
                new FeatureData("Sensor Accelerometer", PackageManager.FEATURE_SENSOR_ACCELEROMETER, Build.VERSION_CODES.FROYO, R.string.detail_sensor_accelerometer),
                new FeatureData("Sensor Ambient Temperature", PackageManager.FEATURE_SENSOR_AMBIENT_TEMPERATURE, Build.VERSION_CODES.LOLLIPOP, R.string.detail_sensor_ambient_temperature),
                new FeatureData("Sensor Barometer", PackageManager.FEATURE_SENSOR_BAROMETER, Build.VERSION_CODES.GINGERBREAD, R.string.detail_sensor_barometer),
                new FeatureData("Sensor Compass", PackageManager.FEATURE_SENSOR_COMPASS, Build.VERSION_CODES.FROYO, R.string.detail_sensor_compass),
                new FeatureData("Sensor Gyroscope", PackageManager.FEATURE_SENSOR_GYROSCOPE, Build.VERSION_CODES.GINGERBREAD, R.string.detail_sensor_gyroscope),
                new FeatureData("Sensor Heart Rate", PackageManager.FEATURE_SENSOR_HEART_RATE, Build.VERSION_CODES.KITKAT_WATCH, R.string.detail_sensor_heart_rate),
                new FeatureData("Sensor Heart Rate ECG", PackageManager.FEATURE_SENSOR_HEART_RATE_ECG, Build.VERSION_CODES.LOLLIPOP, R.string.detail_sensor_heart_rate_ecg),
                new FeatureData("Sensor Light", PackageManager.FEATURE_SENSOR_LIGHT, Build.VERSION_CODES.ECLAIR_MR1, R.string.detail_sensor_light),
                new FeatureData("Sensor Proximity", PackageManager.FEATURE_SENSOR_PROXIMITY, Build.VERSION_CODES.ECLAIR_MR1, R.string.detail_sensor_proximity),
                new FeatureData("Sensor Relative Humidity", PackageManager.FEATURE_SENSOR_RELATIVE_HUMIDITY, Build.VERSION_CODES.LOLLIPOP, R.string.detail_sensor_relative_humidity),
                new FeatureData("Sensor Step Counter", PackageManager.FEATURE_SENSOR_STEP_COUNTER, Build.VERSION_CODES.KITKAT, R.string.detail_sensor_step_counter),
                new FeatureData("Sensor Step Detector", PackageManager.FEATURE_SENSOR_STEP_DETECTOR, Build.VERSION_CODES.KITKAT, R.string.detail_sensor_step_detector),
                new FeatureData("SIP", PackageManager.FEATURE_SIP, Build.VERSION_CODES.GINGERBREAD, R.string.detail_sip),
                new FeatureData("SIP VOIP", PackageManager.FEATURE_SIP_VOIP, Build.VERSION_CODES.GINGERBREAD, R.string.detail_sip_voip),
                new FeatureData("Telephony", PackageManager.FEATURE_TELEPHONY, Build.VERSION_CODES.ECLAIR_MR1, R.string.detail_telephony),
                new FeatureData("Telephony CDMA", PackageManager.FEATURE_TELEPHONY_CDMA, Build.VERSION_CODES.ECLAIR_MR1, R.string.detail_telephony_cdma),
                new FeatureData("Telephony GSM", PackageManager.FEATURE_TELEPHONY_GSM, Build.VERSION_CODES.ECLAIR_MR1, R.string.detail_telephony_gsm),
                new FeatureData("Television", PackageManager.FEATURE_TELEVISION, Build.VERSION_CODES.JELLY_BEAN, R.string.detail_television),
                new FeatureData("Touchscreen", PackageManager.FEATURE_TOUCHSCREEN, Build.VERSION_CODES.FROYO, R.string.detail_touchscreen),
                new FeatureData("Touchscreen Multitouch", PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH, Build.VERSION_CODES.ECLAIR_MR1, R.string.detail_touchscreen_multitouch),
                new FeatureData("Touchscreen Multitouch Distinct", PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT, Build.VERSION_CODES.ECLAIR_MR1, R.string.detail_touchscreen_multitouch_distinct),
                new FeatureData("Touchscreen Multitouch Jazzhand", PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_JAZZHAND, Build.VERSION_CODES.FROYO, R.string.detail_touchscreen_multitouch_jazzhand),
                new FeatureData("USB Accessory", PackageManager.FEATURE_USB_ACCESSORY, Build.VERSION_CODES.HONEYCOMB_MR1, R.string.detail_usb_accessory),
                new FeatureData("USB Host", PackageManager.FEATURE_USB_HOST, Build.VERSION_CODES.HONEYCOMB_MR1, R.string.detail_usb_host),
                new FeatureData("Verified Boot", PackageManager.FEATURE_VERIFIED_BOOT, Build.VERSION_CODES.LOLLIPOP, R.string.detail_verified_boot),
                new FeatureData("Watch", PackageManager.FEATURE_WATCH, Build.VERSION_CODES.KITKAT_WATCH, R.string.detail_watch),
                new FeatureData("WebView", PackageManager.FEATURE_WEBVIEW, Build.VERSION_CODES.KITKAT_WATCH, R.string.detail_webview),
                new FeatureData("WiFi", PackageManager.FEATURE_WIFI, Build.VERSION_CODES.FROYO, R.string.detail_wifi),
                new FeatureData("WiFi Direct", PackageManager.FEATURE_WIFI_DIRECT, Build.VERSION_CODES.ICE_CREAM_SANDWICH, R.string.detail_wifi_direct)
        );
    }

    public static FeatureData getFeatureData(String name) {
        if(featureList == null)
            initialFeatureList();
        for(int i = 0 ; i < featureList.size() ; i++)
            if(name.equals(featureList.get(i).getName()))
                return featureList.get(i);
        return null;
    }

    public static List<FeatureData> getFeatureList() {
        if(featureList == null)
            initialFeatureList();
        return featureList;
    }
}
