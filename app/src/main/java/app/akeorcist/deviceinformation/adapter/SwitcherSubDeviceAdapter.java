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
import app.akeorcist.deviceinformation.holder.SwitcherDeviceHolder;
import app.akeorcist.deviceinformation.holder.SwitcherSubDeviceHolder;
import app.akeorcist.deviceinformation.model.SubDevice;

public class SwitcherSubDeviceAdapter extends RecyclerView.Adapter<SwitcherSubDeviceHolder> {
    private Context context;
    private OnItemClickListener listener;
    private ArrayList<SubDevice> arrDeviceList;

    public SwitcherSubDeviceAdapter(Context context, ArrayList<SubDevice> arrDeviceList) {
        this.context = context;
        this.arrDeviceList = arrDeviceList;
    }

    public SwitcherSubDeviceHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_row_switcher_sub_device, viewGroup, false);
        return new SwitcherSubDeviceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SwitcherSubDeviceHolder viewHolder, final int position) {
        SubDevice device = arrDeviceList.get(position);

        viewHolder.tvDeviceVersion.setText(device.getVersion());
        viewHolder.tvDeviceFingerprint.setText(device.getFingerprint());
        viewHolder.layoutDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.onItemClick(viewHolder.layoutDevice, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(arrDeviceList != null)
            return arrDeviceList.size();
        return 0;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
}
