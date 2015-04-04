package app.akeorcist.deviceinformation.adapter;

/**
 * Created by Akexorcist on 2/27/15 AD.
 */

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import app.akeorcist.deviceinformation.R;
import app.akeorcist.deviceinformation.holder.AppDataHolder;
import app.akeorcist.deviceinformation.model.AppData;
import app.akeorcist.deviceinformation.provider.BusProvider;

public class AppCardAdapter extends RecyclerView.Adapter<AppDataHolder> {
    private Context context;
    private FragmentManager fragmentManager;
    private ArrayList<AppData> appDataArrayList;

    public AppCardAdapter(Context context, FragmentManager fragmentManager, ArrayList<AppData> appDataArrayList) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.appDataArrayList = appDataArrayList;
    }

    public AppDataHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_row_app_card, viewGroup, false);
        return new AppDataHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AppDataHolder viewHolder, int position) {
        final AppData appData = appDataArrayList.get(position);
        String name = appData.getName();
        String pack = appData.getPackage();
        int resIcon = appData.getIconResource();
        viewHolder.tvAppName.setText(name);
        viewHolder.tvAppPackage.setText(pack);
        viewHolder.cvApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusProvider.getInstance().post(appData);
            }
        });

        if(resIcon != 0) {
            Uri uri = Uri.parse("android.resource://" + pack + "/" + resIcon);
            Glide.with(context).load(uri).into(viewHolder.ivAppIcon);
        } else {
            Glide.with(context).load(R.drawable.ic_app_default).into(viewHolder.ivAppIcon);
        }

        // TODO Add Touch Animate Here
        //viewHolder.cvFeature.setOnTouchListener(AnimateUtils.getTouchAnimateListener(0.80f, viewHolder.tvFeatureTitle));
    }

    @Override
    public int getItemCount() {
        return appDataArrayList.size();
    }


}
