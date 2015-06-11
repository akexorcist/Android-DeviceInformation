package app.akeorcist.deviceinformation.fragment.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.inthecheesefactory.thecheeselibrary.fragment.support.v4.app.StatedFragment;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.otto.Subscribe;

import app.akeorcist.deviceinformation.R;
import app.akeorcist.deviceinformation.adapter.CameraPagerAdapter;
import app.akeorcist.deviceinformation.data.device.DataManager;
import app.akeorcist.deviceinformation.event.ViewEvent;
import app.akeorcist.deviceinformation.provider.BusProvider;
import app.akeorcist.deviceinformation.utility.AnimateUtils;

public class CameraFragment extends StatedFragment {
    private Activity activity;
    private LinearLayout layoutContent;
    private ProgressWheel progressWheel;
    private ViewPager vpContent;
    private PagerSlidingTabStrip pagerTab;
    private int cameraCount;

	public static CameraFragment newInstance() {
		return new CameraFragment();
	}

	public CameraFragment() { }

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_viewpager_form, container, false);

        cameraCount = DataManager.getCameraDataCount(activity);
        if(cameraCount > 0) {
            vpContent = (ViewPager) rootView.findViewById(R.id.vp_content);
            pagerTab = (PagerSlidingTabStrip) rootView.findViewById(R.id.pager_tab);
        } else {
            rootView = inflater.inflate(R.layout.fragment_blank, container, false);
        }

        progressWheel = (ProgressWheel) rootView.findViewById(R.id.progress_wheel);
        layoutContent = (LinearLayout) rootView.findViewById(R.id.layout_content);

        if(savedInstanceState == null) {
            layoutContent.setVisibility(View.GONE);
            progressWheel.setVisibility(View.VISIBLE);
        }

        setHasOptionsMenu(true);
		return rootView;
	}

    private void initialCamera() {
        String[] strTitle = new String[cameraCount];
        for (int i = 0; i < cameraCount; i++) {
            strTitle[i] = "Camera " + DataManager.getCameraId(activity, i);
        }
        CameraPagerAdapter adapter = new CameraPagerAdapter(activity, getChildFragmentManager(), strTitle);
        if(vpContent != null)
            vpContent.setAdapter(adapter);
        if(pagerTab != null && vpContent != null)
            pagerTab.setViewPager(vpContent);
    }

    @Override
    protected void onSaveState(Bundle outState) {
        super.onSaveState(outState);
    }

    @Override
    protected void onRestoreState(Bundle savedInstanceState) {
        super.onRestoreState(savedInstanceState);
        layoutContent.setVisibility(View.VISIBLE);
        progressWheel.setVisibility(View.GONE);
        initialCamera();
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
            initialCamera();
            animateView();
        }
    }

    private void animateView() {
        AnimateUtils.fadeOutAnimate(progressWheel, new AnimateUtils.OnProgressGoneListener() {
            @Override
            public void onGone() {
                AnimateUtils.fadeInAnimateWithZero(layoutContent);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
}
