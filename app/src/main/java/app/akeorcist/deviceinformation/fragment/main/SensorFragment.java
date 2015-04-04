package app.akeorcist.deviceinformation.fragment.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inthecheesefactory.thecheeselibrary.fragment.support.v4.app.StatedFragment;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.otto.Subscribe;

import app.akeorcist.deviceinformation.R;
import app.akeorcist.deviceinformation.adapter.HardwearCardAdapter;
import app.akeorcist.deviceinformation.adapter.SensorCardAdapter;
import app.akeorcist.deviceinformation.event.ViewEvent;
import app.akeorcist.deviceinformation.provider.BusProvider;
import app.akeorcist.deviceinformation.utility.AnimateUtils;

public class SensorFragment extends StatedFragment {
    private Activity activity;
    private RecyclerView rvSensorCard;
    private ProgressWheel progressWheel;

	public static SensorFragment newInstance() {
		SensorFragment fragment = new SensorFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
		return fragment;
	}

	public SensorFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sensor, container, false);

        rvSensorCard = (RecyclerView) rootView.findViewById(R.id.rv_sensor_card);
        int column = getResources().getInteger(R.integer.sensor_card_column);
        rvSensorCard.setLayoutManager(new StaggeredGridLayoutManager(column, StaggeredGridLayoutManager.VERTICAL));

        progressWheel = (ProgressWheel) rootView.findViewById(R.id.progress_wheel);

        if(savedInstanceState == null) {
            rvSensorCard.setVisibility(View.GONE);
            progressWheel.setVisibility(View.VISIBLE);
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
        rvSensorCard.setVisibility(View.VISIBLE);
        progressWheel.setVisibility(View.GONE);
        initView(false);
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
            initView(true);
        }
    }

    private void initView(boolean fromFirstTime) {
        RecyclerView.Adapter adapter = new SensorCardAdapter(getActivity());
        rvSensorCard.setAdapter(adapter);
        if(fromFirstTime) {
            animateView();
        }
    }

    private void animateView() {
        AnimateUtils.fadeOutAnimate(progressWheel, new AnimateUtils.OnProgressGoneListener() {
            @Override
            public void onGone() {
                AnimateUtils.fadeInAnimateWithZero(rvSensorCard);
            }
        });
    }
}
