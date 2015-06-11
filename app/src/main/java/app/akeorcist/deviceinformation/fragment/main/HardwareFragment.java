package app.akeorcist.deviceinformation.fragment.main;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inthecheesefactory.thecheeselibrary.fragment.support.v4.app.StatedFragment;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.otto.Subscribe;

import app.akeorcist.deviceinformation.R;
import app.akeorcist.deviceinformation.adapter.HardwearCardAdapter;
import app.akeorcist.deviceinformation.event.ViewEvent;
import app.akeorcist.deviceinformation.provider.BusProvider;
import app.akeorcist.deviceinformation.utility.AnimateUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class HardwareFragment extends StatedFragment {
    private Activity activity;
    private RecyclerView rvHardwareCard;
    private ProgressWheel progressWheel;

    public static Fragment newInstance(boolean fromSwitcher) {
        Fragment fragment = new HardwareFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("from_switcher", fromSwitcher);
        fragment.setArguments(bundle);
        return fragment;
    }

    public HardwareFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hardware, container, false);

        progressWheel = (ProgressWheel) rootView.findViewById(R.id.progress_wheel);
        rvHardwareCard = (RecyclerView) rootView.findViewById(R.id.rv_hardware_card);
        int column = getResources().getInteger(R.integer.hardware_card_column);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(column, StaggeredGridLayoutManager.VERTICAL);
        rvHardwareCard.setLayoutManager(layoutManager);

        if(savedInstanceState == null) {
            rvHardwareCard.setVisibility(View.GONE);
            progressWheel.setVisibility(View.VISIBLE);
            boolean fromSwitcher = getArguments().getBoolean("from_switcher", false);
            if(fromSwitcher) {
                initView(true);
            }
        }

        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onSaveState(Bundle outState) {
        super.onSaveState(outState);
    }

    @Override
    public void onRestoreState(Bundle savedInstanceState) {
        super.onRestoreState(savedInstanceState);
        progressWheel.setVisibility(View.GONE);
        rvHardwareCard.setVisibility(View.VISIBLE);
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
        RecyclerView.Adapter adapter = new HardwearCardAdapter(activity);
        rvHardwareCard.setAdapter(adapter);
        if(fromFirstTime) {
            animateView();
        }
    }

    private void animateView() {
        AnimateUtils.fadeOutAnimate(progressWheel, new AnimateUtils.OnProgressGoneListener() {
            @Override
            public void onGone() {
                AnimateUtils.fadeInAnimateWithZero(rvHardwareCard);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
}
