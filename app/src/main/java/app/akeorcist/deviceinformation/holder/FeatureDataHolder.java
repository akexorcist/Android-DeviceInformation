package app.akeorcist.deviceinformation.holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.akeorcist.deviceinformation.R;

/**
 * Created by Akexorcist on 2/27/15 AD.
 */
public class FeatureDataHolder extends RecyclerView.ViewHolder {
    public TextView tvFeatureTitle;
    public TextView tvFeaturePackage;
    public CardView cvFeature;

    public FeatureDataHolder(View view) {
        super(view);
        tvFeatureTitle = (TextView) view.findViewById(R.id.tv_feature_title);
        tvFeaturePackage = (TextView) view.findViewById(R.id.tv_feature_package);
        cvFeature = (CardView) view.findViewById(R.id.card_view);
    }
}
