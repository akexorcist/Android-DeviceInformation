package app.akeorcist.deviceinformation.adapter;

/**
 * Created by Akexorcist on 2/27/15 AD.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import app.akeorcist.deviceinformation.R;
import app.akeorcist.deviceinformation.constants.URL;
import app.akeorcist.deviceinformation.holder.SwitcherBrandHolder;
import app.akeorcist.deviceinformation.holder.SwitcherDeviceHolder;

public class SwitcherDeviceAdapter extends RecyclerView.Adapter<SwitcherDeviceHolder> {
    private Context context;
    private OnItemClickListener listener;
    private ArrayList<String> arrDeviceList = new ArrayList<>();

    public SwitcherDeviceAdapter(Context context, ArrayList<String> arrDeviceList) {
        this.context = context;
        this.arrDeviceList = arrDeviceList;
    }

    public SwitcherDeviceHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_row_switcher_device, viewGroup, false);
        return new SwitcherDeviceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SwitcherDeviceHolder viewHolder, final int position) {
        String name = arrDeviceList.get(position);

        viewHolder.tvDeviceName.setText(name);
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
