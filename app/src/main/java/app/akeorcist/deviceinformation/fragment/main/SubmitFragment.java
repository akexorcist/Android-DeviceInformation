package app.akeorcist.deviceinformation.fragment.main;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

import com.dd.CircularProgressButton;
import com.inthecheesefactory.thecheeselibrary.fragment.support.v4.app.StatedFragment;
import com.nineoldandroids.animation.ValueAnimator;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.otto.Subscribe;

import app.akeorcist.deviceinformation.R;
import app.akeorcist.deviceinformation.application.DDIApplication;
import app.akeorcist.deviceinformation.constants.Constants;
import app.akeorcist.deviceinformation.data.file.FileManager;
import app.akeorcist.deviceinformation.event.SubmitEvent;
import app.akeorcist.deviceinformation.event.ViewEvent;
import app.akeorcist.deviceinformation.provider.BusProvider;
import app.akeorcist.deviceinformation.utility.AnimateUtils;
import app.akeorcist.deviceinformation.utility.AppPreferences;
import app.akeorcist.deviceinformation.utility.DevicePreferences;
import app.akeorcist.deviceinformation.utility.FirstTimePreferences;
import app.akeorcist.deviceinformation.utility.SnackBar;
import app.akeorcist.deviceinformation.view.FinishImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubmitFragment extends StatedFragment implements View.OnClickListener {
    private Activity activity;
    private CircularProgressButton btnSubmitInfo;
    private LinearLayout layoutSubmitStatus;
    private FinishImageView btnFinish;
    private FinishImageView btnConnectionAvailable;
    private FinishImageView btnCollectDeviceInfo;
    private FinishImageView btnDeviceExisting;
    private FinishImageView btnSendDeviceInfo;
    private ProgressWheel progressWheel;

    public static SubmitFragment newInstance() {
        SubmitFragment fragment = new SubmitFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    public SubmitFragment() { }

    @Override
    public void onSaveState(Bundle outState) {
        super.onSaveState(outState);
        outState.putInt("btnFinish", btnFinish.getState());
        outState.putInt("btnConnectionAvailable", btnConnectionAvailable.getState());
        outState.putInt("btnCollectDeviceInfo", btnCollectDeviceInfo.getState());
        outState.putInt("btnDeviceExisting", btnDeviceExisting.getState());
        outState.putInt("btnSendDeviceInfo", btnSendDeviceInfo.getState());
    }

    @Override
    public void onRestoreState(Bundle savedInstanceState) {
        super.onRestoreState(savedInstanceState);
        progressWheel.setVisibility(View.GONE);
        btnSubmitInfo.setVisibility(View.VISIBLE);
        layoutSubmitStatus.setVisibility(View.VISIBLE);
        initView(false);

        btnFinish.setState(savedInstanceState.getInt("btnFinish"));
        btnConnectionAvailable.setState(savedInstanceState.getInt("btnConnectionAvailable"));
        btnCollectDeviceInfo.setState(savedInstanceState.getInt("btnCollectDeviceInfo"));
        btnDeviceExisting.setState(savedInstanceState.getInt("btnDeviceExisting"));
        btnSendDeviceInfo.setState(savedInstanceState.getInt("btnSendDeviceInfo"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_submit, container, false);
        layoutSubmitStatus = (LinearLayout) rootView.findViewById(R.id.layout_submit_status);
        progressWheel = (ProgressWheel) rootView.findViewById(R.id.progress_wheel);

        btnSubmitInfo = (CircularProgressButton) rootView.findViewById(R.id.btn_submit_info);

        btnConnectionAvailable = (FinishImageView) rootView.findViewById(R.id.iv_connection_available);
        btnDeviceExisting = (FinishImageView) rootView.findViewById(R.id.iv_device_existing);
        btnCollectDeviceInfo = (FinishImageView) rootView.findViewById(R.id.iv_collect_device_info);
        btnSendDeviceInfo = (FinishImageView) rootView.findViewById(R.id.iv_send_device_info);
        btnFinish = (FinishImageView) rootView.findViewById(R.id.iv_finish);

        btnSubmitInfo.setOnTouchListener(AnimateUtils.touchAnimateListener);
        btnSubmitInfo.setOnClickListener(SubmitFragment.this);

        if(savedInstanceState == null) {
            layoutSubmitStatus.setVisibility(View.GONE);
            btnSubmitInfo.setVisibility(View.GONE);
            progressWheel.setVisibility(View.VISIBLE);
            if(FirstTimePreferences.isFirstPage(activity)) {
                initView(true);
            }
        }

        return rootView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btn_submit_info) {
            if(btnSubmitInfo.getProgress() == 0) {
                clearSubmitState();
                simulateSuccessProgress(2);
                checkConnectionAvailable();
            }
        }
    }

    private void simulateSuccessProgress(final int value) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ValueAnimator widthAnimation = ValueAnimator.ofInt(1, value);
                widthAnimation.setDuration(200);
                widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
                widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Integer value = (Integer) animation.getAnimatedValue();
                        btnSubmitInfo.setProgress(value);
                    }
                });
                widthAnimation.start();
            }
        });
    }

    private void checkConnectionAvailable() {
        DDIApplication.getNetworkInstance().checkDeviceExist(
                DevicePreferences.getDeviceBrand(activity),
                DevicePreferences.getDeviceModel(activity),
                DevicePreferences.getDeviceVersion(activity),
                DevicePreferences.getDeviceFingerprint(activity));
    }

    private void collectDeviceInfo() {
        btnCollectDeviceInfo.finish();
        simulateSuccessProgress(60);
        sendDeviceInfo();
    }

    private void sendDeviceInfo() {
        simulateSuccessProgress(80);
        DDIApplication.getNetworkInstance().sendDeviceInfo(
                DevicePreferences.getDeviceBrand(activity),
                DevicePreferences.getDeviceModel(activity),
                DevicePreferences.getDeviceVersion(activity),
                DevicePreferences.getDeviceFingerprint(activity),
                FileManager.readInternalFile(activity, Constants.PATH_DEVICE_INFO));
    }

    private void clearSubmitState() {
        btnSubmitInfo.setProgress(0);
        btnFinish.clear();
        btnConnectionAvailable.clear();
        btnDeviceExisting.clear();
        btnCollectDeviceInfo.clear();
        btnSendDeviceInfo.clear();
    }

    @Subscribe
    public void connectionUnavailable(final SubmitEvent event) {
        if(event.getEvent().equals(SubmitEvent.EVENT_CONNECTION_UNAVAILABLE)) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    btnConnectionAvailable.error();
                    SnackBar.showMessage(activity, event.getMessage());
                    btnSubmitInfo.setProgress(-1);
                    simulateSuccessProgress(0);
                }
            });
        }
    }

    @Subscribe
    public void deviceExist(SubmitEvent event) {
        if(event.getEvent().equals(SubmitEvent.EVENT_DEVICE_EXIST)) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    btnConnectionAvailable.finish();
                    btnDeviceExisting.error();
                    simulateSuccessProgress(0);
                    DevicePreferences.setDeviceExist(activity);
                    SnackBar.showMessage(activity, getString(R.string.snack_duplicated_info));
                }
            });
        }
    }

    @Subscribe
    public void deviceNotExist(SubmitEvent event) {
        if(event.getEvent().equals(SubmitEvent.EVENT_DEVICE_NOT_EXIST)) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    collectDeviceInfo();
                    simulateSuccessProgress(40);
                    btnConnectionAvailable.finish();
                    btnDeviceExisting.finish();
                }
            });
        }
    }

    @Subscribe
    public void sendFinish(SubmitEvent event) {
        if(event.getEvent().equals(SubmitEvent.EVENT_SEND_FINISHED)) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    btnFinish.finish();
                    btnSendDeviceInfo.finish();
                    simulateSuccessProgress(100);
                    AppPreferences.setValidated(activity);
                }
            });
        }
    }

    @Subscribe
    public void sendFailed(SubmitEvent event) {
        if(event.getEvent().equals(SubmitEvent.EVENT_SEND_FAILED)) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    btnFinish.error();
                    SnackBar.showMessage(activity, "");
                    btnSubmitInfo.setProgress(-1);
                    simulateSuccessProgress(0);
                    SnackBar.showMessage(activity, getString(R.string.snack_cant_send_info));
                }
            });
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
    public void loadView(ViewEvent event) {
        if(ViewEvent.EVENT_MENU_SELECTED.equals(event.getEventState())) {
            initView(true);
        }
    }

    private void initView(boolean fromFirstTime) {
        if(fromFirstTime) {
            AnimateUtils.fadeOutAnimate(progressWheel, new AnimateUtils.OnProgressGoneListener() {
                @Override
                public void onGone() {
                    AnimateUtils.fadeInAnimate(layoutSubmitStatus);
                    AnimateUtils.scaleIn(btnSubmitInfo);

                    initialFinishButton();
                    setupFinishButton();
                }
            });
        } else {
            initialFinishButton();
        }
    }

    private void initialFinishButton() {
        btnConnectionAvailable.setFinishImageResource(R.drawable.ic_finish);
        btnConnectionAvailable.setErrorImageResource(R.drawable.ic_error);
        btnConnectionAvailable.setFinishBackgroundResource(R.drawable.shape_oval_green);

        btnDeviceExisting.setFinishImageResource(R.drawable.ic_finish);
        btnDeviceExisting.setErrorImageResource(R.drawable.ic_error);
        btnDeviceExisting.setFinishBackgroundResource(R.drawable.shape_oval_green);

        btnCollectDeviceInfo.setFinishImageResource(R.drawable.ic_finish);
        btnCollectDeviceInfo.setErrorImageResource(R.drawable.ic_error);
        btnCollectDeviceInfo.setFinishBackgroundResource(R.drawable.shape_oval_green);

        btnSendDeviceInfo.setFinishImageResource(R.drawable.ic_finish);
        btnSendDeviceInfo.setErrorImageResource(R.drawable.ic_error);
        btnSendDeviceInfo.setFinishBackgroundResource(R.drawable.shape_oval_green);

        btnFinish.setFinishImageResource(R.drawable.ic_finish);
        btnFinish.setErrorImageResource(R.drawable.ic_error);
        btnFinish.setFinishBackgroundResource(R.drawable.shape_oval_green);
    }

    private void setupFinishButton() {
        if(AppPreferences.isValidated(activity)) {
            btnConnectionAvailable.finish();
            btnDeviceExisting.finish();
            btnCollectDeviceInfo.finish();
            btnSendDeviceInfo.finish();
            btnFinish.finish();
        } else if(DevicePreferences.isDeviceExist(activity)) {
            btnConnectionAvailable.finish();
            btnDeviceExisting.error();
        }
    }
}
