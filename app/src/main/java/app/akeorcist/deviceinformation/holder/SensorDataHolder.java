package app.akeorcist.deviceinformation.holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.akeorcist.deviceinformation.R;

/**
 * Created by Akexorcist on 2/27/15 AD.
 */
public class SensorDataHolder extends RecyclerView.ViewHolder {
    public TextView tvName;
    public TextView tvType;
    public CardView cvSensor;

    public SensorDataHolder(View view) {
        super(view);
        tvName = (TextView) view.findViewById(R.id.tv_sensor_name);
        tvType = (TextView) view.findViewById(R.id.tv_sensor_type);
        cvSensor = (CardView) view.findViewById(R.id.card_view);
    }
}
