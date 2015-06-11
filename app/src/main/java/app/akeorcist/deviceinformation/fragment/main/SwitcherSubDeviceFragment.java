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
import com.marshalchen.ultimaterecyclerview.animators.FadeInAnimator;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import app.akeorcist.deviceinformation.R;
import app.akeorcist.deviceinformation.adapter.SwitcherSubDeviceAdapter;
import app.akeorcist.deviceinformation.application.DDIApplication;
import app.akeorcist.deviceinformation.constants.Status;
import app.akeorcist.deviceinformation.event.ChooseDeviceEvent;
import app.akeorcist.deviceinformation.event.ChooseDeviceNameEvent;
import app.akeorcist.deviceinformation.event.DeviceQueryEvent;
import app.akeorcist.deviceinformation.event.DeviceSwitcherNext;
import app.akeorcist.deviceinformation.event.PagerControlEvent;
import app.akeorcist.deviceinformation.event.ViewEvent;
import app.akeorcist.deviceinformation.model.SubDevice;
import app.akeorcist.deviceinformation.provider.BusProvider;
import app.akeorcist.deviceinformation.utility.AnimateUtils;
import app.akeorcist.deviceinformation.utility.FirstTimePreferences;
import app.akeorcist.deviceinformation.utility.SnackBar;

public class SwitcherSubDeviceFragment extends StatedFragment implements View.OnClickListener {
    private Activity activity;
    private LinearLayout layoutPullDown;
    private UltimateRecyclerView rvSubDevice;
    private SwitcherSubDeviceAdapter switcherSubDeviceAdapter;
    private TextView tvChooserDevice;
    private ImageButton btnDeviceBack;
    private ArrayList<SubDevice> arrDeviceList = new ArrayList<>();

    private String brand = "";
    private String name = "";
    private String model = "";

    // Error handle when rotate
    private int status = Status.STATUS_IDLE;

	public static SwitcherSubDeviceFragment newInstance() {
		SwitcherSubDeviceFragment fragment = new SwitcherSubDeviceFragment();
		return fragment;
	}

