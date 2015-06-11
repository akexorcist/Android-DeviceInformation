package app.akeorcist.deviceinformation.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import app.akeorcist.deviceinformation.R;
import app.akeorcist.deviceinformation.application.DDIApplication;
import app.akeorcist.deviceinformation.constants.Constants;
import app.akeorcist.deviceinformation.constants.Status;
import app.akeorcist.deviceinformation.data.device.DataManager;
import app.akeorcist.deviceinformation.data.file.FileManager;
import app.akeorcist.deviceinformation.dialog.AppDialog;
import app.akeorcist.deviceinformation.dialog.FeatureDialog;
import app.akeorcist.deviceinformation.dialog.SensorDialog;
import app.akeorcist.deviceinformation.event.BackPressedDeviceSwitcherEvent;
import app.akeorcist.deviceinformation.event.BackPressedEvent;
import app.akeorcist.deviceinformation.event.BackPressedOptionsMenuEvent;
import app.akeorcist.deviceinformation.event.ConfirmSwitchEvent;
import app.akeorcist.deviceinformation.event.DeviceNameDownloadEvent;
import app.akeorcist.deviceinformation.event.DeviceNameResultEvent;
import app.akeorcist.deviceinformation.event.DevicePrompt;
import app.akeorcist.deviceinformation.event.DeviceSwitchEvent;
import app.akeorcist.deviceinformation.event.DeviceSwitcherBackable;
import app.akeorcist.deviceinformation.event.DialogFeatureEvent;
import app.akeorcist.deviceinformation.event.SearchBarEvent;
import app.akeorcist.deviceinformation.event.ViewEvent;
import app.akeorcist.deviceinformation.fragment.main.AppFragment;
import app.akeorcist.deviceinformation.fragment.main.Camera2Fragment;
import app.akeorcist.deviceinformation.fragment.main.CameraFragment;
import app.akeorcist.deviceinformation.fragment.main.FeatureFragment;
import app.akeorcist.deviceinformation.fragment.main.HardwareFragment;
import app.akeorcist.deviceinformation.fragment.main.NavigationDrawerFragment;
import app.akeorcist.deviceinformation.fragment.main.ScreenFragment;
import app.akeorcist.deviceinformation.fragment.main.SensorFragment;
import app.akeorcist.deviceinformation.fragment.main.SubmitFragment;
import app.akeorcist.deviceinformation.fragment.main.SwitcherFragment;
import app.akeorcist.deviceinformation.model.AppData;
import app.akeorcist.deviceinformation.model.SensorData;
import app.akeorcist.deviceinformation.model.SubDevice;
import app.akeorcist.deviceinformation.network.NetworkManager;
import app.akeorcist.deviceinformation.provider.BusProvider;
import app.akeorcist.deviceinformation.utility.AppPreferences;
import app.akeorcist.deviceinformation.utility.DevicePreferences;
import app.akeorcist.deviceinformation.utility.FirstTimePreferences;
import app.akeorcist.deviceinformation.utility.StringUtils;
import app.akeorcist.deviceinformation.utility.WindowsUtils;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    // Constant for page number
    public static final int FIRST_PAGE = 1;
    public static final int DEVICE_SWITCHER_PAGE = 3;
    
	private NavigationDrawerFragment mNavigationDrawerFragment;
    private DialogFragment dialogFragment;
	private CharSequence mTitle;

    // Is current information from that device or another device that fetch from web server
    private boolean isYourDevice = true;

    // Is search bar on current fragment showing
    private boolean isSearchBarExpanded = false;

    // Is device switcher fragment is showing (For handle viewpager from back pressed)
    private boolean isDeviceSwitcherBackable = false;

    // Current and history page
	private int currentPosition = -1;
    private ArrayList<Integer> historyPosition = new ArrayList<>();

    // To handle while device is switching
    private int status = Status.STATUS_IDLE;

    // Store Device Information
    private String deviceName;
    private String fileName;
    private String fingerprint;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        WindowsUtils.setStatusAndNavColor(this);

		setContentView(R.layout.activity_main);

        // Register event bus
        BusProvider.getInstance().register(this);
        BusProvider.getNetworkInstance().register(this);

        // Set actionbar color
		ActionBar ab = getSupportActionBar();
        if(ab != null)
            ab.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));

        // Set action bar title and initialize navigator drawer
        mTitle = getTitle();
		mNavigationDrawerFragment = (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

        // When activity just started, select first page
        if(savedInstanceState == null) {
            selectFragment(FIRST_PAGE);
            getDeviceNameAndImage();
        }

        //TODO Collect storage path
        if(FirstTimePreferences.hasSentSDInfo(this)) {
            //NetworkManager.sendSD(this);
        }

        // Droidsans Features
        //deleteApp();
        //vibrate();
        //playSound();
	}

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore state
        super.onRestoreInstanceState(savedInstanceState);
        currentPosition = savedInstanceState.getInt("current_page");
        historyPosition = savedInstanceState.getIntegerArrayList("history_page");
        status = savedInstanceState.getInt("status");
        deviceName = savedInstanceState.getString("deviceName");
        fileName = savedInstanceState.getString("fileName");
        fingerprint = savedInstanceState.getString("fingerprint");
        isYourDevice = savedInstanceState.getBoolean("isYourDevice");

        selectFragment(currentPosition);
        setSwitcherSnackBar();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Hide dialog fragment, if dialog is showing
        if(dialogFragment != null)
            dialogFragment.dismiss();

        // Save state
        super.onSaveInstanceState(outState);
        outState.putInt("current_page", currentPosition);
        outState.putIntegerArrayList("history_page", historyPosition);
        outState.putInt("status", status);
        outState.putString("deviceName", deviceName);
        outState.putString("fileName", fileName);
        outState.putBoolean("isYourDevice", isYourDevice);
    }

    // Select current page from saved instance
    private void selectFragment(int position) {
        onNavigationDrawerItemSelected(position, false);
        onSectionAttached(position);
    }

    // Show snack bar while switching
    private void setSwitcherSnackBar() {
        switch (status) {
            case Status.STATUS_SUCCESS:
                showDeviceSwitchSnackBar();
                break;
            case Status.STATUS_WAITING:
                showDeviceSwitchingSnackbar();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Unregister event bus
        BusProvider.getInstance().unregister(this);
        BusProvider.getNetworkInstance().unregister(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        // Dismiss dialog, if visible
        if(dialogFragment != null && dialogFragment.isVisible()) {
            dialogFragment.onStop();
        }
    }

    // Get current device name and image url from web service or shared preference if exist
    private void getDeviceNameAndImage() {
        String deviceImage = "";
        String deviceName = "";
        String deviceModel = "";
        if(isYourDevice) {
            deviceImage = StringUtils.removeUnknown(DevicePreferences.getDeviceImage(this));
            deviceName = StringUtils.removeUnknown(DevicePreferences.getDeviceName(this));
            deviceModel = StringUtils.removeUnknown(DevicePreferences.getDeviceModelName(this));
        }

        if(!isYourDevice) {
            DDIApplication.getNetworkInstance().getDeviceName(fingerprint);
        } else if(!"".equals(deviceName)) {
            setDeviceNameAndImage(deviceName, deviceModel, deviceImage);
        } else {
            String fingerprint = DataManager.getFingerprint(this);
            DDIApplication.getNetworkInstance().getDeviceName(fingerprint);
        }
    }

    // Set current device name and image url on navigator drawer
    private void setDeviceNameAndImage(String deviceName, String deviceModel, String deviceImage) {
        if(isYourDevice) {
            DevicePreferences.setDeviceName(MainActivity.this, deviceName);
            DevicePreferences.setDeviceModelName(MainActivity.this, deviceModel);
            DevicePreferences.setDeviceImage(MainActivity.this, deviceImage);
        }
        mNavigationDrawerFragment.setDeviceName(deviceName);
        mNavigationDrawerFragment.setDeviceModel(deviceModel);
        mNavigationDrawerFragment.setDeviceImage(deviceImage);
    }

    // Result of current device name and image url from web service
    @Subscribe
    public void incomingDeviceName(DeviceNameResultEvent event) {
        if(DeviceNameResultEvent.RESULT_FOUND.equals(event.getResult())) {
            DevicePreferences.setNameAndImageDownloaded(this);
            final String name = event.getDeviceName();
            final String brand = event.getDeviceBrand();
            final String model = event.getDeviceModel();
            final String image = event.getDeviceImage();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String deviceName = brand + " " + name;
                    setDeviceNameAndImage(deviceName, model, image);
                }
            });
        } else if(DeviceNameResultEvent.RESULT_NOT_FOUND.equals(event.getResult())) {
            final String deviceName = getString(R.string.menu_unknown_device);
            final String deviceModel = getString(R.string.menu_unknown_device);
            final String deviceImage = "";
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setDeviceNameAndImage(deviceName, deviceModel, deviceImage);
                }
            });
        }
    }

    @Subscribe
    public void startDeviceNameDownload(DeviceNameDownloadEvent event) {
        getDeviceNameAndImage();
    }

    /////////////////////////////
    ///// Droidsans Feature /////
    /////////////////////////////
    private void deleteApp() {
        Uri packageURI = Uri.parse("package:" + getPackageName());
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        startActivity(uninstallIntent);
    }

    private void playSound() {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.effect);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(500);
    }
    /////////////////////////////
    /////////////////////////////
    /////////////////////////////


    @SuppressLint("InlinedApi")
    public void onNavigationDrawerItemSelected(int position, boolean fromSavedInstanceState) {
        onNavigationDrawerItemSelected(position, fromSavedInstanceState, false);
    }

    // Handler for navigator drawer refresh twice when rotate
	@SuppressLint("InlinedApi")
	public void onNavigationDrawerItemSelected(int position, boolean fromSavedInstanceState, boolean fromSwitcher) {
		if(currentPosition != position && !fromSavedInstanceState) {
            Fragment fragment = null;
            switch(position) {
                case 0:
                    backToYourDevice();
                    return;
                case 1:
                    fragment = SubmitFragment.newInstance();
                    break;
                case 2:
                    fragment = SwitcherFragment.newInstance();
                    break;
                case 3:
                    fragment = HardwareFragment.newInstance(fromSwitcher);
                    break;
                case 4:
                    fragment = SensorFragment.newInstance();
                    break;
                case 5:
                    fragment = ScreenFragment.newInstance();
                    break;
                case 6:
                    fragment = CameraFragment.newInstance();
                    break;
                case 7:
                    fragment = Camera2Fragment.newInstance();
                    break;
                case 8:
                    fragment = FeatureFragment.newInstance();
                    break;
                case 9:
                    fragment = AppFragment.newInstance();
                    break;
            }

            if(!getSupportActionBar().isShowing())
                getSupportActionBar().show();

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.commit();

            currentPosition = position;

            if(position >= 0) {
                removeDuplicateHistory(position);
                historyPosition.add(position);
            }

            onSectionAttached(position);
		}
	}

    private void removeDuplicateHistory(int position) {
        if(historyPosition.contains(position)) {
            for(int i = 0 ; i < historyPosition.size() ; i++) {
                if(historyPosition.get(i) == position)
                    historyPosition.remove(i);
            }
        }
    }

    // Set title label
	public void onSectionAttached(int number) {
        switch (number) {
            case 0:
                mTitle = getString(R.string.menu_back_your_device);
                break;
            case 1:
                mTitle = getString(R.string.menu_submit);
                break;
            case 2:
                mTitle = getString(R.string.menu_change_device);
                break;
            case 3:
                mTitle = getString(R.string.menu_hardware);
                break;
            case 4:
                mTitle = getString(R.string.menu_sensor);
                break;
            case 5:
                mTitle = getString(R.string.menu_screen);
                break;
            case 6:
                mTitle = getString(R.string.menu_camera);
                break;
            case 7:
                mTitle = getString(R.string.menu_camera2);
                break;
            case 8:
                mTitle = getString(R.string.menu_features);
                break;
            case 9:
                mTitle = getString(R.string.menu_applist);
                break;
        }
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
        //actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
		int color = getResources().getColor(R.color.action_bar_bg);
		ColorDrawable colorDrawable = new ColorDrawable(color);
		actionBar.setBackgroundDrawable(colorDrawable);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

    // Handler back button pressed
	public void onBackPressed() {
		if(mNavigationDrawerFragment.isVisible()) {
			mNavigationDrawerFragment.hideNavigationDrawer();
        } else if(isSearchBarExpanded) {
            BusProvider.getInstance().post(new BackPressedOptionsMenuEvent());
        } else if(isDeviceSwitcherBackable) {
            BusProvider.getInstance().post(new BackPressedDeviceSwitcherEvent());
        } else if(historyPosition.size() > 1) {
            historyPosition.remove(historyPosition.size() - 1);
            backToFragment(historyPosition.get(historyPosition.size() - 1));
        } else {
			super.onBackPressed();
		}
	}

    // Call onBackPressed
    @Subscribe
    public void onBackPressedFromEventBus(BackPressedEvent event) {
        onBackPressed();
    }

    // Display feature information dialog
    @Subscribe
    public void showFeatureDialog(DialogFeatureEvent event) {
        if(dialogFragment != null && dialogFragment.isVisible())
            dialogFragment.dismiss();
        dialogFragment = FeatureDialog.newInstance(event.getName(), event.getDescription(), event.getVersion());
        dialogFragment.show(getSupportFragmentManager(), "dialog");
    }

    // Display sensor information dialog
    @Subscribe
    public void showSensorDialog(SensorData sensorData) {
        if(dialogFragment != null && dialogFragment.isVisible())
            dialogFragment.dismiss();
        dialogFragment = SensorDialog.newInstance(sensorData);
        dialogFragment.show(getSupportFragmentManager(), "dialog");
    }

    // Display application information dialog
    @Subscribe
    public void showAppData(AppData appData) {
        if(dialogFragment != null && dialogFragment.isVisible())
            dialogFragment.dismiss();
        dialogFragment = AppDialog.newInstance(appData);
        dialogFragment.show(getSupportFragmentManager(), "dialog");
    }

    // Result of device information on device switcher
    @Subscribe
    public void retrieveDeviceInfo(final DeviceSwitchEvent event) {
        if(DeviceSwitchEvent.EVENT_SUCCESS.equals(event.getEvent())) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final SubDevice subDevice = event.getSubDevice();
                    String name = subDevice.getBrand() + " " + subDevice.getName();
                    deviceName = name;
                    fileName = StringUtils.wrapUrl(subDevice.getFingerprint());
                    fingerprint = subDevice.getFingerprint();
                    showDeviceSwitchSnackBar();
                    BusProvider.getInstance().post(new DevicePrompt());
                }
            });
        }
    }

    // Failed to get a result of device information
    @Subscribe
    public void failedRetrieveDeviceInfo(final DeviceSwitchEvent event) {
        if (DeviceSwitchEvent.EVENT_FAILURE.equals(event.getEvent())) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SubDevice subDevice = event.getSubDevice();
                    String name = subDevice.getBrand() + " " + subDevice.getName();
                    SnackbarManager.dismiss();
                    SnackbarManager.show(Snackbar.with(MainActivity.this)
                            .text(event.getMessage())
                            .actionLabel("Try Again")
                            .swipeToDismiss(true)
                            .color(getResources().getColor(R.color.dark_transparent))
                            .duration(Snackbar.SnackbarDuration.LENGTH_LONG)
                            .actionListener(new ActionClickListener() {
                                @Override
                                public void onActionClicked(Snackbar snackbar) {
                                    // TODO try again
                                    BusProvider.getInstance().post(new ConfirmSwitchEvent(event.getSubDevice()));
                                }
                            }), MainActivity.this);
                }
            });
        }
    }

    // Switch device information to selected device
    @Subscribe
    public void switchDevice(ConfirmSwitchEvent event) {
        String fingerprint = event.getSubDevice().getFingerprint();
        if(!FileManager.isDataCached(this, fingerprint)) {
            DDIApplication.getNetworkInstance().getDeviceInfo(this, event.getSubDevice());
            showDeviceSwitchingSnackbar();
        } else {
            // Offline info
            String fileName = StringUtils.wrapUrl(fingerprint);
            deviceName = "Device";
            this.fileName = fileName;
            this.fingerprint = fingerprint;
            showDeviceSwitchSnackBar();
        }
    }

    // Snackbar when device switching
    private void showDeviceSwitchingSnackbar() {
        status = Status.STATUS_WAITING;
        SnackbarManager.show(Snackbar.with(this)
                .text("Device Switching...")
                .actionLabel("Cancel")
                .swipeToDismiss(false)
                .color(getResources().getColor(R.color.dark_transparent))
                .duration(Snackbar.SnackbarDuration.LENGTH_INDEFINITE)
                .actionListener(new ActionClickListener() {
                    @Override
                    public void onActionClicked(Snackbar snackbar) {
                        //TODO Cancel Switching
                        status = Status.STATUS_IDLE;
                        DDIApplication.getNetworkInstance().stopDeviceInfoQuery();
                    }
                }), this);
    }

    // Get back to original device
    private void backToYourDevice() {
        // Refresh menu to back to user device information
        isYourDevice = true;
        DevicePreferences.setCurrentDevice(MainActivity.this, Constants.FILE_DEVICE_INFO);
        mNavigationDrawerFragment.setYourDevice(true);
        AppPreferences.clearJustStarted(this);
        getDeviceNameAndImage();
        refreshNavigatorDrawer();
    }

    // Snackbar for switch information when download information finished
    private void showDeviceSwitchSnackBar() {
        status = Status.STATUS_SUCCESS;
        SnackbarManager.dismiss();
        SnackbarManager.show(Snackbar.with(MainActivity.this)
                .text(deviceName + " ready")
                .actionLabel("Refresh")
                .swipeToDismiss(true)
                .color(getResources().getColor(R.color.dark_transparent))
                .duration(Snackbar.SnackbarDuration.LENGTH_INDEFINITE)
                .actionListener(new ActionClickListener() {
                    @Override
                    public void onActionClicked(Snackbar snackbar) {
                        // TODO Update app to new device info
                        status = Status.STATUS_IDLE;
                        isYourDevice = false;
                        getDeviceNameAndImage();
                        DevicePreferences.setCurrentDevice(MainActivity.this, fileName + ".json");
                        mNavigationDrawerFragment.setYourDevice(false);
                        AppPreferences.clearJustStarted(MainActivity.this);
                        refreshNavigatorDrawer();
                    }
                }), MainActivity.this);
    }

    // Set search bar on current fragment status
    @Subscribe
    public void setSearchBarExpanded(SearchBarEvent event) {
        isSearchBarExpanded = event.isSearchBarExpanded();
    }

    // Back pressed handle for device switcher fragment
    @Subscribe
    public void setDeviceSwitcherPage(DeviceSwitcherBackable event) {
        isDeviceSwitcherBackable = event.isDeviceSwitcherBackable();
    }

    // Restore navigator drawer state when rotate
    private void refreshNavigatorDrawer() {
        currentPosition = -1;
        historyPosition.clear();
        mNavigationDrawerFragment.setNavigatorDrawerMenu(isYourDevice);
        mNavigationDrawerFragment.setItemChecked(DEVICE_SWITCHER_PAGE);
        onNavigationDrawerItemSelected(DEVICE_SWITCHER_PAGE, false, true);
        mNavigationDrawerFragment.selectItem(DEVICE_SWITCHER_PAGE, false);
    }

    // Set fragment when back pressed
    private void backToFragment(int position) {
        mNavigationDrawerFragment.setItemChecked(position);
        onNavigationDrawerItemSelected(position, false);
        mNavigationDrawerFragment.selectItem(position, false);
        mNavigationDrawerFragment.setItemSelected(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(mTitle);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                BusProvider.getInstance().post(new ViewEvent(ViewEvent.EVENT_MENU_SELECTED));
            }
        }, 300);
    }
}
