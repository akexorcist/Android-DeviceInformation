package app.akeorcist.deviceinformation.fragment.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.inthecheesefactory.thecheeselibrary.fragment.support.v4.app.StatedFragment;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Locale;

import app.akeorcist.deviceinformation.R;
import app.akeorcist.deviceinformation.adapter.SwitcherBrandAdapter;
import app.akeorcist.deviceinformation.application.DDIApplication;
import app.akeorcist.deviceinformation.constants.Status;
import app.akeorcist.deviceinformation.event.BackPressedOptionsMenuEvent;
import app.akeorcist.deviceinformation.event.ChooseBrandEvent;
import app.akeorcist.deviceinformation.event.DeviceQueryEvent;
import app.akeorcist.deviceinformation.event.DeviceSwitcherNext;
import app.akeorcist.deviceinformation.event.PagerControlEvent;
import app.akeorcist.deviceinformation.event.SearchBarEvent;
import app.akeorcist.deviceinformation.provider.BusProvider;
import app.akeorcist.deviceinformation.utility.AnimateUtils;
import app.akeorcist.deviceinformation.utility.SnackBar;

public class SwitcherBrandFragment extends StatedFragment implements View.OnClickListener, SearchView.OnQueryTextListener, MenuItemCompat.OnActionExpandListener {
    private Activity activity;
    private LinearLayout layoutContent;
    private LinearLayout layoutSearchOverlay;
    private UltimateRecyclerView rvBrand;
    private ProgressWheel progressWheel;
    private ImageView btnRefreshBrand;
    private ArrayList<String> arrBrandList = new ArrayList<>();
    private ArrayList<String> arrResultBrandList = new ArrayList<>();
    private SwitcherBrandAdapter adapter;
    private MenuItem searchItem;

    private boolean isSearchBarExpanded = false;

    // Error handle when rotate
    private int status = Status.STATUS_IDLE;

	public static SwitcherBrandFragment newInstance() {
		SwitcherBrandFragment fragment = new SwitcherBrandFragment();
		return fragment;
	}

