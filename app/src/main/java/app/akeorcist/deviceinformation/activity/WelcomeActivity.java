package app.akeorcist.deviceinformation.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.squareup.otto.Subscribe;

import app.akeorcist.deviceinformation.R;
import app.akeorcist.deviceinformation.utility.FirstTimePreferences;
import app.akeorcist.deviceinformation.adapter.WelcomePagerAdapter;
import app.akeorcist.deviceinformation.event.WelcomeEvent;
import app.akeorcist.deviceinformation.provider.BusProvider;
import app.akeorcist.deviceinformation.utility.WindowsUtils;

public class WelcomeActivity extends ActionBarActivity {
    ViewPager pagerWelcome;
    WelcomePagerAdapter adapterWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowsUtils.setStatusAndNavColor(this);
        WindowsUtils.setActionBarColor(this);

        setContentView(R.layout.activity_welcome);

        BusProvider.getInstance().register(this);

        adapterWelcome = new WelcomePagerAdapter(getSupportFragmentManager());
        pagerWelcome = (ViewPager) findViewById(R.id.pager_welcome);
        pagerWelcome.setAdapter(adapterWelcome);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void handleButtonPress(WelcomeEvent event) {
        if(event.getEvent().equals(WelcomeEvent.EVENT_SKIP)) {
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else if(event.getEvent().equals(WelcomeEvent.EVENT_NEXT)) {
            pagerWelcome.setCurrentItem(pagerWelcome.getCurrentItem() + 1, true);
        }
    }
}
