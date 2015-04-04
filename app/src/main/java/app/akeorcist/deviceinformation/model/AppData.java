package app.akeorcist.deviceinformation.model;

import android.content.pm.ActivityInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;

import java.security.Provider;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Ake on 2/28/2015.
 */
public class AppData {
    private String name;
    private String pack;
    private String versionCode;
    private String versionName;
    private int resIcon;
    private long firstInstallTime;
    private ArrayList<ActivityInfo> activityInfos = new ArrayList<>();
    private ArrayList<ActivityInfo> receiverInfos = new ArrayList<>();
    private ArrayList<ProviderInfo> providerInfos = new ArrayList<>();
    private ArrayList<FeatureInfo> featureInfos = new ArrayList<>();
    private ArrayList<ServiceInfo> serviceInfos = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();

    public AppData(String name, String pack, int resIcon, String versionCode, String versionName, long firstInstallTime) {
        this.name = name;
        this.pack = pack;
        this.resIcon = resIcon;
        this.versionCode = versionCode;
        this.versionName = versionName;
        this.firstInstallTime = firstInstallTime;
    }

    public String getName() {
        return name;
    }

    public String getPackage() {
        return pack;
    }

    public int getIconResource() {
        return resIcon;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public String getFirstInstallTime() {
        return getDate(firstInstallTime, "dd/MM/yyyy hh:mm:ss.SSS");
    }

    public void addPermission(String permission) {
        permissions.add(permission);
    }

    public ArrayList<String> getPermissions() {
        return permissions;
    }

    public void addActivityInfo(ActivityInfo activityInfo) {
        activityInfos.add(activityInfo);
    }

    public ArrayList<ActivityInfo> getActivityInfos() {
        return activityInfos;
    }

    public void addReceiverInfo(ActivityInfo receiverInfo) {
        receiverInfos.add(receiverInfo);
    }

    public ArrayList<ActivityInfo> getReceiverInfos() {
        return receiverInfos;
    }

    public void addProviderInfo(ProviderInfo providerInfo) {
        providerInfos.add(providerInfo);
    }

    public ArrayList<ProviderInfo> getProviderInfos() {
        return providerInfos;
    }

    public void addFeaturerInfo(FeatureInfo featureInfo) {
        featureInfos.add(featureInfo);
    }

    public ArrayList<FeatureInfo> getFeatureInfos() {
        return featureInfos;
    }

    public void addServiceInfo(ServiceInfo serviceInfo) {
        serviceInfos.add(serviceInfo);
    }

    public ArrayList<ServiceInfo> getServiceInfos() {
        return serviceInfos;
    }

    private static String getDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
