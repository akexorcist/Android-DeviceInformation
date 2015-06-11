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
import app.akeorcist.deviceinformation.event.WelcomeEvent;
import app.akeorcist.deviceinformation.provider.BusProvider;

public class DescriptionLastFragment extends Fragment {

    public static DescriptionLastFragment newInstance() {
        return new DescriptionLastFragment();
    }

    public DescriptionLastFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_description_last, null);
        Button buttonOk = (Button) rootView.findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusProvider.getInstance().post(ok());
            }
        });

        TextView tvMenu = (TextView) rootView.findViewById(R.id.tv_menu);
        TextView tvDescription = (TextView) rootView.findViewById(R.id.tv_description);
        ImageView ivMenuIcon = (ImageView) rootView.findViewById(R.id.iv_menu_icon);

        // App List Menu Descrption
        tvMenu.setText(getResources().getString(R.string.menu_change_device));
        tvDescription.setText(getResources().getString(R.string.welcome_description_switcher));
        ivMenuIcon.setImageResource(R.drawable.menu_icon_switcher);
        ivMenuIcon.setBackgroundResource(R.drawable.shape_oval_blue);

        return rootView;
    }

    @Produce
    public WelcomeEvent ok() {
        return new WelcomeEvent(WelcomeEvent.EVENT_SKIP);
    }
}
