package app.akeorcist.deviceinformation.utility;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.view.WindowManager;

import app.akeorcist.deviceinformation.R;

/**
 * Created by Akexorcist on 4/5/15 AD.
 */
public class WindowsUtils {

    public static void hideActionBar(ActionBarActivity action) {
        ActionBar actionBar = action.getSupportActionBar();
        if(actionBar != null)
            actionBar.hide();
    }

    public static void setStatusAndNavColor(Activity activity) {
        Window window = activity.getWindow();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(R.color.dark_blue));
            window.setNavigationBarColor(activity.getResources().getColor(R.color.dark_blue));
        }
    }

}
