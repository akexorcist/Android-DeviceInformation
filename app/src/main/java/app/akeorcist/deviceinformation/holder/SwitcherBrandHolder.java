package app.akeorcist.deviceinformation.holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import app.akeorcist.deviceinformation.R;

/**
 * Created by Akexorcist on 2/27/15 AD.
 */
public class SwitcherBrandHolder extends RecyclerView.ViewHolder {
    public RelativeLayout layoutBrand;
    public LinearLayout layoutGradient;
    public TextView tvBrandName;
    public ImageView ivBrandIcon;

    public SwitcherBrandHolder(View view) {
        super(view);
        layoutBrand = (RelativeLayout) view.findViewById(R.id.layout_brand);
        layoutGradient = (LinearLayout) view.findViewById(R.id.layout_gradient);
        tvBrandName = (TextView) view.findViewById(R.id.tv_brand_name);
        ivBrandIcon = (ImageView) view.findViewById(R.id.iv_brand_icon);
    }
}
