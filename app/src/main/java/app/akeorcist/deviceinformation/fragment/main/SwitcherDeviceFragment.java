package app.akeorcist.deviceinformation.fragment.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inthecheesefactory.thecheeselibrary.fragment.support.v4.app.StatedFragment;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import app.akeorcist.deviceinformation.R;
import app.akeorcist.deviceinformation.adapter.SwitcherDeviceAdapter;
import app.akeorcist.deviceinformation.application.DDIApplication;
import app.akeorcist.deviceinformation.constants.Status;
import app.akeorcist.deviceinformation.event.ChooseBrandEvent;
import app.akeorcist.deviceinformation.event.ChooseDeviceNameEvent;
import app.akeorcist.deviceinformation.event.DeviceQueryEvent;
import app.akeorcist.deviceinformation.event.PagerControlEvent;
import app.akeorcist.deviceinformation.event.ViewEvent;
import app.akeorcist.deviceinformation.model.DeviceList;
import app.akeorcist.deviceinformation.provider.BusProvider;
import app.akeorcist.deviceinformation.utility.AnimateUtils;
import app.akeorcist.deviceinformation.utility.FirstTimePreferences;
import app.akeorcist.deviceinformation.utility.SnackBar;

public class SwitcherDeviceFragment extends StatedFragment implements View.OnClickListener {
    private Activity activity;
    private LinearLayout layoutPullDown;
    private UltimateRecyclerView rvDevice;
    private TextView tvChooserDevice;
    private ImageButton btnDeviceBack;
    private SwitcherDeviceAdapter switcherDeviceAdapter;
    private ArrayList<String> arrDeviceList = new ArrayList<>();
    private ArrayList<DeviceList> deviceLists = new ArrayList<>();

    private String brand = "";

    // Error handle when rotate
    private int status = Status.STATUS_IDLE;

	public static SwitcherDeviceFragment newInstance() {
		SwitcherDeviceFragment fragment = new SwitcherDeviceFragment();
		return fragment;
	}

