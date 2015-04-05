package app.akeorcist.deviceinformation.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.ms.square.android.etsyblur.BlurDialogFragmentHelper;

import app.akeorcist.deviceinformation.R;

/**
 * Created by Akexorcist on 3/14/15 AD.
 */
public class FeatureDialog extends DialogFragment {
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_VERSION = "version";
    private TextView tvTitle = null;
    private TextView tvDescription = null;
    private TextView tvMinSdk = null;
    private BlurDialogFragmentHelper blurHelper = null;

    public static FeatureDialog newInstance(String title, String description, String version) {
        FeatureDialog dialog = new FeatureDialog();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TITLE, title);
        bundle.putString(KEY_DESCRIPTION, description);
        bundle.putString(KEY_VERSION, version);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        blurHelper = new BlurDialogFragmentHelper(this);
        blurHelper.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        View rootView = inflater.inflate(R.layout.dialog_feature_detail, container, false);

        tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        tvTitle.setText(getArguments().getString(KEY_TITLE));
        tvDescription = (TextView) rootView.findViewById(R.id.tv_description);
        tvDescription.setText(getArguments().getString(KEY_DESCRIPTION));
        tvMinSdk = (TextView) rootView.findViewById(R.id.tv_min_sdk);
        tvMinSdk.setText(getString(R.string.feature_api_level) + " " + getArguments().getString(KEY_VERSION));
        setCancelable(true);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        blurHelper.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        blurHelper.onStart();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        blurHelper.onCancel(dialog);
        super.onCancel(dialog);
    }

    @Override
    public void onStop() {
        blurHelper.onCancel(getDialog());
        super.onStop();
    }
}