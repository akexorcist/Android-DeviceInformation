package app.akeorcist.deviceinformation.fragment.welcome;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.otto.Produce;

import app.akeorcist.deviceinformation.R;
import app.akeorcist.deviceinformation.constants.Page;
import app.akeorcist.deviceinformation.event.WelcomeEvent;
import app.akeorcist.deviceinformation.provider.BusProvider;

public class DescriptionFragment extends Fragment {

    public static DescriptionFragment newInstance(int page) {
        DescriptionFragment fragment = new DescriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("page", page);
        fragment.setArguments(bundle);
        return fragment;
    }

    public DescriptionFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_description, null);
        Button buttonNext = (Button) rootView.findViewById(R.id.button_next);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusProvider.getInstance().post(next());
            }
        });
        Button buttonSkip = (Button) rootView.findViewById(R.id.button_skip);
        buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusProvider.getInstance().post(skip());
            }
        });

        TextView tvDescription = (TextView) rootView.findViewById(R.id.tv_description);
        TextView tvMenu = (TextView) rootView.findViewById(R.id.tv_menu);
        ImageView ivMenuIcon = (ImageView) rootView.findViewById(R.id.iv_menu_icon);

        int page = getArguments().getInt("page");
        if(page == Page.SUBMIT) {
            tvMenu.setText(R.string.menu_submit);
            tvDescription.setText(getResources().getString(R.string.welcome_description_submit));
            ivMenuIcon.setImageResource(R.drawable.menu_icon_submit);
            ivMenuIcon.setBackgroundResource(R.drawable.shape_oval_red);
        } else if(page == Page.HARDWARE) {
            tvMenu.setText(R.string.menu_hardware);
            tvDescription.setText(getResources().getString(R.string.welcome_description_hardware));
            ivMenuIcon.setImageResource(R.drawable.menu_icon_hardware);
            ivMenuIcon.setBackgroundResource(R.drawable.shape_oval_red);
        } else if(page == Page.SENSOR) {
            tvMenu.setText(R.string.menu_sensor);
            tvDescription.setText(getResources().getString(R.string.welcome_description_sensor));
            ivMenuIcon.setImageResource(R.drawable.menu_icon_sensor);
            ivMenuIcon.setBackgroundResource(R.drawable.shape_oval_red);
        } else if(page == Page.SCREEN) {
            tvMenu.setText(R.string.menu_screen);
            tvDescription.setText(getResources().getString(R.string.welcome_description_screen));
            ivMenuIcon.setImageResource(R.drawable.menu_icon_screen);
            ivMenuIcon.setBackgroundResource(R.drawable.shape_oval_red);
        } else if(page == Page.CAMERA) {
            tvMenu.setText(R.string.menu_camera);
            tvDescription.setText(getResources().getString(R.string.welcome_description_camera));
            ivMenuIcon.setImageResource(R.drawable.menu_icon_camera);
            ivMenuIcon.setBackgroundResource(R.drawable.shape_oval_red);
        } else if(page == Page.CAMERA2) {
            tvMenu.setText(R.string.menu_camera2);
            tvDescription.setText(getResources().getString(R.string.welcome_description_camera2));
            ivMenuIcon.setImageResource(R.drawable.menu_icon_camera2);
            ivMenuIcon.setBackgroundResource(R.drawable.shape_oval_red);
        } else if(page == Page.FEATURE) {
            tvMenu.setText(R.string.menu_features);
            tvDescription.setText(getResources().getString(R.string.welcome_description_feature));
            ivMenuIcon.setImageResource(R.drawable.menu_icon_feature);
            ivMenuIcon.setBackgroundResource(R.drawable.shape_oval_red);
        } else if(page == Page.APP_LIST) {
            tvMenu.setText(R.string.menu_applist);
            tvDescription.setText(getResources().getString(R.string.welcome_description_applist));
            ivMenuIcon.setImageResource(R.drawable.menu_icon_app);
            ivMenuIcon.setBackgroundResource(R.drawable.shape_oval_red);
        }

        return rootView;
    }

    @Produce
    public WelcomeEvent next() {
        return new WelcomeEvent(WelcomeEvent.EVENT_NEXT);
    }

    @Produce
    public WelcomeEvent skip() {
        return new WelcomeEvent(WelcomeEvent.EVENT_SKIP);
    }
}
