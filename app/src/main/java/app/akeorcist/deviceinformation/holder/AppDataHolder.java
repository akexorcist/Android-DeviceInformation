package app.akeorcist.deviceinformation.holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import app.akeorcist.deviceinformation.R;

/**
 * Created by Akexorcist on 2/27/15 AD.
 */
public class AppDataHolder extends RecyclerView.ViewHolder {
    public TextView tvAppName;
    public TextView tvAppPackage;
    public ImageView ivAppIcon;
    public CardView cvApp;

    public AppDataHolder(View view) {
        super(view);
        tvAppName = (TextView) view.findViewById(R.id.tv_app_name);
        tvAppPackage = (TextView) view.findViewById(R.id.tv_app_package);
        ivAppIcon = (ImageView) view.findViewById(R.id.iv_app_icon);
        cvApp = (CardView) view.findViewById(R.id.card_view);
    }
}
