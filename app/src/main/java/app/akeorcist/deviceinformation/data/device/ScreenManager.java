package app.akeorcist.deviceinformation.data.device;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;

import java.lang.reflect.Method;

import app.akeorcist.deviceinformation.model.ScreenData;

/**
 * Created by Akexorcist on 2/27/15 AD.
 */
public class ScreenManager {
    private static ScreenData screenData = null;

    public static void initialData(Activity activity) {
        screenData = new ScreenData()
                .setDensity(getDensity(activity))
                .setDpi(getDpi(activity))
                .setDpiX(getDpiX(activity))
                .setDpiY(getDpiY(activity))
                .setResolutionDp(getResolutionDP(activity))
                .setResolutionPx(getResolutionPX(activity))
                .setSize(getScreenSize(activity))
                .setMultitouch(getMultitouch(activity));
    }

    public static void destroy() {
        screenData = null;
    }

    public static ScreenData getScreenData() {
        return screenData;
    }

    private static String getMultitouch(Activity activity) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD
                && activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_JAZZHAND)) {
            return "5+ Points";
        } else if(activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT)) {
            return "2-5 Points";
        } else if(activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH)) {
            return "2 Points";
        }
        return "Not supported";
    }

    @SuppressLint("NewApi")
    private static String getDensity(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        if(dm.densityDpi == DisplayMetrics.DENSITY_LOW)
            return "Low";
        else if(dm.densityDpi == DisplayMetrics.DENSITY_MEDIUM)
            return "Medium";
        else if(dm.densityDpi == DisplayMetrics.DENSITY_TV)
            return "TV";
        else if(dm.densityDpi == DisplayMetrics.DENSITY_HIGH)
            return "High";
        else if(dm.densityDpi == DisplayMetrics.DENSITY_XHIGH)
            return "Extra High";
        else if(dm.densityDpi == DisplayMetrics.DENSITY_XXHIGH)
            return "Extra Extra High";
        else if(dm.densityDpi == DisplayMetrics.DENSITY_XXXHIGH)
            return "Extra Extra Extra High";
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && dm.densityDpi == DisplayMetrics.DENSITY_400) {
            return "400";
        } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                && dm.densityDpi == DisplayMetrics.DENSITY_560) {
            return "560";
        }
        return "Unknown";
    }

    private static String getDpi(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.densityDpi + " DPI";
    }

    @SuppressLint("NewApi")
    private static String getDpiX(Activity activity) {
        float densityX = 0;
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            densityX = activity.getResources().getDisplayMetrics().xdpi;
        } else if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics outMetrics = new DisplayMetrics ();
            display.getRealMetrics(outMetrics);
            densityX = outMetrics.xdpi;
        }
        return densityX + " DPI";
    }

    @SuppressLint("NewApi")
    private static String getDpiY(Activity activity) {
        float densityY = 0;
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            densityY = activity.getResources().getDisplayMetrics().ydpi;
        } else if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics outMetrics = new DisplayMetrics ();
            display.getRealMetrics(outMetrics);
            densityY = outMetrics.ydpi;
        }

        return densityY + " DPI";
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private static String getResolutionDP(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        int resolutionX = 0, resolutionY = 0;

        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                Method mGetRawH = Display.class.getMethod("getRawHeight");
                Method mGetRawW = Display.class.getMethod("getRawWidth");
                resolutionX = (Integer) mGetRawW.invoke(display);
                resolutionY = (Integer) mGetRawH.invoke(display);
            } catch (Exception e) {
                resolutionX = display.getWidth();
                resolutionY = display.getHeight();
            }
        } else if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics outMetrics = new DisplayMetrics ();
            display.getRealMetrics(outMetrics);

            resolutionX = outMetrics.widthPixels;
            resolutionY = outMetrics.heightPixels;
        }

        int heightDensity = (int)(resolutionY * (1f / dm.density));
        int widthDensity = (int)(resolutionX * (1f / dm.density));

        return heightDensity + " x " + widthDensity + " DP";
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    private static String getResolutionPX(Activity activity) {
        int resolutionX = 0, resolutionY = 0;
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                Method mGetRawH = Display.class.getMethod("getRawHeight");
                Method mGetRawW = Display.class.getMethod("getRawWidth");
                resolutionX = (Integer) mGetRawW.invoke(display);
                resolutionY = (Integer) mGetRawH.invoke(display);
            } catch (Exception e) {
                resolutionX = display.getWidth();
                resolutionY = display.getHeight();
            }
        } else if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics outMetrics = new DisplayMetrics ();
            display.getRealMetrics(outMetrics);

            resolutionX = outMetrics.widthPixels;
            resolutionY = outMetrics.heightPixels;
        }

        return resolutionY + " x " + resolutionX + " PX";
    }

    private static String getScreenSize(Activity activity) {
        int screenSize = activity.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK;
        if(screenSize == Configuration.SCREENLAYOUT_SIZE_SMALL)
            return "Small";
        else if(screenSize == Configuration.SCREENLAYOUT_SIZE_NORMAL)
            return "Normal";
        else if(screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE)
            return "Large";
        else if(screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE)
            return "Extra Large";
        return "Unknown";
    }
}
