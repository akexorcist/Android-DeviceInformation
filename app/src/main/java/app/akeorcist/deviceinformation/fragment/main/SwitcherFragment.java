package app.akeorcist.deviceinformation.fragment.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.inthecheesefactory.thecheeselibrary.fragment.support.v4.app.StatedFragment;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.otto.Subscribe;

import app.akeorcist.deviceinformation.R;
import app.akeorcist.deviceinformation.adapter.SwitcherPagerAdapter;
import app.akeorcist.deviceinformation.event.PagerControlEvent;
import app.akeorcist.deviceinformation.event.ViewEvent;
import app.akeorcist.deviceinformation.provider.BusProvider;
import app.akeorcist.deviceinformation.utility.AnimateUtils;
import app.akeorcist.deviceinformation.view.NonSwipeableViewPager;

public class SwitcherFragment extends StatedFragment {
    private ProgressWheel progressWheel;
    private NonSwipeableViewPager vpChooser;

	public static SwitcherFragment newInstance() {
		SwitcherFragment fragment = new SwitcherFragment();
		return fragment;
	}

	public SwitcherFragment() { }

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_switcher, container, false);

        progressWheel = (ProgressWheel) rootView.findViewById(R.id.progress_wheel);
        vpChooser = (NonSwipeableViewPager) rootView.findViewById(R.id.vp_chooser);

        if(savedInstanceState == null) {
            progressWheel.setVisibility(View.VISIBLE);
            vpChooser.setVisibility(View.GONE);
            vpChooser.setOffscreenPageLimit(4);
            initialView();
        }

		return rootView;
	}

    @Override
    protected void onSaveState(Bundle outState) {
        super.onSaveState(outState);
    }

    @Override
    protected void onRestoreState(Bundle savedInstanceState) {
        super.onRestoreState(savedInstanceState);
        vpChooser.setVisibility(View.VISIBLE);
        progressWheel.setVisibility(View.GONE);
        initialView();
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
    public void changePage(PagerControlEvent event) {
        if(PagerControlEvent.MOVE_PREV.equals(event.getCommand()))
            vpChooser.setCurrentItem(vpChooser.getCurrentItem() - 1);
        else if(PagerControlEvent.MOVE_NEXT.equals(event.getCommand()))
            vpChooser.setCurrentItem(vpChooser.getCurrentItem() + 1);
        else if(PagerControlEvent.MOVE_POSITION.equals(event.getCommand()))
            vpChooser.setCurrentItem(event.getPage());
    }

    @Subscribe
    public void loadView(ViewEvent event) {
        if(ViewEvent.EVENT_MENU_SELECTED.equals(event.getEventState())) {
            animateView();
        }
    }

    private void animateView() {
        AnimateUtils.fadeOutAnimate(progressWheel, new AnimateUtils.OnProgressGoneListener() {
            @Override
            public void onGone() {
                AnimateUtils.fadeInAnimateWithZero(vpChooser);
            }
        });
    }

    private void initialView() {
        SwitcherPagerAdapter adapter = new SwitcherPagerAdapter(getChildFragmentManager());
        vpChooser.setAdapter(adapter);
    }
}
