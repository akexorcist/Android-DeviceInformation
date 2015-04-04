package app.akeorcist.deviceinformation.fragment.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.akeorcist.deviceinformation.R;
import app.akeorcist.deviceinformation.data.device.DataManager;
import app.akeorcist.deviceinformation.model.Camera2Data;

public class Camera2ChildFragment extends Fragment {
    private static final String CAMERA_ID = "camera_id";

    private Activity activity;

	public static Camera2ChildFragment newInstance(int cameraId) {
		Camera2ChildFragment fragment = new Camera2ChildFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CAMERA_ID, cameraId);
        fragment.setArguments(bundle);
		return fragment;
	}

	public Camera2ChildFragment() { }

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_camera_2, container, false);

        int cameraId = getArguments().getInt(CAMERA_ID);

        TextView tvCameraId = (TextView) rootView.findViewById(R.id.tv_camera_id);
        TextView tvActiveArraySize = (TextView) rootView.findViewById(R.id.tv_active_array_size);
        TextView tvAECompensationRange = (TextView) rootView.findViewById(R.id.tv_ae_compensation_range);
        TextView tvAECompensationStep = (TextView) rootView.findViewById(R.id.tv_ae_compensation_step);
        TextView tvAvailableAEAntibandingMode = (TextView) rootView.findViewById(R.id.tv_available_ae_antibanding_mode);
        TextView tvAvailableAEMode = (TextView) rootView.findViewById(R.id.tv_available_ae_mode);
        TextView tvAvailableAFMode = (TextView) rootView.findViewById(R.id.tv_available_af_mode);
        TextView tvAvailableAETargetFpsRange = (TextView) rootView.findViewById(R.id.tv_available_ae_target_fps_range);
        TextView tvAvailableAperture = (TextView) rootView.findViewById(R.id.tv_available_aperture);
        TextView tvAvailableAWBMode = (TextView) rootView.findViewById(R.id.tv_available_awb_mode);
        TextView tvAvailableEdgeMode = (TextView) rootView.findViewById(R.id.tv_available_edge_mode);
        TextView tvAvailableEffect = (TextView) rootView.findViewById(R.id.tv_available_effect);
        TextView tvAvailableFaceDetecMode = (TextView) rootView.findViewById(R.id.tv_available_face_detect_mode);
        TextView tvAvailableFilterDensity = (TextView) rootView.findViewById(R.id.tv_available_filter_density);
        TextView tvAvailableFlash = (TextView) rootView.findViewById(R.id.tv_available_flash);
        TextView tvAvailableFocalLength = (TextView) rootView.findViewById(R.id.tv_available_focal_length);
        TextView tvAvailableHotPixelMapMode = (TextView) rootView.findViewById(R.id.tv_available_hot_pixel_map_mode);
        TextView tvAvailableHotPixelMode = (TextView) rootView.findViewById(R.id.tv_available_hot_pixel_mode);
        TextView tvAvailableJpegThumbnailSize = (TextView) rootView.findViewById(R.id.tv_available_jpeg_thumbnail_size);
        TextView tvAvailableMaxDigitalZoom = (TextView) rootView.findViewById(R.id.tv_available_max_digital_zoom);
        TextView tvAvailableNoiseReductionMode = (TextView) rootView.findViewById(R.id.tv_available_noise_reduction_mode);
        TextView tvAvailableOpticalStabilization = (TextView) rootView.findViewById(R.id.tv_available_optical_stabilization);
        TextView tvAvailableRequestCapability = (TextView) rootView.findViewById(R.id.tv_available_request_capability);
        TextView tvAvailableSceneMode = (TextView) rootView.findViewById(R.id.tv_available_scene_mode);
        TextView tvAvailableTestPatternMode = (TextView) rootView.findViewById(R.id.tv_available_test_pattern_mode);
        TextView tvAvailableToneMapMode = (TextView) rootView.findViewById(R.id.tv_available_tone_map_mode);
        TextView tvAvailableVideoStabilizationMode = (TextView) rootView.findViewById(R.id.tv_available_video_stabilization_mode);
        TextView tvColorFilterArrangement = (TextView) rootView.findViewById(R.id.tv_color_filter_arrangement);
        TextView tvExposureTimeRange = (TextView) rootView.findViewById(R.id.tv_exposure_time_range);
        TextView tvFocusDistanceCalibration = (TextView) rootView.findViewById(R.id.tv_focus_distance_calibration);
        TextView tvHyperFocalDistanceSupported = (TextView) rootView.findViewById(R.id.tv_hyper_focal_distance_supported);
        TextView tvLensFacing = (TextView) rootView.findViewById(R.id.tv_lens_facing);
        TextView tvMaxAnalogSensiticity = (TextView) rootView.findViewById(R.id.tv_max_analog_sensitivity);
        TextView tvMaxFaceCount = (TextView) rootView.findViewById(R.id.tv_max_face_count);
        TextView tvMaxNumberOutputProc = (TextView) rootView.findViewById(R.id.tv_max_number_output_proc);
        TextView tvMaxNumberOutputProcStall = (TextView) rootView.findViewById(R.id.tv_max_number_output_proc_stall);
        TextView tvMaxNumberOutputRaw = (TextView) rootView.findViewById(R.id.tv_max_number_output_raw);
        TextView tvMaxRegionAE = (TextView) rootView.findViewById(R.id.tv_max_region_ae);
        TextView tvMaxRegionAF = (TextView) rootView.findViewById(R.id.tv_max_region_af);
        TextView tvMaxRegionAWB = (TextView) rootView.findViewById(R.id.tv_max_region_awb);
        TextView tvMinimumFocusDistance = (TextView) rootView.findViewById(R.id.tv_minimum_focus_distance);
        TextView tvPartialResultCount = (TextView) rootView.findViewById(R.id.tv_partial_result_count);
        TextView tvPhysicalSize = (TextView) rootView.findViewById(R.id.tv_physical_size);
        TextView tvPipelineMaxDepth = (TextView) rootView.findViewById(R.id.tv_pipeline_max_depth);
        TextView tvPixelArraySize = (TextView) rootView.findViewById(R.id.tv_pixel_array_size);
        TextView tvReferenceIlluminant1 = (TextView) rootView.findViewById(R.id.tv_reference_illuminant_1);
        TextView tvReferenceIlluminant2 = (TextView) rootView.findViewById(R.id.tv_reference_illuminant_2);
        TextView tvScaleCroppingType = (TextView) rootView.findViewById(R.id.tv_scale_cropping_type);
        TextView tvSensitivityRange = (TextView) rootView.findViewById(R.id.tv_sensitivity_range);
        TextView tvSensorOrientation = (TextView) rootView.findViewById(R.id.tv_sensor_orientation);
        TextView tvSupportAberrationMode = (TextView) rootView.findViewById(R.id.tv_support_aberration_mode);
        TextView tvSupportHardwareLevel = (TextView) rootView.findViewById(R.id.tv_support_hardware_level);
        TextView tvSupportHighspeedVideoFpsRange = (TextView) rootView.findViewById(R.id.tv_support_highspeed_video_fps_range);
        TextView tvSupportHighspeedVideoSize = (TextView) rootView.findViewById(R.id.tv_support_highspeed_video_size);
        TextView tvSupportImageFormat = (TextView) rootView.findViewById(R.id.tv_support_image_format);
        TextView tvSupportOutputSize = (TextView) rootView.findViewById(R.id.tv_support_output_size);
        TextView tvSyncMaxLatency = (TextView) rootView.findViewById(R.id.tv_sync_max_latency);
        TextView tvTimestampSource = (TextView) rootView.findViewById(R.id.tv_timestamp_source);
        TextView tvToneMapMaxCurvePoint = (TextView) rootView.findViewById(R.id.tv_tone_map_max_curve_point);
        TextView tvWhiteLevel = (TextView) rootView.findViewById(R.id.tv_white_level);

        Camera2Data data = DataManager.getCamera2Data(activity, cameraId);
        tvCameraId.setText(data.getCameraId());
        tvActiveArraySize.setText(data.getActiveArraySize());
        tvAECompensationRange.setText(data.getAECompensationRange());
        tvAECompensationStep.setText(data.getAECompensationStep());
        tvAvailableAEAntibandingMode.setText(data.getAvailableAEAntibandingMode());
        tvAvailableAEMode.setText(data.getAvailableAEMode());
        tvAvailableAFMode.setText(data.getAvailableAFMode());
        tvAvailableAETargetFpsRange.setText(data.getAvailableAETargetFpsRange());
        tvAvailableAperture.setText(data.getAvailableAperture());
        tvAvailableAWBMode.setText(data.getAvailableAWBMode());
        tvAvailableEdgeMode.setText(data.getAvailableEdgeMode());
        tvAvailableEffect.setText(data.getAvailableEffect());
        tvAvailableFaceDetecMode.setText(data.getAvailableFaceDetectMode());
        tvAvailableFilterDensity.setText(data.getAvailableFilterDensity());
        tvAvailableFlash.setText(data.getAvailableFlash());
        tvAvailableFocalLength.setText(data.getAvailableFocalLength());
        tvAvailableHotPixelMapMode.setText(data.getAvailableHotPixelMapMode());
        tvAvailableHotPixelMode.setText(data.getAvailableHotPixelMode());
        tvAvailableJpegThumbnailSize.setText(data.getAvailableJpegThumbnailSize());
        tvAvailableMaxDigitalZoom.setText(data.getAvailableMaxDigitalZoom());
        tvAvailableNoiseReductionMode.setText(data.getAvailableNoiseReductionMode());
        tvAvailableOpticalStabilization.setText(data.getAvailableOpticalStabilization());
        tvAvailableRequestCapability.setText(data.getAvailableRequestCapability());
        tvAvailableSceneMode.setText(data.getAvailableSceneMode());
        tvAvailableTestPatternMode.setText(data.getAvailableTestPartternMode());
        tvAvailableToneMapMode.setText(data.getAvailableToneMapMode());
        tvAvailableVideoStabilizationMode.setText(data.getAvailableVideoStabilizationMode());
        tvColorFilterArrangement.setText(data.getColorFilterArrangement());
        tvExposureTimeRange.setText(data.getExposureTimeRange());
        tvFocusDistanceCalibration.setText(data.getFocusDistanceCalibration());
        tvHyperFocalDistanceSupported.setText(data.getHyperfocalDistance());
        tvLensFacing.setText(data.getLensFacing());
        tvMaxAnalogSensiticity.setText(data.getMaxAnalogSensitivity());
        tvMaxFaceCount.setText(data.getMaxFaceCount());
        tvMaxNumberOutputProc.setText(data.getMaxNumOutputProc());
        tvMaxNumberOutputProcStall.setText(data.getMaxNumOutputProcStall());
        tvMaxNumberOutputRaw.setText(data.getMaxNumOutputRaw());
        tvMaxRegionAE.setText(data.getMaxRegionAE());
        tvMaxRegionAF.setText(data.getMaxRegionAF());
        tvMaxRegionAWB.setText(data.getMaxRegionAWB());
        tvMinimumFocusDistance.setText(data.getMinimumFocusDistance());
        tvPartialResultCount.setText(data.getPartialResultCount());
        tvPhysicalSize.setText(data.getPhysicalSize());
        tvPipelineMaxDepth.setText(data.getPipelineMaxDepth());
        tvPixelArraySize.setText(data.getPixelArraySize());
        tvReferenceIlluminant1.setText(data.getReferenceIlluminant1());
        tvReferenceIlluminant2.setText(data.getReferenceIlluminant2());
        tvScaleCroppingType.setText(data.getScaleCroppingType());
        tvSensitivityRange.setText(data.getSensitivityRange());
        tvSensorOrientation.setText(data.getSensorOrientation());
        tvSupportAberrationMode.setText(data.getColorCorrectionAberrationMode());
        tvSupportHardwareLevel.setText(data.getSupportHardwareLevel());
        tvSupportHighspeedVideoFpsRange.setText(data.getHighSpeedVideoFpsRange());
        tvSupportHighspeedVideoSize.setText(data.getHighSpeedVideoSize());
        tvSupportImageFormat.setText(data.getSupportImageFormat());
        tvSupportOutputSize.setText(data.getSupportOutputSize());
        tvSyncMaxLatency.setText(data.getSyncMaxLatency());
        tvTimestampSource.setText(data.getTimestampSource());
        tvToneMapMaxCurvePoint.setText(data.getToneMapMaxCurvePoint());
        tvWhiteLevel.setText(data.getWhiteLevel());

        return rootView;
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }
}
