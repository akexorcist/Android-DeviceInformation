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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.ArrayList;

import app.akeorcist.deviceinformation.R;
import app.akeorcist.deviceinformation.constants.URL;
import app.akeorcist.deviceinformation.holder.SwitcherBrandHolder;
import app.akeorcist.deviceinformation.utility.AnimateUtils;

public class SwitcherBrandAdapter extends UltimateViewAdapter {
    private Context context;
    private ArrayList<String> arrBrandList;
    private OnItemClickListener listener;

    public SwitcherBrandAdapter(Context context, ArrayList<String> arrBrandList) {
        this.context = context;
        this.arrBrandList = arrBrandList;
    }

    public SwitcherBrandHolder onCreateViewHolder(ViewGroup viewGroup) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_row_switcher_brand, viewGroup, false);
        return new SwitcherBrandHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        String name = arrBrandList.get(position);

        final SwitcherBrandHolder viewHolder = (SwitcherBrandHolder) holder;
        viewHolder.tvBrandName.setText(name);
        viewHolder.layoutGradient.setVisibility(View.INVISIBLE);
        Glide.with(context).load(URL.URL_BRAND_LOGO + name).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                AnimateUtils.fadeInAnimate(viewHolder.layoutGradient);
                return false;
            }
        }).into(viewHolder.ivBrandIcon);
        viewHolder.layoutBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.onItemClick(viewHolder.layoutBrand, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(arrBrandList != null)
            return arrBrandList.size();
        return 0;
    }

    @Override
    public int getAdapterItemCount() {
        return arrBrandList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
}
