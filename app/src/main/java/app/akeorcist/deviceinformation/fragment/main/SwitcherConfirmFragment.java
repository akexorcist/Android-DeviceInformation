package app.akeorcist.deviceinformation.fragment.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.inthecheesefactory.thecheeselibrary.fragment.support.v4.app.StatedFragment;
import com.squareup.otto.Subscribe;

import app.akeorcist.deviceinformation.R;
import app.akeorcist.deviceinformation.event.ChooseDeviceEvent;
import app.akeorcist.deviceinformation.event.ConfirmSwitchEvent;
import app.akeorcist.deviceinformation.event.DevicePrompt;
import app.akeorcist.deviceinformation.event.DeviceSwitchEvent;
import app.akeorcist.deviceinformation.event.PagerControlEvent;
import app.akeorcist.deviceinformation.model.SubDevice;
import app.akeorcist.deviceinformation.provider.BusProvider;
import app.akeorcist.deviceinformation.utility.AnimateUtils;

public class SwitcherConfirmFragment extends StatedFragment implements View.OnClickListener {

    private TextView tvDeviceName;
    private TextView tvDeviceVersion;
    private TextView tvDeviceFingerprint;
    private ImageButton btnDeviceBack;
    private ImageButton btnSwitcherCancel;
    private ImageButton btnSwitcherOK;

    private String brand;
    private String name;
    private String model;
    private String version;
    private String fingerprint;

    private SubDevice subDevice;

    // Handler device info prompt
    private boolean isInfoPrompt = false;

    public static SwitcherConfirmFragment newInstance() {
		SwitcherConfirmFragment fragment = new SwitcherConfirmFragment();
		return fragment;
	}

	public SwitcherConfirmFragment() { }

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_switcher_confirm, container, false);

        tvDeviceName = (TextView) rootView.findViewById(R.id.tv_device_name);
        tvDeviceVersion = (TextView) rootView.findViewById(R.id.tv_device_version);
        tvDeviceFingerprint = (TextView) rootView.findViewById(R.id.tv_device_fingerprint);

        btnDeviceBack = (ImageButton) rootView.findViewById(R.id.btn_device_back);
        btnDeviceBack.setOnClickListener(this);
        btnDeviceBack.setOnTouchListener(AnimateUtils.touchAnimateListener);

        btnSwitcherCancel = (ImageButton) rootView.findViewById(R.id.btn_switcher_cancel);
        btnSwitcherCancel.setOnClickListener(this);
        btnSwitcherCancel.setOnTouchListener(AnimateUtils.touchAnimateListener);

        btnSwitcherOK = (ImageButton) rootView.findViewById(R.id.btn_switcher_ok);
        btnSwitcherOK.setOnClickListener(this);
        btnSwitcherOK.setOnTouchListener(AnimateUtils.touchAnimateListener);

		return rootView;
	}

    @Override
    public void onSaveState(Bundle outState) {
        super.onSaveState(outState);
        if(subDevice != null) {
            outState.putString("brand", subDevice.getBrand());
            outState.putString("name", subDevice.getName());
            outState.putString("model", subDevice.getModel());
            outState.putString("version", subDevice.getVersion());
            outState.putString("fingerprint", subDevice.getFingerprint());
        }
        outState.putBoolean("isInfoPrompt", isInfoPrompt);
    }

    @Override
    public void onRestoreState(Bundle savedInstanceState) {
        super.onRestoreState(savedInstanceState);

        name = savedInstanceState.getString("name");
        brand = savedInstanceState.getString("brand");
        model = savedInstanceState.getString("model");
        version = savedInstanceState.getString("version");
        fingerprint = savedInstanceState.getString("fingerprint");

        isInfoPrompt = savedInstanceState.getBoolean("isInfoPrompt");

        subDevice = new SubDevice();
        subDevice.setName(name);
        subDevice.setBrand(brand);
        subDevice.setModel(model);
        subDevice.setVersion(version);
        subDevice.setFingerprint(fingerprint);

        initialView();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_device_back:
                BusProvider.getInstance().post(new PagerControlEvent(PagerControlEvent.MOVE_PREV));
                break;
            case R.id.btn_switcher_cancel:
                BusProvider.getInstance().post(new PagerControlEvent(0));
                break;
            case R.id.btn_switcher_ok:
                BusProvider.getInstance().post(new ConfirmSwitchEvent(subDevice));
                hideSwitcherProgress();
                break;
        }
    }

    @Subscribe
    public void retrieveDeviceInfo(final DeviceSwitchEvent event) {
        if(DeviceSwitchEvent.EVENT_FAILURE.equals(event.getEvent())) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showSwitcherProgress();
                }
            });
        }
    }

    private void hideSwitcherProgress() {
        disableButton();
        AnimateUtils.scaleOut(btnSwitcherOK);
        AnimateUtils.scaleOut(btnSwitcherCancel);
    }

    private void showSwitcherProgress() {
        enableButton();
        if(isInfoPrompt) {
            AnimateUtils.scaleOutWithZero(btnSwitcherOK);
            AnimateUtils.scaleOutWithZero(btnSwitcherCancel);
        } else {
            AnimateUtils.scaleIn(btnSwitcherOK);
            AnimateUtils.scaleIn(btnSwitcherCancel);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public void readyChooseDevice(ChooseDeviceEvent event) {
        isInfoPrompt = false;

        brand = event.getBrand();
        name = event.getName();
        model = event.getModel();
        version = event.getVersion();
        fingerprint = event.getFingerprint();

        subDevice = new SubDevice();
        subDevice.setName(name);
        subDevice.setBrand(brand);
        subDevice.setModel(model);
        subDevice.setVersion(version);
        subDevice.setFingerprint(fingerprint);

        initialView();
    }

    @Subscribe
    public void failedRetrieveDeviceInfo(final DeviceSwitchEvent event) {
        if(DeviceSwitchEvent.EVENT_FAILURE.equals(event.getEvent())) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showSwitcherProgress();
                }
            });
        }
    }

    @Subscribe
    public void setDeviceSwitcherPrompt(DevicePrompt event) {
        isInfoPrompt = true;
    }

    private void disableButton() {
        btnSwitcherCancel.setEnabled(false);
        btnSwitcherOK.setEnabled(false);
    }

    private void enableButton() {
        btnSwitcherCancel.setEnabled(true);
        btnSwitcherOK.setEnabled(true);
    }

    private void initialView() {
        tvDeviceName.setText(brand + " " + name + " " + model);
        tvDeviceVersion.setText(version);
        tvDeviceFingerprint.setText(fingerprint);

        showSwitcherProgress();
    }

}
