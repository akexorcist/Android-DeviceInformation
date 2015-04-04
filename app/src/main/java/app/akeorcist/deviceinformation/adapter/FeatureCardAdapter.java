package app.akeorcist.deviceinformation.adapter;

/**
 * Created by Akexorcist on 2/27/15 AD.
 */

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.akeorcist.deviceinformation.R;
import app.akeorcist.deviceinformation.constants.Constants;
import app.akeorcist.deviceinformation.data.device.DataManager;
import app.akeorcist.deviceinformation.event.DialogFeatureEvent;
import app.akeorcist.deviceinformation.holder.FeatureDataHolder;
import app.akeorcist.deviceinformation.model.FeatureData;
import app.akeorcist.deviceinformation.provider.BusProvider;

public class FeatureCardAdapter extends RecyclerView.Adapter<FeatureDataHolder> {
    private Context context;
    private String featureType;
    private FragmentManager fragmentManager;

    public FeatureCardAdapter(Context context, FragmentManager fragmentManager, String featureType) {
        this.context = context;
        this.featureType = featureType;
        this.fragmentManager = fragmentManager;
    }

    public FeatureDataHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_row_feature_card, viewGroup, false);
        return new FeatureDataHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FeatureDataHolder viewHolder, int position) {
        FeatureData featureData = null;
        if(featureType.equals(Constants.FEATURES_SUPPORTED))
            featureData = DataManager.getSupportedFeatureData(context, position);
        else if(featureType.equals(Constants.FEATURES_UNSUPPORTED))
            featureData = DataManager.getUnsupportedFeatureData(context, position);

        final String name = featureData.getName();
        final String pack = featureData.getPackage();
        final String minSdk = featureData.getMinSdk() + "";
        final String desc = context.getString(featureData.getDetail());

        viewHolder.tvFeatureTitle.setText(name);
        viewHolder.tvFeaturePackage.setText(pack);
        viewHolder.cvFeature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusProvider.getInstance().post(new DialogFeatureEvent(name, minSdk, desc));
            }
        });

        // TODO Add Touch Animate Here
        //viewHolder.cvFeature.setOnTouchListener(AnimateUtils.getTouchAnimateListener(0.80f, viewHolder.tvFeatureTitle));
    }

    @Override
    public int getItemCount() {
        if(featureType.equals(Constants.FEATURES_SUPPORTED))
            return DataManager.getSupportedFeatureDataCount(context);
        else if(featureType.equals(Constants.FEATURES_UNSUPPORTED))
            return DataManager.getUnsupportedFeatureDataCount(context);
        return 0;
    }
}
