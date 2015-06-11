package app.akeorcist.deviceinformation.activity;

import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;

import com.parse.Parse;
import com.parse.ParseCrashReporting;

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
import app.akeorcist.deviceinformation.data.file.FileManager;
import app.akeorcist.deviceinformation.utility.AppPreferences;
import app.akeorcist.deviceinformation.utility.DevicePreferences;
import app.akeorcist.deviceinformation.utility.FirstTimePreferences;
import app.akeorcist.deviceinformation.utility.WindowsUtils;

public class SplashscreenActivity extends ActionBarActivity {
    int loadTaskCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowsUtils.setStatusAndNavColor(this);
        WindowsUtils.hideActionBar(this);

        setContentView(R.layout.activity_splashscreen);

        initialPreference();
        initialData();
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

        Parse.initialize(this, Key.PARSE_APP_ID, Key.PARSE_CLIENT_ID);
        if(ParseCrashReporting.isCrashReportingEnabled()) {
            ParseCrashReporting.enable(this);
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
