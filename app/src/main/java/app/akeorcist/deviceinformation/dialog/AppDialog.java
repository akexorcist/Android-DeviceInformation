package app.akeorcist.deviceinformation.dialog;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ms.square.android.etsyblur.BlurDialogFragmentHelper;

import app.akeorcist.deviceinformation.R;
import app.akeorcist.deviceinformation.model.AppData;

/**
 * Created by Akexorcist on 3/14/15 AD.
 */
public class AppDialog extends DialogFragment {
    private static final String KEY_NAME = "title";
    private static final String KEY_PACKAGE = "description";
    private static final String KEY_VERSION_CODE = "version_code";
    private static final String KEY_VERSION_NAME = "version_name";
    private static final String KEY_PERMISSION = "permission";
    private static final String KEY_ICON_RESOURCE = "icon_resource";
    private TextView tvAppName = null;
    private TextView tvAppPackage = null;
    private TextView tvAppVersionCode = null;
    private TextView tvAppVersionName = null;
    private TextView tvAppPermission = null;
    private ImageView ivAppIcon = null;
    private LinearLayout layoutAppPermission = null;
    private BlurDialogFragmentHelper blurHelper = null;

    public static AppDialog newInstance(AppData appData) {
        AppDialog dialog = new AppDialog();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_NAME, appData.getName());
        bundle.putString(KEY_PACKAGE, appData.getPackage());
        bundle.putString(KEY_VERSION_CODE, appData.getVersionCode());
        bundle.putString(KEY_VERSION_NAME, appData.getVersionName());
        bundle.putInt(KEY_ICON_RESOURCE, appData.getIconResource());

        if(appData.getPermissions().size() > 0) {
            String strPermission = "";
            for (String permission : appData.getPermissions()) {
                strPermission += permission + "\n\n";
            }
            strPermission = strPermission.trim();
            bundle.putString(KEY_PERMISSION, strPermission);
        } else {
            bundle.putString(KEY_PERMISSION, "");
        }
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

        Bundle bundle = getArguments();

        View rootView = inflater.inflate(R.layout.dialog_app_detail, container, false);

        ivAppIcon = (ImageView) rootView.findViewById(R.id.iv_app_icon);
        int resIcon = bundle.getInt(KEY_ICON_RESOURCE);
        String pack = bundle.getString(KEY_PACKAGE);
        if(resIcon != 0) {
            Uri uri = Uri.parse("android.resource://" + pack + "/" + resIcon);
            Glide.with(getActivity()).load(uri).into(ivAppIcon);
        } else {
            Glide.with(getActivity()).load(R.drawable.ic_app_default).into(ivAppIcon);
        }

        tvAppName = (TextView) rootView.findViewById(R.id.tv_app_name);
        tvAppName.setText(bundle.getString(KEY_NAME));
        tvAppPackage = (TextView) rootView.findViewById(R.id.tv_app_package);
        tvAppPackage.setText(pack);
        tvAppVersionCode = (TextView) rootView.findViewById(R.id.tv_app_version_code);
        tvAppVersionCode.setText(bundle.getString(KEY_VERSION_CODE));
        tvAppVersionName = (TextView) rootView.findViewById(R.id.tv_app_version_name);
        tvAppVersionName.setText(bundle.getString(KEY_VERSION_NAME));
        tvAppPermission = (TextView) rootView.findViewById(R.id.tv_app_permission);
        layoutAppPermission = (LinearLayout) rootView.findViewById(R.id.layout_app_permission);
        String permission = bundle.getString(KEY_PERMISSION);
        if(!permission.equals("")) {
            tvAppPermission.setText(permission);
        } else {
            layoutAppPermission.setVisibility(View.GONE);
        }

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
}