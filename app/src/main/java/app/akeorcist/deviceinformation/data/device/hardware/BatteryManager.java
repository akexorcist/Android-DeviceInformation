package app.akeorcist.deviceinformation.data.device.hardware;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

import app.akeorcist.deviceinformation.utility.StringUtils;

/**
 * Created by Akexorcist on 2/27/15 AD.
 */
public class BatteryManager {
    private static final String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";
    public static String getCapacity(Context context) {
        Object powerProfile = null;


        try {
            powerProfile = Class.forName(POWER_PROFILE_CLASS).getConstructor(Context.class).newInstance(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            double batteryCapacity = (Double) Class.forName(POWER_PROFILE_CLASS)
                    .getMethod("getAveragePower", java.lang.String.class)
                    .invoke(powerProfile, "battery.capacity");

            return (int)batteryCapacity + " mA";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unknown";
    }
}
