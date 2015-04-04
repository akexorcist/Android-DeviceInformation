package app.akeorcist.deviceinformation.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.akeorcist.deviceinformation.R;

/**
 * Created by Akexorcist on 2/27/15 AD.
 */
public class SwitcherDeviceHolder extends RecyclerView.ViewHolder {
    public LinearLayout layoutDevice;
    public TextView tvDeviceName;

    public SwitcherDeviceHolder(View view) {
        super(view);
        layoutDevice = (LinearLayout) view.findViewById(R.id.layout_device);
        tvDeviceName = (TextView) view.findViewById(R.id.tv_device_name);
    }
}
