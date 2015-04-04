package app.akeorcist.deviceinformation.data.device.hardware;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Vibrator;

/**
 * Created by Akexorcist on 2/26/15 AD.
 */
public class CommunicationManager {
    public static String hasCellular(Activity activity) {
        ConnectivityManager manager = (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
            return "Yes";
        } catch (NullPointerException e) { }
        return "No";
    }

    public static String hasWiFi(Activity activity) {
        ConnectivityManager manager = (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
            return "Yes";
        } catch (NullPointerException e) { }
        return "No";
    }

    public static String hasWiMax(Activity activity) {
        ConnectivityManager manager = (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            manager.getNetworkInfo(ConnectivityManager.TYPE_WIMAX).getState();
            return "Yes";
        } catch (NullPointerException e) { }
        return "No";
    }

    public static String hasEthernet(Activity activity) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            ConnectivityManager manager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            try {
                manager.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET).getState();
                return "Yes";
            } catch (NullPointerException e) { }
        }
        return "No";
    }

    public static String hasGps(Activity activity) {
        if(activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS)) {
            return "Yes";
        }
        return "No";
    }

    public static String hasTelephony(Activity activity) {
        if(activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY)) {
            return "Yes";
        }
        return "No";
    }

    public static String hasNFC(Activity activity) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD
                && activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_NFC)) {
            return "Yes";
        }
        return "No";
    }

    public static String hasNFCHost(Activity activity) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_NFC_HOST_CARD_EMULATION)) {
            return "Yes";
        }
        return "No";
    }

    public static String hasBluetooth(Activity activity) {
        if (activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)) {
            return "Yes";
        }
        return "No";
    }

    public static String hasBluetoothLE(Activity activity) {
        String hasBluetooth = hasBluetooth(activity);
        if(hasBluetooth.equals("Yes")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2
                    && activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE))
                return "Yes";
            return "No";
        }
        return "No";
    }

    public static String hasWiFiDirect(Activity activity) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH
                && activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_WIFI_DIRECT)) {
            return "Yes";
        }
        return "No";
    }

    public static String hasOTG(Activity activity) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1
                && activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_USB_HOST)) {
            return "Yes";
        }
        return "No";
    }

    public static String hasAOA(Activity activity) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1
                && activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_USB_ACCESSORY)) {
            return "Yes";
        }
        return "No";
    }

    public static String hasMicrophone(Activity activity) {
        if(activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)) {
            return "Yes";
        }
        return "No";
    }

    @SuppressLint("NewApi")
    public static String hasVibrate(Activity activity) {
        Vibrator vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            if(vibrator.hasVibrator())
                return "Yes";
        } else {
            if(vibrator != null) {
                return "Yes";
            }
        }
        return "No";
    }
}
