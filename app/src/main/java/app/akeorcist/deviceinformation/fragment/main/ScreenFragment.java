package app.akeorcist.deviceinformation.fragment.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inthecheesefactory.thecheeselibrary.fragment.support.v4.app.StatedFragment;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.otto.Subscribe;

import app.akeorcist.deviceinformation.R;
import app.akeorcist.deviceinformation.constants.Constants;
import app.akeorcist.deviceinformation.data.device.DataManager;
import app.akeorcist.deviceinformation.event.ViewEvent;
import app.akeorcist.deviceinformation.provider.BusProvider;
import app.akeorcist.deviceinformation.utility.AnimateUtils;
import app.akeorcist.deviceinformation.activity.ScreenMeasureActivity;
import app.akeorcist.deviceinformation.model.ScreenData;
import app.akeorcist.deviceinformation.utility.AppPreferences;
import app.akeorcist.deviceinformation.utility.DevicePreferences;

public class ScreenFragment extends StatedFragment {
    private Activity activity;
	private CardView cvScreenMeasure;
    private LinearLayout layoutContent;
    private ProgressWheel progressWheel;
    private TextView tvResolutionPx;
    private TextView tvResolutionDp;
    private TextView tvDpiX;
    private TextView tvDpiY;
    private TextView tvDpi;
    private TextView tvSize;
    private TextView tvDensity;
    private TextView tvMultitouch;

	public static ScreenFragment newInstance() {
		ScreenFragment fragment = new ScreenFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
		return fragment;
	}

	public ScreenFragment() { }

	@SuppressLint("NewApi")
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_screen, container, false);

        tvResolutionPx = (TextView) rootView.findViewById(R.id.tv_resolution_px);
        tvResolutionDp = (TextView) rootView.findViewById(R.id.tv_resolution_dp);
        tvDpiX = (TextView) rootView.findViewById(R.id.tv_dpi_x);
        tvDpiY = (TextView) rootView.findViewById(R.id.tv_dpi_y);
        tvDpi = (TextView) rootView.findViewById(R.id.tv_dpi);
        tvSize = (TextView) rootView.findViewById(R.id.tv_size);
        tvDensity = (TextView) rootView.findViewById(R.id.tv_density);
        tvMultitouch = (TextView) rootView.findViewById(R.id.tv_multitouch);

        progressWheel = (ProgressWheel) rootView.findViewById(R.id.progress_wheel);
        layoutContent = (LinearLayout) rootView.findViewById(R.id.layout_content);
        cvScreenMeasure = (CardView) rootView.findViewById(R.id.cv_screen_measure);
        cvScreenMeasure.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ScreenMeasureActivity.class);
                startActivity(intent);
            }
        });

        if(savedInstanceState == null) {
            layoutContent.setVisibility(View.GONE);
            setMeasureScreen();
        }

		return rootView;
	}

    @Override
    public void onSaveState(Bundle outState) {
        super.onSaveState(outState);
    }

    @Override
    public void onRestoreState(Bundle savedInstanceState) {
        super.onRestoreState(savedInstanceState);
        initialData();
        setMeasureScreen();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void loadView(ViewEvent event) {
        if(ViewEvent.EVENT_MENU_SELECTED.equals(event.getEventState())) {
            initialData();
            AnimateUtils.fadeOutAnimate(progressWheel, new AnimateUtils.OnProgressGoneListener() {
                @Override
                public void onGone() {
                    AnimateUtils.fadeInAnimateWithZero(layoutContent);
                }
            });
        }
    }

    private void initialData() {
        ScreenData screenData = DataManager.getScreenData(activity);
        tvResolutionPx.setText(screenData.getResolutionPx());
        tvResolutionDp.setText(screenData.getResolutionDp());
        tvDpiX.setText(screenData.getDpiX());
        tvDpiY.setText(screenData.getDpiY());
        tvDpi.setText(screenData.getDpi());
        tvSize.setText(screenData.getSize());
        tvDensity.setText(screenData.getDensity());
        tvMultitouch.setText(screenData.getMultitouch());
    }

    private void setMeasureScreen() {
        boolean isYourDevice = DevicePreferences.getCurrentDevice(activity).equals(Constants.FILE_DEVICE_INFO);
        if (isYourDevice) {
            cvScreenMeasure.setVisibility(View.VISIBLE);
        } else {
            cvScreenMeasure.setVisibility(View.GONE);
        }
    }
}
