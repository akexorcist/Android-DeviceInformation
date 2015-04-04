package app.akeorcist.deviceinformation.loader;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import app.akeorcist.deviceinformation.adapter.AppCardAdapter;
import app.akeorcist.deviceinformation.data.device.DataManager;
import app.akeorcist.deviceinformation.event.ViewEvent;
import app.akeorcist.deviceinformation.model.AppData;
import app.akeorcist.deviceinformation.provider.BusProvider;

/**
 * Created by Akexorcist on 3/21/15 AD.
 */
public class AppListLoader extends AsyncTask<Void, Void, ArrayList<AppData>> {
    private Context context;
    private FragmentManager fragmentManager;
    private RecyclerView recyclerView;
    private int appType;

    public AppListLoader(Context context, FragmentManager fragmentManager, RecyclerView recyclerView, int appType) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.recyclerView = recyclerView;
        this.appType = appType;
    }

    @Override
    protected ArrayList<AppData> doInBackground(Void... params) {
        ArrayList<AppData> appDataArrayList = DataManager.getAppList(context, appType);
        return appDataArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<AppData> result) {
        RecyclerView.Adapter adapter = new AppCardAdapter(context, fragmentManager, result);
        recyclerView.setAdapter(adapter);
        BusProvider.getInstance().post(new ViewEvent(ViewEvent.EVENT_VIEW_SHOW_UP));
    }
}