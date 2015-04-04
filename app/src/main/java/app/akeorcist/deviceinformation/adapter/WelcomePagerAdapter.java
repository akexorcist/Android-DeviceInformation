package app.akeorcist.deviceinformation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import app.akeorcist.deviceinformation.fragment.welcome.IntroduceDeveloperFragment;
import app.akeorcist.deviceinformation.fragment.welcome.IntroduceUserFragment;
import app.akeorcist.deviceinformation.fragment.welcome.IntroduceWelcomeFragment;

/**
 * Created by Ake on 2/25/2015.
 */
public class WelcomePagerAdapter extends FragmentPagerAdapter {
    private final int PAGE_COUNT = 3;

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
        }
        return null;
    }
}
