package app.akeorcist.deviceinformation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import app.akeorcist.deviceinformation.constants.Page;
import app.akeorcist.deviceinformation.fragment.welcome.DescriptionLastFragment;
import app.akeorcist.deviceinformation.fragment.welcome.IntroduceDeveloperFragment;
import app.akeorcist.deviceinformation.fragment.welcome.IntroduceUserFragment;
import app.akeorcist.deviceinformation.fragment.welcome.DescriptionFragment;
import app.akeorcist.deviceinformation.fragment.welcome.IntroduceWelcomeFragment;

/**
 * Created by Ake on 2/25/2015.
 */
public class WelcomePagerAdapter extends FragmentPagerAdapter {
    private final int PAGE_COUNT = 12;

    public WelcomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public int getCount() {
        return PAGE_COUNT;
    }

    public Fragment getItem(int position) {
        if(position == 0) {
            return IntroduceWelcomeFragment.newInstance();
        } else if(position == 1) {
            return IntroduceUserFragment.newInstance();
        } else if(position == 2) {
            return IntroduceDeveloperFragment.newInstance();
        } else if(position == 3) {
            return DescriptionFragment.newInstance(Page.SUBMIT);
        } else if(position == 4) {
            return DescriptionFragment.newInstance(Page.HARDWARE);
        } else if(position == 5) {
            return DescriptionFragment.newInstance(Page.SENSOR);
        } else if(position == 6) {
            return DescriptionFragment.newInstance(Page.SCREEN);
        } else if(position == 7) {
            return DescriptionFragment.newInstance(Page.CAMERA);
        } else if(position == 8) {
            return DescriptionFragment.newInstance(Page.CAMERA2);
        } else if(position == 9) {
            return DescriptionFragment.newInstance(Page.FEATURE);
        } else if(position == 10) {
            return DescriptionFragment.newInstance(Page.APP_LIST);
        } else if(position == 11) {
            return DescriptionLastFragment.newInstance();
        }
        return null;
    }
}