	public SwitcherBrandFragment() { }

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_switcher_brand, container, false);

        layoutContent = (LinearLayout) rootView.findViewById(R.id.layout_content);
        layoutSearchOverlay = (LinearLayout) rootView.findViewById(R.id.layout_search_overlay);
        layoutSearchOverlay.setOnClickListener(this);

        btnRefreshBrand = (ImageButton) rootView.findViewById(R.id.btn_refresh_brand);
        btnRefreshBrand.setOnClickListener(this);
        btnRefreshBrand.setOnTouchListener(AnimateUtils.touchAnimateListener);

        rvBrand = (UltimateRecyclerView) rootView.findViewById(R.id.rv_brand);

        int column = getResources().getInteger(R.integer.switcher_brand_card_column);
        rvBrand.setLayoutManager(new StaggeredGridLayoutManager(column, StaggeredGridLayoutManager.VERTICAL));
        rvBrand.setHasFixedSize(false);
        rvBrand.enableSwipeRefresh(true);
        rvBrand.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getBrandList();
            }
        });

        progressWheel = (ProgressWheel) rootView.findViewById(R.id.progress_wheel);

        if(savedInstanceState == null) {
            initialBrand();
            progressWheel.setVisibility(View.VISIBLE);
            layoutContent.setVisibility(View.GONE);
            btnRefreshBrand.setVisibility(View.GONE);
            getBrandList();
        }

        setHasOptionsMenu(true);
		return rootView;
	}

    @Override
    protected void onSaveState(Bundle outState) {
        super.onSaveState(outState);
        outState.putStringArrayList("brand_list", arrBrandList);
        outState.putInt("status", status);
    }

    @Override
    protected void onRestoreState(Bundle savedInstanceState) {
        super.onRestoreState(savedInstanceState);
        arrBrandList = savedInstanceState.getStringArrayList("brand_list");
        arrResultBrandList = arrBrandList;
        status = savedInstanceState.getInt("status");
        setViewVisibility();
        if(status != Status.STATUS_ERROR) {
            initialBrand();
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        BusProvider.getInstance().register(this);
        BusProvider.getNetworkInstance().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
        BusProvider.getNetworkInstance().unregister(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_refresh_brand:
                btnRefreshBrand.setEnabled(false);
                AnimateUtils.scaleOut(btnRefreshBrand);
                progressWheel.setVisibility(View.VISIBLE);
                layoutContent.setVisibility(View.VISIBLE);
                getBrandList();
                break;
            case R.id.layout_search_overlay:
                collapseOptionsMenu();
                break;
        }
    }

    @Subscribe
    public void incomingBrandList(DeviceQueryEvent event) {
        if(DeviceQueryEvent.RESULT_SUCCESS.equals(event.getResult()) &&
                DeviceQueryEvent.EVENT_BRAND.equals(event.getEvent())) {
            status = Status.STATUS_SUCCESS;
            Log.e("Check", "IncomingBrandList");
            arrBrandList = event.getBrandList();
            updateBrandList(arrBrandList, true);
        } else if(DeviceQueryEvent.RESULT_FAILURE.equals(event.getResult()) &&
                DeviceQueryEvent.EVENT_BRAND.equals(event.getEvent())) {
            status = Status.STATUS_ERROR;
            notifyFailure(event.getMessage());
        }
    }

    @Subscribe
    public void onBackPressed(BackPressedOptionsMenuEvent event) {
        collapseOptionsMenu();
    }

    public void getBrandList() {
        DDIApplication.getNetworkInstance().getAllBrand();
    }

    private void hideProgressWheel() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressWheel.setVisibility(View.GONE);
            }
        });
    }

    private void updateBrandList(ArrayList<String> brandList, final boolean isFadeIn) {
        arrResultBrandList.clear();
        arrResultBrandList.addAll(brandList);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rvBrand.setRefreshing(false);
                if (adapter != null)
                    adapter.notifyDataSetChanged();
                showLayout(isFadeIn);
            }
        });
    }

    private void initialBrand() {
        adapter = new SwitcherBrandAdapter(activity, arrResultBrandList);
        adapter.setOnItemClickListener(new SwitcherBrandAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.e("Check", "OnItemClick");
                String brand = arrResultBrandList.get(position);
                BusProvider.getInstance().post(new PagerControlEvent(PagerControlEvent.MOVE_NEXT));
                BusProvider.getInstance().post(new ChooseBrandEvent(brand));
                BusProvider.getInstance().post(new DeviceSwitcherNext(SwitcherFragment.PAGE_SWITCHER_DEVICE));
                collapseOptionsMenu();
            }
        });
        rvBrand.setAdapter(adapter);
    }

    private void notifyFailure(final String message) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideProgressWheel();
                rvBrand.setRefreshing(false);
                SnackBar.showMessage(activity, message);
                AnimateUtils.scaleIn(btnRefreshBrand);
                btnRefreshBrand.setVisibility(View.VISIBLE);
                btnRefreshBrand.setEnabled(true);
                AnimateUtils.fadeOutAnimate(rvBrand, new AnimateUtils.OnProgressGoneListener() {
                    @Override
                    public void onGone() {
                        rvBrand.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    private void showLayout(final boolean isFadeIn) {
        AnimateUtils.fadeOutAnimate(progressWheel, new AnimateUtils.OnProgressGoneListener() {
            @Override
            public void onGone() {
                if (isFadeIn) {
                    AnimateUtils.fadeInAnimateWithZero(layoutContent);
                    AnimateUtils.fadeInAnimateWithZero(rvBrand);
                }
                layoutContent.setVisibility(View.VISIBLE);
                rvBrand.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setViewVisibility() {
        switch (status) {
            case Status.STATUS_SUCCESS:
                layoutContent.setVisibility(View.VISIBLE);
                progressWheel.setVisibility(View.GONE);
                btnRefreshBrand.setVisibility(View.GONE);
                break;
            case Status.STATUS_ERROR:
                layoutContent.setVisibility(View.GONE);
                progressWheel.setVisibility(View.GONE);
                btnRefreshBrand.setVisibility(View.VISIBLE);
                break;
            case Status.STATUS_WAITING:
                layoutContent.setVisibility(View.GONE);
                progressWheel.setVisibility(View.VISIBLE);
                btnRefreshBrand.setVisibility(View.GONE);
                break;
            case Status.STATUS_IDLE:
                layoutContent.setVisibility(View.VISIBLE);
                progressWheel.setVisibility(View.VISIBLE);
                btnRefreshBrand.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search_bar, menu);
        searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.search));
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(false);
        searchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(searchItem, this);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void collapseOptionsMenu() {
        MenuItemCompat.collapseActionView(searchItem);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if(arrBrandList != null && arrBrandList.size() > 0) {
            ArrayList<String> arrayList = new ArrayList<>();
            for(int i = 0 ; i < arrBrandList.size() ; i++) {
                if(arrBrandList.get(i).toLowerCase(Locale.getDefault()).contains(s.toLowerCase(Locale.getDefault())))
                    arrayList.add(arrBrandList.get(i));
            }
            updateBrandList(arrayList, false);
        }
        return true;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        // Do something when collapsed
        //layoutSearchOverlay.setVisibility(View.GONE);
        isSearchBarExpanded = true;
        BusProvider.getInstance().post(new SearchBarEvent(isSearchBarExpanded));
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        // Do something when expanded
        //layoutSearchOverlay.setVisibility(View.VISIBLE);
        isSearchBarExpanded = false;
        BusProvider.getInstance().post(new SearchBarEvent(isSearchBarExpanded));
        return true;
    }
}
