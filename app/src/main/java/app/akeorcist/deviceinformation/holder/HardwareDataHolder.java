package app.akeorcist.deviceinformation.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.akeorcist.deviceinformation.R;

/**
 * Created by Akexorcist on 2/27/15 AD.
 */
public class HardwareDataHolder extends RecyclerView.ViewHolder {
    public TextView tvCardHeader;
    public LinearLayout layoutHardwareCard;

    public HardwareDataHolder(View view) {
        super(view);
        tvCardHeader = (TextView) view.findViewById(R.id.tv_card_header);
        layoutHardwareCard = (LinearLayout) view.findViewById(R.id.layout_content);
    }
}
