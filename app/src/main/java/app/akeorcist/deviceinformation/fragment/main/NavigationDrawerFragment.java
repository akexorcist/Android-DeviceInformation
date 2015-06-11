package app.akeorcist.deviceinformation.fragment.main;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import app.akeorcist.deviceinformation.R;
import app.akeorcist.deviceinformation.activity.MainActivity;
import app.akeorcist.deviceinformation.adapter.NavigatorDrawerAdapter;
import app.akeorcist.deviceinformation.constants.Page;
import app.akeorcist.deviceinformation.constants.URL;
import app.akeorcist.deviceinformation.event.DeviceNameDownloadEvent;
import app.akeorcist.deviceinformation.event.ViewEvent;
import app.akeorcist.deviceinformation.provider.BusProvider;
import app.akeorcist.deviceinformation.utility.DevicePreferences;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends Fragment {



    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private View mDrawerRootView;
    private NavigatorDrawerAdapter mDrawerAdapter;
    private View mFragmentContainerView;
    private TextView tvDeviceName;
    private TextView tvDeviceModel;
    private TextView tvYourDevice;
    private ImageView ivDevice;

    private int mPreSelectedPosition = MainActivity.FIRST_PAGE;
    private int mCurrentSelectedPosition = MainActivity.FIRST_PAGE;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;
    private boolean mItemSelected;

    private String[] arrMenuList;
    private int[] arrIconList;
    private boolean[] isMenuShow;

    private String deviceImageUrl = "";

    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mPreSelectedPosition = mCurrentSelectedPosition;
            mFromSavedInstanceState = true;
        }

        // Select either the default item (0) or the last selected item.
        selectItem(mCurrentSelectedPosition, true);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mDrawerRootView = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        tvDeviceName = (TextView) mDrawerRootView.findViewById(R.id.tv_menu_device_name);
        tvDeviceModel = (TextView) mDrawerRootView.findViewById(R.id.tv_menu_device_model);
        tvYourDevice = (TextView) mDrawerRootView.findViewById(R.id.tv_menu_your_device);

        ivDevice = (ImageView) mDrawerRootView.findViewById(R.id.iv_menu_device);

        mDrawerListView = (ListView) mDrawerRootView.findViewById(R.id.lv_menu);
        arrMenuList = new String[]{
                getString(R.string.menu_back_your_device),
                getString(R.string.menu_submit),
                getString(R.string.menu_change_device),
                getString(R.string.menu_hardware),
                getString(R.string.menu_sensor),
                getString(R.string.menu_screen),
                getString(R.string.menu_camera),
                getString(R.string.menu_camera2),
                getString(R.string.menu_features),
                getString(R.string.menu_applist)
        };

        arrIconList = new int[]{
                R.drawable.menu_icon_back,
                R.drawable.menu_icon_submit,
                R.drawable.menu_icon_switcher,
                R.drawable.menu_icon_hardware,
                R.drawable.menu_icon_sensor,
                R.drawable.menu_icon_screen,
                R.drawable.menu_icon_camera,
                R.drawable.menu_icon_camera2,
                R.drawable.menu_icon_feature,
                R.drawable.menu_icon_app
        };

        isMenuShow = new boolean[]{
                false,
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                true
        };

        mDrawerAdapter = new NavigatorDrawerAdapter(getActivity(), arrMenuList, arrIconList, isMenuShow);
        setNavigatorDrawerMenu(true);
        mDrawerListView.setAdapter(mDrawerAdapter);
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position, false);
            }
        });
        mDrawerAdapter.setItemChecked(mCurrentSelectedPosition);

        if(savedInstanceState != null) {
            tvDeviceName.setText(savedInstanceState.getString("device_name"));
            tvDeviceModel.setText(savedInstanceState.getString("device_model"));
            tvYourDevice.setText(savedInstanceState.getString("your_device"));
            downloadDeviceImage(savedInstanceState.getString("device_image"));
        }

        return mDrawerRootView;
    }

    public void setItemChecked(int position) {
        mDrawerAdapter.setItemChecked(position);
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.drawable.ic_drawer,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                if(mItemSelected) {
                    BusProvider.getInstance().post(new ViewEvent(ViewEvent.EVENT_MENU_SELECTED));
                    mItemSelected = false;
                }
                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).commit();
                }

                if(!DevicePreferences.isNameAndImageDownloaded(getActivity())) {
                    BusProvider.getInstance().post(new DeviceNameDownloadEvent());
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    public void selectItem(int position, boolean fromSavedInstanceState) {
        mCurrentSelectedPosition = position;
        if (mDrawerListView != null) {
            mDrawerAdapter.setItemChecked(position);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position, fromSavedInstanceState);
        }

        if(mCurrentSelectedPosition == mPreSelectedPosition) {
            mItemSelected = false;
        } else {
            mItemSelected = true;
            mPreSelectedPosition = mCurrentSelectedPosition;
        }
    }

    public void setItemSelected(boolean isItemSelected) {
        mItemSelected = isItemSelected;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
        outState.putString("device_name", tvDeviceName.getText().toString());
        outState.putString("device_model", tvDeviceModel.getText().toString());
        outState.putString("your_device", tvYourDevice.getText().toString());
        outState.putString("device_image", deviceImageUrl);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (mDrawerLayout != null && isDrawerOpen()) {
            showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Per the navigation drawer design guidelines, updates the action bar to show the global app
     * 'context', rather than just what's in the current screen.
     */
    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(R.string.menu_choose);
    }

    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position, boolean fromSavedInstanceState);
    }

    public void setDeviceName(String name) {
        if(tvDeviceName != null)
            tvDeviceName.setText(name);
    }

    public void setDeviceModel(String model) {
        if(tvDeviceModel != null && !model.equals("")) {
            tvDeviceModel.setText(model);
        } else if(tvDeviceModel != null) {
            tvDeviceModel.setVisibility(View.GONE);
        }
    }

    public void setDeviceImage(String imageName) {
        String url = URL.URL_DEVICE_IMAGE + imageName;
        downloadDeviceImage(url);
        deviceImageUrl = url;
    }

    private void downloadDeviceImage(String url) {
        Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivDevice);
    }

    public void setYourDevice(boolean isYourDevice) {
        if(isYourDevice)
            tvYourDevice.setText(R.string.menu_your_device);
        else
            tvYourDevice.setText(R.string.menu_other_device);
    }
    
    public void hideNavigationDrawer() {
    	mDrawerLayout.closeDrawers();
    }
    
    public void showNavigationDrawer() {
    	mDrawerLayout.openDrawer(mFragmentContainerView);
    }

    public void setNavigatorDrawerMenu(boolean isYourDevice) {
        if(isYourDevice) {
            mDrawerAdapter.hideMenu(Page.BACK_YOUR_DEVICE);
            mDrawerAdapter.showMenu(Page.SUBMIT);
            mDrawerAdapter.showMenu(Page.CHANGE_DEVICE);
            mDrawerAdapter.showMenu(Page.HARDWARE);
            mDrawerAdapter.showMenu(Page.SENSOR);
            mDrawerAdapter.showMenu(Page.SCREEN);
            mDrawerAdapter.showMenu(Page.CAMERA);
            mDrawerAdapter.showMenu(Page.CAMERA2);
            mDrawerAdapter.showMenu(Page.FEATURE);
            mDrawerAdapter.showMenu(Page.APP_LIST);
        } else {
            mDrawerAdapter.showMenu(Page.BACK_YOUR_DEVICE);
            mDrawerAdapter.hideMenu(Page.SUBMIT);
            mDrawerAdapter.showMenu(Page.CHANGE_DEVICE);
            mDrawerAdapter.showMenu(Page.HARDWARE);
            mDrawerAdapter.showMenu(Page.SENSOR);
            mDrawerAdapter.showMenu(Page.SCREEN);
            mDrawerAdapter.showMenu(Page.CAMERA);
            mDrawerAdapter.showMenu(Page.CAMERA2);
            mDrawerAdapter.showMenu(Page.FEATURE);
            mDrawerAdapter.hideMenu(Page.APP_LIST);
        }
        mDrawerAdapter.notifyDataSetChanged();
    }
}
