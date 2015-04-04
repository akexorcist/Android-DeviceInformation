package app.akeorcist.deviceinformation.data.device.hardware;

import android.annotation.SuppressLint;
import android.os.Build;

/**
 * Created by Akexorcist on 2/27/15 AD.
 */
public class AndroidManager {
    @SuppressLint("NewApi")
    public static String getSystemType() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
            if(Build.SUPPORTED_64_BIT_ABIS.length > 0)
                return "64-bit";
        }
        return "32-bit";
    }

    @SuppressLint("NewApi")
    public static String getVersionCode() {
        int sdkInt = Build.VERSION.SDK_INT;
        if(sdkInt == Build.VERSION_CODES.BASE)
            return "Apple Pie";
        else if(sdkInt == Build.VERSION_CODES.BASE_1_1)
            return "Banana Bread";
        else if(sdkInt == Build.VERSION_CODES.CUPCAKE)
            return "Cupcake";
        else if(sdkInt == Build.VERSION_CODES.DONUT)
            return "Donut";
        else if(sdkInt == Build.VERSION_CODES.ECLAIR)
            return "Eclair";
        else if(sdkInt == Build.VERSION_CODES.ECLAIR_0_1)
            return "Eclair";
        else if(sdkInt == Build.VERSION_CODES.ECLAIR_MR1)
            return "Eclair";
        else if(sdkInt == Build.VERSION_CODES.FROYO)
            return "Froyo";
        else if(sdkInt == Build.VERSION_CODES.CUPCAKE)
            return "Cupcake";
        else if(sdkInt == Build.VERSION_CODES.GINGERBREAD)
            return "Gingerbread";
        else if(sdkInt == Build.VERSION_CODES.GINGERBREAD_MR1)
            return "Gingerbread";
        else if(sdkInt == Build.VERSION_CODES.HONEYCOMB)
            return "Honeycomb";
        else if(sdkInt == Build.VERSION_CODES.HONEYCOMB_MR1)
            return "Honeycomb";
        else if(sdkInt == Build.VERSION_CODES.HONEYCOMB_MR2)
            return "Honeycomb";
        else if(sdkInt == Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            return "Ice Cream Sandwich";
        else if(sdkInt == Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
            return "Ice Cream Sandwich";
        else if(sdkInt == Build.VERSION_CODES.JELLY_BEAN)
            return "Jelly Bean";
        else if(sdkInt == Build.VERSION_CODES.JELLY_BEAN_MR1)
            return "Jelly Bean";
        else if(sdkInt == Build.VERSION_CODES.JELLY_BEAN_MR2)
            return "Jelly Bean";
        else if(sdkInt == Build.VERSION_CODES.KITKAT)
            return "KitKat";
        else if(sdkInt == Build.VERSION_CODES.KITKAT_WATCH)
            return "KitKat Wear";
        else if(sdkInt == Build.VERSION_CODES.LOLLIPOP)
            return "Lollipop";
        else if(sdkInt == Build.VERSION_CODES.LOLLIPOP_MR1)
            return "Lollipop";
        return "Unknown";
    }
}
