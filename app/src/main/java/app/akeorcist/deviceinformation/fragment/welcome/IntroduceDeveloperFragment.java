package app.akeorcist.deviceinformation.fragment.welcome;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.squareup.otto.Produce;

import app.akeorcist.deviceinformation.R;
import app.akeorcist.deviceinformation.event.WelcomeEvent;
import app.akeorcist.deviceinformation.provider.BusProvider;

public class IntroduceDeveloperFragment extends Fragment {

    public static IntroduceDeveloperFragment newInstance() {
        IntroduceDeveloperFragment fragment = new IntroduceDeveloperFragment();
        return fragment;
    }

    public IntroduceDeveloperFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_welcome_developer, null);
        Button buttonOk = (Button) rootView.findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusProvider.getInstance().post(ok());
            }
        });
        return rootView;
    }

    @Produce
    public WelcomeEvent ok() {
        return new WelcomeEvent(WelcomeEvent.EVENT_SKIP);
    }
}
