package app.akeorcist.deviceinformation.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseCrashReporting;

import java.util.HashSet;

import app.akeorcist.deviceinformation.R;
import app.akeorcist.deviceinformation.constants.Constants;
import app.akeorcist.deviceinformation.constants.Key;
import app.akeorcist.deviceinformation.data.device.Camera2Manager;
import app.akeorcist.deviceinformation.data.device.CameraManager;
import app.akeorcist.deviceinformation.data.device.DataManager;
import app.akeorcist.deviceinformation.data.device.FeatureManager;
import app.akeorcist.deviceinformation.data.device.HardwareManager;
import app.akeorcist.deviceinformation.data.device.ScreenManager;
import app.akeorcist.deviceinformation.data.device.SensorListManager;
import app.akeorcist.deviceinformation.data.device.hardware.ExternalStorageManager;
import app.akeorcist.deviceinformation.data.file.FileManager;
import app.akeorcist.deviceinformation.event.WelcomeEvent;
import app.akeorcist.deviceinformation.utility.AppPreferences;
import app.akeorcist.deviceinformation.utility.DevicePreferences;
import app.akeorcist.deviceinformation.utility.FirstTimePreferences;
import app.akeorcist.deviceinformation.utility.WindowsUtils;

public class SplashscreenActivity extends ActionBarActivity {
    int loadTaskCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowsUtils.hideActionBar(this);

        setContentView(R.layout.activity_splashscreen);

        initialPreference();
        initialData();
        //initialSD();
        initialParse();
    }

    private void initialPreference() {
        DevicePreferences.clearCurrentDevice(this);
        AppPreferences.clearJustStarted(this);
        FirstTimePreferences.clearFirstPage(this);
        DevicePreferences.setCurrentDevice(this, Constants.FILE_DEVICE_INFO);
        DevicePreferences.clearNameAndImage(this);
    }

    private void onDataLoaded() {
        if (loadTaskCount == 1) {
            FileManager.writeInternalFile(this, Constants.PATH_DEVICE_INFO, DataManager.createDataJson());
            destroyData();
            callGotoFirstActivity();
        } else {
            loadTaskCount++;
        }
    }

    private void initialParse() {
        if(ParseCrashReporting.isCrashReportingEnabled()) {
            ParseCrashReporting.enable(this);
            Parse.initialize(this, Key.PARSE_APP_ID, Key.PARSE_CLIENT_ID);
        }
    }

    private void initialData() {
        DataManager.buildDeviceSpecific(this);
        CameraManager.initialData(this);
        Camera2Manager.initialData(this);
        FeatureManager.initialData(this);
        ScreenManager.initialData(this);
        SensorListManager.initialData(this);
        GLSurfaceView glSurfaceView = (GLSurfaceView) findViewById(R.id.gl_surface_view);
        HardwareManager.initialData(this, glSurfaceView, new HardwareManager.OnGLSurfaceViewLoadedListener() {
            @Override
            public void onLoaded() {
                onDataLoaded();
            }
        });
        onDataLoaded();
    }

    private void initialSD() {
        HashSet<String> externalLocations = ExternalStorageManager.getStorageSet();

        String str = "";
        for(int i = 0 ; i < externalLocations.size() ; i++) {
            Object[] myArr = externalLocations.toArray();
            String value1 = myArr[0].toString();
            str += value1 + "|||||";
        }


        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("text label", str);
        clipboard.setPrimaryClip(clip);
    }

    private void destroyData() {
        CameraManager.destroy();
        Camera2Manager.destroy();
        FeatureManager.destroy();
        HardwareManager.destroy();
        ScreenManager.destroy();
        SensorListManager.destroy();
    }

    private void callGotoFirstActivity() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        goToFirstActivity();
                    }
                }, 1000);
            }
        });
    }

    private void goToFirstActivity() {
        if(FirstTimePreferences.isFirstRun(this)) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