	public SwitcherSubDeviceFragment() { }

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_switcher_sub_device, container, false);

        layoutPullDown = (LinearLayout) rootView.findViewById(R.id.layout_pull_down);
        layoutPullDown.setVisibility(View.GONE);

        tvChooserDevice = (TextView) rootView.findViewById(R.id.tv_chooser_title);

        btnDeviceBack = (ImageButton) rootView.findViewById(R.id.btn_device_back);
        btnDeviceBack.setOnClickListener(this);
        btnDeviceBack.setOnTouchListener(AnimateUtils.touchAnimateListener);

        switcherSubDeviceAdapter = new SwitcherSubDeviceAdapter(activity, arrDeviceList);
        switcherSubDeviceAdapter.setOnItemClickListener(new SwitcherSubDeviceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // TODO Do something when user select on some device
                String version = arrDeviceList.get(position).getVersion();
                String fingerprint = arrDeviceList.get(position).getFingerprint();
                BusProvider.getInstance().post(new PagerControlEvent(PagerControlEvent.MOVE_NEXT));
                BusProvider.getInstance().post(new ChooseDeviceEvent(brand, name, model, version, fingerprint));
                BusProvider.getInstance().post(new DeviceSwitcherNext(SwitcherFragment.PAGE_SWITCHER_CONFIRM));
            }
        });

        rvSubDevice = (UltimateRecyclerView) rootView.findViewById(R.id.rv_sub_device);
        int column = getResources().getInteger(R.integer.switcher_sub_device_card_column);
        rvSubDevice.setLayoutManager(new StaggeredGridLayoutManager(column, StaggeredGridLayoutManager.VERTICAL));
        rvSubDevice.setAdapter(switcherSubDeviceAdapter);
        rvSubDevice.setHasFixedSize(false);
        rvSubDevice.enableSwipeRefresh(true);
        rvSubDevice.setItemAnimator(new FadeInAnimator());
        rvSubDevice.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getSubDeviceList();
            }
        });

		return rootView;
	}

    @Override
    public void onSaveState(Bundle outState) {
        super.onSaveState(outState);
        outState.putString("brand", brand);
        outState.putString("name", name);
        outState.putString("model", model);
        outState.putInt("status", status);

        ArrayList<String> arrVersionList = new ArrayList<>();
        ArrayList<String> arrFingerprintList = new ArrayList<>();

        for(SubDevice deviceList : arrDeviceList) {
            arrVersionList.add(deviceList.getVersion());
            arrFingerprintList.add(deviceList.getFingerprint());
        }

        outState.putStringArrayList("version_ist", arrVersionList);
        outState.putStringArrayList("fingerprint_list", arrFingerprintList);
    }

    @Override
    public void onRestoreState(Bundle savedInstanceState) {
        super.onRestoreState(savedInstanceState);
        brand = savedInstanceState.getString("brand");
        name = savedInstanceState.getString("name");
        model = savedInstanceState.getString("model");
        status = savedInstanceState.getInt("status");

        ArrayList<SubDevice> arrDeviceList = new ArrayList<>();
        ArrayList<String> arrVersionList = savedInstanceState.getStringArrayList("version_ist");
        ArrayList<String> arrFingerprintList = savedInstanceState.getStringArrayList("fingerprint_list");
        for(int i = 0 ; i < arrVersionList.size() ; i++) {
            SubDevice subDevice = new SubDevice();
            subDevice.setVersion(arrVersionList.get(i));
            subDevice.setFingerprint(arrFingerprintList.get(i));
            arrDeviceList.add(subDevice);
        }
        setViewVisibility();
        if(status != Status.STATUS_ERROR) {
            showPullProgress();
            updateDeviceList(arrDeviceList);
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
    public void incomingSubDeviceList(DeviceQueryEvent event) {
        if(DeviceQueryEvent.RESULT_SUCCESS.equals(event.getResult()) &&
                DeviceQueryEvent.EVENT_SUB.equals(event.getEvent())) {
            status = Status.STATUS_SUCCESS;
            updateDeviceList(event.getSubList());
        } else if(DeviceQueryEvent.RESULT_FAILURE.equals(event.getResult()) &&
                DeviceQueryEvent.EVENT_SUB.equals(event.getEvent())) {
            status = Status.STATUS_ERROR;
            notifyFailure(event.getMessage());
        }
    }

    @Subscribe
    public void readyGetSubDeviceList(ChooseDeviceNameEvent event) {
        final String brand = event.getBrand();
        final String name = event.getName();
        final String model = event.getModel();
        this.brand = brand;
        this.name = name;
        this.model = model;

        if(!FirstTimePreferences.isSubDeviceList(activity)) {
            getSubDeviceList();
            rvSubDevice.setRefreshing(true);
        } else {
            layoutPullDown.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe
    public void onNextPage(DeviceSwitcherNext event) {
        if(event.getPage() == SwitcherFragment.PAGE_SWITCHER_SUB_DEVICE) {
            arrDeviceList.clear();
            switcherSubDeviceAdapter.notifyDataSetChanged();
            rvSubDevice.setRefreshing(true);
        }
    }

    public void getSubDeviceList() {
        tvChooserDevice.setText("Choose " + brand + " " + name);
        showPullProgress();
        DDIApplication.getNetworkInstance().getSubDeviceByName(brand, name, model);
    }

    private void updateDeviceList(final ArrayList<SubDevice> arrDevice) {
        arrDeviceList.clear();
        arrDeviceList.addAll(arrDevice);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AnimateUtils.fadeOutAnimate(layoutPullDown);
                switcherSubDeviceAdapter.notifyDataSetChanged();
            }
        });
    }

    private void notifyFailure(final String message) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AnimateUtils.fadeInAnimate(layoutPullDown);
                rvSubDevice.setRefreshing(false);
                SnackBar.showMessage(activity, message);
            }
        });
    }

    @Subscribe
    public void loadView(ViewEvent event) {
        if(ViewEvent.EVENT_REFRESH_LIST.equals(event.getEventState())) {
            getSubDeviceList();
        }
    }

    private void showPullProgress() {
        rvSubDevice.setRefreshing(true);
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
