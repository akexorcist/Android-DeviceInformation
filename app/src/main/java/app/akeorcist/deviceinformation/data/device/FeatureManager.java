package app.akeorcist.deviceinformation.data.device;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;

import java.util.ArrayList;

import app.akeorcist.deviceinformation.constants.Features;
import app.akeorcist.deviceinformation.model.FeatureData;

/**
 * Created by Akexorcist on 2/26/15 AD.
 */
public class FeatureManager {
    private static ArrayList<FeatureData> supportFeatureDataList = null;
    private static ArrayList<FeatureData> unsupportFeatureDataList = null;

    public static void initialData(Activity activity) {
        getFeatureList(activity);
    }

    public static void destroy() {
        if(supportFeatureDataList != null)
            supportFeatureDataList.clear();
        supportFeatureDataList = null;
        if(unsupportFeatureDataList != null)
            unsupportFeatureDataList.clear();
        unsupportFeatureDataList = null;
    }

    public static FeatureData getSupportFeature(int position) {
        return supportFeatureDataList.get(position);
    }

    public static int getSupportFeatureCount() {
        return supportFeatureDataList.size();
    }

    public static FeatureData getUnsupportFeature(int position) {
        return unsupportFeatureDataList.get(position);
    }

    public static int getUnsupportFeatureCount() {
        return unsupportFeatureDataList.size();
    }

    private static ArrayList<FeatureData> getSupportFeature(Activity activity) {
        return getFeatureList(activity);
    }

    private static ArrayList<FeatureData> getUnsupportFeature(Activity activity) {
        return getFeatureList(activity);
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("InlinedApi")
    private static ArrayList<FeatureData> getFeatureList(Activity activity) {
        supportFeatureDataList = new ArrayList<>();
        unsupportFeatureDataList = new ArrayList<>();
        ArrayList<FeatureData> featureList = new ArrayList<>();
        for(FeatureData feature : Features.getFeatureList()) {
            if(hasFeature(activity, feature.getPackage(), feature.getMinSdk()).equals("Yes"))
                supportFeatureDataList.add(feature);
            else
                unsupportFeatureDataList.add(feature);
        }
        return featureList;
    }

    private static String isFeatureSupported(Activity activity, String feature) {
        if(activity.getPackageManager().hasSystemFeature(feature))
            return "Yes";
        else
            return "No";
    }

    private static String hasFeature(Activity activity, String feature, int minVersion) {
        int version = Build.VERSION.SDK_INT;
        if (version >= minVersion)
            return isFeatureSupported(activity, feature);
        else
            return "No";
    }
}

