package app.akeorcist.deviceinformation.utility;

import android.os.Looper;

/**
 * Created by Akexorcist on 3/29/15 AD.
 */
public class ThreadUtils {

    public static boolean isOnUiThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }
}
