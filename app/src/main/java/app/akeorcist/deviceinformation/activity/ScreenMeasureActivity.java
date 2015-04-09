package app.akeorcist.deviceinformation.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import app.akeorcist.deviceinformation.R;
import app.akeorcist.deviceinformation.utility.WindowsUtils;

public class ScreenMeasureActivity extends ActionBarActivity {
    private RelativeLayout layoutScreenMeasurement;

    private boolean isFullscreen = true;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        WindowsUtils.setStatusAndNavColor(this);

        setContentView(R.layout.activity_screen_measure);

        layoutScreenMeasurement = (RelativeLayout)findViewById(R.id.layoutScreenMeasurement);
        layoutScreenMeasurement.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(isFullscreen) {
                    clearFullscreen();
                } else {
                    setFullscreen();
                }
            }
        });
        setFullscreen();
        hideActionbar();
    }

    private void hideActionbar() {
        android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        if(supportActionBar != null)
            supportActionBar.hide();
    }

    private void setFullscreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(uiOptions);
        }
        isFullscreen = true;
    }

    private void clearFullscreen() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        isFullscreen = false;
    }
}
