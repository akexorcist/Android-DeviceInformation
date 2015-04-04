package app.akeorcist.deviceinformation.adapter;

/**
 * Created by Akexorcist on 2/27/15 AD.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.akeorcist.deviceinformation.R;
import app.akeorcist.deviceinformation.data.device.DataManager;
import app.akeorcist.deviceinformation.event.DialogFeatureEvent;
import app.akeorcist.deviceinformation.holder.SensorDataHolder;
import app.akeorcist.deviceinformation.model.SensorData;
import app.akeorcist.deviceinformation.provider.BusProvider;

public class SensorCardAdapter extends RecyclerView.Adapter<SensorDataHolder> {
    private Context context;
    private ArrayList<SensorData> sensorDatas;

    public SensorCardAdapter(Context context) {
        this.context = context;
        sensorDatas = DataManager.getSensorData(context);
    }

    public SensorDataHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_row_sensor_card, viewGroup, false);
        return new SensorDataHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SensorDataHolder viewHolder, final int position) {
        final SensorData sensor = sensorDatas.get(position);
        viewHolder.tvName.setText(sensor.getName());
        viewHolder.tvType.setText(sensor.getType());
        viewHolder.cvSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusProvider.getInstance().post(sensor);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sensorDatas.size();
    }
}