	public SwitcherDeviceFragment() { }

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_switcher_device, container, false);

        layoutPullDown = (LinearLayout) rootView.findViewById(R.id.layout_pull_down);
        layoutPullDown.setVisibility(View.GONE);

        tvChooserDevice = (TextView) rootView.findViewById(R.id.tv_chooser_title);

        btnDeviceBack = (ImageButton) rootView.findViewById(R.id.btn_device_back);
        btnDeviceBack.setOnClickListener(this);
        btnDeviceBack.setOnTouchListener(AnimateUtils.touchAnimateListener);

        switcherDeviceAdapter = new SwitcherDeviceAdapter(activity, arrDeviceList);
        switcherDeviceAdapter.setOnItemClickListener(new SwitcherDeviceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // TODO Do something when user select on some device
                if(position < arrDeviceList.size()) {
                    BusProvider.getInstance().post(new PagerControlEvent(PagerControlEvent.MOVE_NEXT));
                    BusProvider.getInstance().post(new ChooseDeviceNameEvent(brand, deviceLists.get(position)));
                }
            }
        });

        rvDevice = (UltimateRecyclerView) rootView.findViewById(R.id.rv_device);
        int column = getResources().getInteger(R.integer.switcher_device_card_column);
        rvDevice.setLayoutManager(new StaggeredGridLayoutManager(column, StaggeredGridLayoutManager.VERTICAL));
        rvDevice.setAdapter(switcherDeviceAdapter);
        rvDevice.setHasFixedSize(false);
        rvDevice.enableSwipeRefresh(true);
        rvDevice.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showPullProgress();
                DDIApplication.getNetworkInstance().getDeviceByBrand(brand);
            }
        });


		return rootView;
	}

    @Override
    public void onSaveState(Bundle outState) {
        super.onSaveState(outState);
        outState.putString("brand", brand);
        outState.putInt("status", status);

        ArrayList<String> arrNameList = new ArrayList<>();
        ArrayList<String> arrModelList = new ArrayList<>();

        for(DeviceList deviceList : deviceLists) {
            arrNameList.add(deviceList.getName());
            arrModelList.add(deviceList.getModel());
        }

        outState.putStringArrayList("name_list", arrNameList);
        outState.putStringArrayList("model_list", arrModelList);
    }

    @Override
    public void onRestoreState(Bundle savedInstanceState) {
        super.onRestoreState(savedInstanceState);
        brand = savedInstanceState.getString("brand");
        status = savedInstanceState.getInt("status");

        deviceLists = new ArrayList<>();
        ArrayList<String> arrNameList = savedInstanceState.getStringArrayList("name_list");
        ArrayList<String> arrModelList = savedInstanceState.getStringArrayList("model_list");
        for(int i = 0 ; i < arrNameList.size() ; i++) {
            DeviceList deviceList = new DeviceList();
            deviceList.setName(arrNameList.get(i));
            deviceList.setModel(arrModelList.get(i));

            deviceLists.add(deviceList);
        }
        setViewVisibility();
        if(status != Status.STATUS_ERROR) {
            rvDevice.setRefreshing(true);
            initialDevice();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_device_back:
                BusProvider.getInstance().post(new PagerControlEvent(PagerControlEvent.MOVE_PREV));
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        BusProvider.getInstance().register(this);
        BusProvider.getNetworkInstance().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
        BusProvider.getNetworkInstance().unregister(this);
    }

    @Subscribe
    public void incomingDeviceList(DeviceQueryEvent event) {
        if(DeviceQueryEvent.RESULT_SUCCESS.equals(event.getResult()) &&
                DeviceQueryEvent.EVENT_NAME.equals(event.getEvent())) {
            status = Status.STATUS_SUCCESS;
            updateDeviceList(event.getDeviceList());
        } else if(DeviceQueryEvent.RESULT_FAILURE.equals(event.getResult()) &&
                DeviceQueryEvent.EVENT_NAME.equals(event.getEvent())) {
            status = Status.STATUS_ERROR;
            notifyFailure(event.getMessage());
        }
    }

    @Subscribe
    public void readyGetDeviceList(ChooseBrandEvent event) {
        final String brand = event.getBrand();
        this.brand = brand;
        if(!FirstTimePreferences.isDeviceList(activity)) {
            getDeviceList();
            rvDevice.setRefreshing(true);
        } else {
            layoutPullDown.setVisibility(View.VISIBLE);
        }
    }

    private void getDeviceList() {
        tvChooserDevice.setText("Choose " + brand + " devices");
        showPullProgress();
        DDIApplication.getNetworkInstance().getDeviceByBrand(brand);
    }

    private void updateDeviceList(ArrayList<DeviceList> arrDevice) {
        deviceLists = arrDevice;
        initialDevice();
    }

    private void notifyFailure(final String message) {
       activity.runOnUiThread(new Runnable() {
           @Override
           public void run() {
               AnimateUtils.fadeInAnimate(layoutPullDown);
               rvDevice.setRefreshing(false);
               SnackBar.showMessage(activity, message);
           }
       });
    }

    @Subscribe
    public void loadView(ViewEvent event) {
        if(ViewEvent.EVENT_REFRESH_LIST.equals(event.getEventState())) {
            getDeviceList();
        }
    }

    private void initialDevice() {
        ArrayList<String> strDeviceLists = new ArrayList<>();
        for(int i = 0 ; i < deviceLists.size() ; i++) {
            DeviceList deviceList = deviceLists.get(i);
            strDeviceLists.add(deviceList.getName() + " " + deviceList.getModel());
        }
        arrDeviceList.clear();
        arrDeviceList.addAll(strDeviceLists);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AnimateUtils.fadeOutAnimate(layoutPullDown);
                //switcherDeviceAdapter.notifyItemRangeInserted(0, arrDeviceList.size());
                switcherDeviceAdapter.notifyDataSetChanged();
            }
        });
    }

    private void showPullProgress() {
        rvDevice.setRefreshing(true);
    }


    private void setViewVisibility() {
        switch (status) {
            case Status.STATUS_SUCCESS:
                layoutPullDown.setVisibility(View.GONE);
                break;
            case Status.STATUS_ERROR:
                layoutPullDown.setVisibility(View.VISIBLE);
                break;
            case Status.STATUS_IDLE:
                layoutPullDown.setVisibility(View.GONE);
                break;
        }
    }

}
