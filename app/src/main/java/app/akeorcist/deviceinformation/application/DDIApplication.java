package app.akeorcist.deviceinformation.application;

import android.app.Application;

import app.akeorcist.deviceinformation.network.NetworkManager;
import app.akeorcist.deviceinformation.utility.SnackBar;

/**
 * Created by Ake on 2/25/2015.
 */
public class DDIApplication  extends Application {
    private static SnackBar snackBar = new SnackBar();
    private static NetworkManager networkManager = new NetworkManager();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static NetworkManager getNetworkInstance() {
        return networkManager;
    }

    public static SnackBar getSnackBar() {
        return snackBar;
    }
}
