package app.akeorcist.deviceinformation.fragment.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import app.akeorcist.deviceinformation.R;
import app.akeorcist.deviceinformation.dialog.FeatureDialog;
import app.akeorcist.deviceinformation.utility.StringUtils;
import app.akeorcist.deviceinformation.adapter.FeatureCardAdapter;

public class FeatureChildFragment extends Fragment {
    private static final String SUPPORT_TYPE = "support_type";

    private Activity activity;

	public static FeatureChildFragment newInstance(String supportType) {
		FeatureChildFragment fragment = new FeatureChildFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SUPPORT_TYPE, supportType);
        fragment.setArguments(bundle);
		return fragment;
	}

	public FeatureChildFragment() { }

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_viewpager_child_from, container, false);

        String featureType = StringUtils.wrapBlank(getArguments().getString(SUPPORT_TYPE));
        RecyclerView.Adapter adapter = new FeatureCardAdapter(activity, getFragmentManager(), featureType);
        RecyclerView rvFeatureCard = (RecyclerView) rootView.findViewById(R.id.rv_form_card);
        int column = getResources().getInteger(R.integer.feature_card_column);
        rvFeatureCard.setLayoutManager(new StaggeredGridLayoutManager(column, StaggeredGridLayoutManager.VERTICAL));
        rvFeatureCard.setAdapter(adapter);

		return rootView;
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }
}
