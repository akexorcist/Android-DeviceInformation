package app.akeorcist.deviceinformation.utility;

import android.app.Activity;
import android.content.Context;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;

/**
 * Created by Akexorcist on 3/26/15 AD.
 */
public class SnackBar {
    public SnackBar() { }

    public static void showMessage(final Activity activity, final String message) {
        if(ThreadUtils.isOnUiThread()) {
            SnackbarManager.show(Snackbar.with(activity).text(message));
        } else {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SnackbarManager.show(Snackbar.with(activity).text(message));
                }
            });
        }
    }
}
