package app.akeorcist.deviceinformation.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.akeorcist.deviceinformation.R;

/**
 * Created by Akexorcist on 2/27/15 AD.
 */
public class SwitcherSubDeviceHolder extends RecyclerView.ViewHolder {
    public TextView tvDeviceVersion;
    public TextView tvDeviceFingerprint;
    public LinearLayout layoutDevice;

    public SwitcherSubDeviceHolder(View view) {
        super(view);
        tvDeviceVersion = (TextView) view.findViewById(R.id.tv_device_version);
        tvDeviceFingerprint = (TextView) view.findViewById(R.id.tv_device_fingerprint);
        layoutDevice = (LinearLayout) view.findViewById(R.id.layout_device);
    }
}
