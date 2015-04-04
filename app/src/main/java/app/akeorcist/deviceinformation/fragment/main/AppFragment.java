package app.akeorcist.deviceinformation.fragment.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.inthecheesefactory.thecheeselibrary.fragment.support.v4.app.StatedFragment;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.otto.Subscribe;

import app.akeorcist.deviceinformation.R;
import app.akeorcist.deviceinformation.adapter.AppPagerAdapter;
import app.akeorcist.deviceinformation.adapter.CameraPagerAdapter;
import app.akeorcist.deviceinformation.data.device.DataManager;
import app.akeorcist.deviceinformation.event.ViewEvent;
import app.akeorcist.deviceinformation.provider.BusProvider;
import app.akeorcist.deviceinformation.utility.AnimateUtils;

public class AppFragment extends StatedFragment {

    private LinearLayout layoutContent;
    private ProgressWheel progressWheel;
    private ViewPager vpContent;
    private PagerSlidingTabStrip pagerTab;

    private String[] strTitle;
    private boolean isViewShow = false;
    private boolean isAppLoaded = false;

	public static AppFragment newInstance() {
		AppFragment fragment = new AppFragment();
		return fragment;
	}

	public AppFragment() { }

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_viewpager_form, container, false);

        strTitle = new String[] {
                getString(R.string.app_title_download),
                getString(R.string.app_title_system)
        };

        progressWheel = (ProgressWheel) rootView.findViewById(R.id.progress_wheel);
        layoutContent = (LinearLayout) rootView.findViewById(R.id.layout_content);

        vpContent = (ViewPager) rootView.findViewById(R.id.vp_content);
        pagerTab = (PagerSlidingTabStrip) rootView.findViewById(R.id.pager_tab);

        if(savedInstanceState == null) {
            layoutContent.setVisibility(View.GONE);
            progressWheel.setVisibility(View.VISIBLE);
        }

		return rootView;
	}

    private void initialApp() {
        AppPagerAdapter adapter = new AppPagerAdapter(getFragmentManager(), strTitle);
        if(vpContent != null)
            vpContent.setAdapter(adapter);
        if(pagerTab != null)
            pagerTab.setViewPager(vpContent);
        isAppLoaded = true;

    }

    @Override
    protected void onSaveState(Bundle outState) {
        super.onSaveState(outState);
    }

    @Override
    protected void onRestoreState(Bundle savedInstanceState) {
        super.onRestoreState(savedInstanceState);
        layoutContent.setVisibility(View.GONE);
        progressWheel.setVisibility(View.VISIBLE);
        initialApp();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            layoutContent.setVisibility(View.INVISIBLE);
            initialApp();
        } else if(!isViewShow && ViewEvent.EVENT_VIEW_SHOW_UP.equals(event.getEventState())) {
            isViewShow = true;
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

}
