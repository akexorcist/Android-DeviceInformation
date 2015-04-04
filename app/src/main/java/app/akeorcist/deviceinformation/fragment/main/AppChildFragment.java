package app.akeorcist.deviceinformation.fragment.main;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.akeorcist.deviceinformation.R;
import app.akeorcist.deviceinformation.adapter.AppCardAdapter;
import app.akeorcist.deviceinformation.data.device.DataManager;
import app.akeorcist.deviceinformation.event.ViewEvent;
import app.akeorcist.deviceinformation.loader.AppListLoader;
import app.akeorcist.deviceinformation.model.AppData;
import app.akeorcist.deviceinformation.provider.BusProvider;

public class AppChildFragment extends Fragment {
    private static final String APP_TYPE = "app_type";
    private Activity activity;

	public static AppChildFragment newInstance(int appType) {
		AppChildFragment fragment = new AppChildFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(APP_TYPE, appType);
        fragment.setArguments(bundle);
		return fragment;
	}

    public AppChildFragment() { }

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_viewpager_child_from, container, false);

        int appType = getArguments().getInt(APP_TYPE);
        RecyclerView rvAppCard = (RecyclerView) rootView.findViewById(R.id.rv_form_card);
        int column = getResources().getInteger(R.integer.app_card_column);
        rvAppCard.setLayoutManager(new StaggeredGridLayoutManager(column, StaggeredGridLayoutManager.VERTICAL));
        new AppListLoader(activity, getFragmentManager(), rvAppCard, appType).execute();

		return rootView;
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }
}
