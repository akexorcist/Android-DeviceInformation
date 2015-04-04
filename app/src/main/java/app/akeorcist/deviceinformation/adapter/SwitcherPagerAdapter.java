package app.akeorcist.deviceinformation.adapter;

/**
 * Created by Ake on 2/28/2015.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import app.akeorcist.deviceinformation.constants.Constants;
import app.akeorcist.deviceinformation.fragment.main.FeatureChildFragment;
import app.akeorcist.deviceinformation.fragment.main.SwitcherBrandFragment;
import app.akeorcist.deviceinformation.fragment.main.SwitcherConfirmFragment;
import app.akeorcist.deviceinformation.fragment.main.SwitcherDeviceFragment;
import app.akeorcist.deviceinformation.fragment.main.SwitcherSubDeviceFragment;

public class SwitcherPagerAdapter extends FragmentPagerAdapter {
    private static final int PAGE_COUNT = 4;

    public SwitcherPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            return SwitcherBrandFragment.newInstance();
        } else if(position == 1) {
            return SwitcherDeviceFragment.newInstance();
        } else if(position == 2) {
            return SwitcherSubDeviceFragment.newInstance();
        } else if(position == 3) {
            return SwitcherConfirmFragment.newInstance();
        }
        return null;
    }
}