package app.akeorcist.deviceinformation.data.device.hardware;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.util.HashSet;
import java.util.Locale;

/**
 * Created by Akexorcist on 2/27/15 AD.
 */
public class StorageManager {
    @SuppressLint("NewApi")
    public static String isSDSupported() {
        HashSet<String> externalLocations = ExternalStorageManager.getStorageSet();
        for(int i = 0 ; i < externalLocations.size() ; i++) {
            Object[] myArr = externalLocations.toArray();
            String path = myArr[0].toString().toLowerCase(Locale.getDefault());
            if(path.contains("sd"))
                return "yes";
        }
        return "no";
    }

    @SuppressWarnings("deprecation")
    public static String getTotalInternalStorage() {
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        long bytesAvailable = (long)stat.getBlockSize() *(long)stat.getBlockCount();
        float megAvailable = bytesAvailable / 1048576000f;
        return String.format(Locale.getDefault(), "%.3f GB", megAvailable);
    }
}
