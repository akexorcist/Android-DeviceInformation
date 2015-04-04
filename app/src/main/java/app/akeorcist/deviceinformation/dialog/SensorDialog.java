package app.akeorcist.deviceinformation.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.ms.square.android.etsyblur.BlurDialogFragmentHelper;

import app.akeorcist.deviceinformation.R;
import app.akeorcist.deviceinformation.model.SensorData;

/**
 * Created by Akexorcist on 3/14/15 AD.
 */
public class SensorDialog extends DialogFragment {
    private static final String KEY_NAME = "name";
    private static final String KEY_VENDOR = "vendor";
    private static final String KEY_TYPE = "type";
    private static final String KEY_VERSION = "version";
    private static final String KEY_POWER = "power";
    private static final String KEY_MAX_RANGE = "max_range";
    private static final String KEY_RESOLUTION = "resolution";
    private static final String KEY_MIN_DELAY = "min_delay";
    private static final String KEY_MAX_DELAY = "max_delay";
    private static final String KEY_FIFO_RESERVED = "fifo_reserved";
    private static final String KEY_FIFO_MAX = "fifo_max";

    public TextView tvName = null;
    public TextView tvVendor = null;
    public TextView tvType = null;
    public TextView tvVersion = null;
    public TextView tvPower = null;
    public TextView tvMaxRange = null;
    public TextView tvResolution = null;
    public TextView tvMinDelay = null;
    public TextView tvMaxDelay = null;
    public TextView tvFifoReserved = null;
    public TextView tvFifoMax = null;

    private BlurDialogFragmentHelper blurHelper = null;

    public static SensorDialog newInstance(SensorData sensorData) {
        SensorDialog dialog = new SensorDialog();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_NAME, sensorData.getName());
        bundle.putString(KEY_VENDOR, sensorData.getVendor());
        bundle.putString(KEY_TYPE, sensorData.getType());
        bundle.putString(KEY_VERSION, sensorData.getVersion());
        bundle.putString(KEY_POWER, sensorData.getPower());
        bundle.putString(KEY_MAX_RANGE, sensorData.getMaxRange());
        bundle.putString(KEY_RESOLUTION, sensorData.getResolution());
        bundle.putString(KEY_MIN_DELAY, sensorData.getMinDelay());
        bundle.putString(KEY_MAX_DELAY, sensorData.getMaxDelay());
        bundle.putString(KEY_FIFO_RESERVED, sensorData.getFifoReserved());
        bundle.putString(KEY_FIFO_MAX, sensorData.getFifoMax());
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


        View rootView = inflater.inflate(R.layout.dialog_sensor_detail, container, false);

        tvName = (TextView) rootView.findViewById(R.id.tv_name);
        tvVendor = (TextView) rootView.findViewById(R.id.tv_vendor);
        tvType = (TextView) rootView.findViewById(R.id.tv_type);
        tvVersion = (TextView) rootView.findViewById(R.id.tv_version);
        tvPower = (TextView) rootView.findViewById(R.id.tv_power);
        tvMaxRange = (TextView) rootView.findViewById(R.id.tv_max_range);
        tvResolution = (TextView) rootView.findViewById(R.id.tv_resolution);
        tvMinDelay = (TextView) rootView.findViewById(R.id.tv_min_delay);
        tvMaxDelay = (TextView) rootView.findViewById(R.id.tv_max_delay);
        tvFifoReserved = (TextView) rootView.findViewById(R.id.tv_fifo_reserved_event);
        tvFifoMax = (TextView) rootView.findViewById(R.id.tv_fifo_max_event);

        Bundle bundle = getArguments();
        tvName.setText(bundle.getString(KEY_NAME));
        tvVendor.setText(bundle.getString(KEY_VENDOR));
        tvType.setText(bundle.getString(KEY_TYPE));
        tvVersion.setText(bundle.getString(KEY_VERSION));
        tvPower.setText(bundle.getString(KEY_POWER));
        tvMaxRange.setText(bundle.getString(KEY_MAX_RANGE));
        tvResolution.setText(bundle.getString(KEY_RESOLUTION));
        tvMinDelay.setText(bundle.getString(KEY_MIN_DELAY));
        tvMaxDelay.setText(bundle.getString(KEY_MAX_DELAY));
        tvFifoReserved.setText(bundle.getString(KEY_FIFO_RESERVED));
        tvFifoMax.setText(bundle.getString(KEY_FIFO_MAX));
        
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