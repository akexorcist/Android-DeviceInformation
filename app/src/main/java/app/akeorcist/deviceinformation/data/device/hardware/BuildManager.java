package app.akeorcist.deviceinformation.data.device.hardware;

import android.annotation.SuppressLint;
import android.os.Build;

import app.akeorcist.deviceinformation.utility.StringUtils;

/**
 * Created by Akexorcist on 2/27/15 AD.
 */
public class BuildManager {
    @SuppressWarnings("deprecation")
    public static String getRadio() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return StringUtils.wrapUnknown(Build.getRadioVersion());
        }
        return Build.RADIO;
    }

    public static String getSerial() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return Build.SERIAL;
        }
        return "Unknown";
    }

    public static String getABIS(String[] arrSupportList) {
        String str = "";
        for (int i = 0; i < arrSupportList.length; i++) {
            str += arrSupportList[i] + " ";
        }
        return str.trim();
    }

    @SuppressLint("NewApi")
    public static String getSupportABIS() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getABIS(Build.SUPPORTED_ABIS);
        }
        return "Unknown";
    }

    @SuppressLint("NewApi")
    public static String getSupport32ABIS() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getABIS(Build.SUPPORTED_32_BIT_ABIS);
        }
        return "Unknown";
    }

    @SuppressLint("NewApi")
    public static String getSupport64ABIS() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getABIS(Build.SUPPORTED_64_BIT_ABIS);
        }
        return "Unknown";
    }
}
