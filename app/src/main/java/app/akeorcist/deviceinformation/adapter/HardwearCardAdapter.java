package app.akeorcist.deviceinformation.adapter;

/**
 * Created by Akexorcist on 2/27/15 AD.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import app.akeorcist.deviceinformation.R;
import app.akeorcist.deviceinformation.constants.Data;
import app.akeorcist.deviceinformation.data.device.DataManager;
import app.akeorcist.deviceinformation.data.device.HardwareManager;
import app.akeorcist.deviceinformation.model.SimpleData;
import app.akeorcist.deviceinformation.holder.HardwareDataHolder;

public class HardwearCardAdapter extends RecyclerView.Adapter<HardwareDataHolder> {
    private ArrayList<SimpleData> androidDatas;
    private ArrayList<SimpleData> batteryDatas;
    private ArrayList<SimpleData> buildDatas;
    private ArrayList<SimpleData> communicationDatas;
    private ArrayList<SimpleData> cpuDatas;
    private ArrayList<SimpleData> gpuDatas;
    private ArrayList<SimpleData> memoryDatas;
    private ArrayList<SimpleData> storageDatas;

    private ArrayList<View> arrView = new ArrayList<>();

    public HardwearCardAdapter(Context context) {
        androidDatas = DataManager.getAndroidData(context);
        batteryDatas = DataManager.getBatteryData(context);
        buildDatas = DataManager.getBuildData(context);
        communicationDatas = DataManager.getCommunicationData(context);
        cpuDatas = DataManager.getCpuData(context);
        gpuDatas = DataManager.getGpuData(context);
        memoryDatas = DataManager.getMemoryData(context);
        storageDatas = DataManager.getStorageData(context);
    }

    public HardwareDataHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_row_hardware_card, viewGroup, false);
        arrView.add(itemView);
        return new HardwareDataHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HardwareDataHolder viewHolder, int position) {
        viewHolder.layoutHardwareCard.removeAllViews();

        ArrayList<SimpleData> arrData = null;
        if(position == Data.DATA_ANDROID) {
            arrData = androidDatas;
            viewHolder.tvCardHeader.setText(Data.TITLE_ANDROID);
        } else if(position == Data.DATA_BATTERY) {
            arrData = batteryDatas;
            viewHolder.tvCardHeader.setText((Data.TITLE_BATTERY));
        } else if(position == Data.DATA_BUILD) {
            arrData = buildDatas;
            viewHolder.tvCardHeader.setText((Data.TITLE_BUILD));
        } else if(position == Data.DATA_COMMUNICATION) {
            arrData = communicationDatas;
            viewHolder.tvCardHeader.setText((Data.TITLE_COMMUNICATION));
        } else if(position == Data.DATA_CPU) {
            arrData = cpuDatas;
            viewHolder.tvCardHeader.setText((Data.TITLE_CPU));
        } else if(position == Data.DATA_GPU) {
            arrData = gpuDatas;
            viewHolder.tvCardHeader.setText((Data.TITLE_GPU));
        } else if(position == Data.DATA_MEMORY) {
            arrData = memoryDatas;
            viewHolder.tvCardHeader.setText((Data.TITLE_MEMORY));
        } else if(position == Data.DATA_STORAGE) {
            arrData = storageDatas;
            viewHolder.tvCardHeader.setText((Data.TITLE_STORAGE));
        }

        Context context = viewHolder.layoutHardwareCard.getContext();
        if(arrData.size() > 0) {
            for (int i = 0; i < arrData.size(); i++) {
                View dataView = LayoutInflater.from(context).inflate(R.layout.view_row_hardware_card_detail, null, false);
                TextView tvTitle = (TextView) dataView.findViewById(R.id.tv_title);
                tvTitle.setText(arrData.get(i).getTitle());

                TextView tvDetail = (TextView) dataView.findViewById(R.id.tv_detail);
                String strDetail = arrData.get(i).getDetail();
                tvDetail.setText((strDetail.equals("") ? "-" : strDetail));

                viewHolder.layoutHardwareCard.addView(dataView);
            }
        } else {
            View blankView = LayoutInflater.from(context).inflate(R.layout.view_row_hardware_card_blank, null, false);
            viewHolder.layoutHardwareCard.addView(blankView);
        }
    }



    @Override
    public int getItemCount() {
        return HardwareManager.getHardwareDataCount();
    }
}
