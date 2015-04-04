package app.akeorcist.deviceinformation.fragment.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.akeorcist.deviceinformation.R;
import app.akeorcist.deviceinformation.data.device.CameraManager;
import app.akeorcist.deviceinformation.data.device.DataManager;
import app.akeorcist.deviceinformation.model.CameraData;
import app.akeorcist.deviceinformation.parser.FileParser;

public class CameraChildFragment extends Fragment {
    private static final String CAMERA_ID = "camera_id";

    private Activity activity;

	public static CameraChildFragment newInstance(int cameraId) {
		CameraChildFragment fragment = new CameraChildFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CAMERA_ID, cameraId);
        fragment.setArguments(bundle);
		return fragment;
	}

	public CameraChildFragment() { }

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_camera, container, false);

        int cameraId = getArguments().getInt(CAMERA_ID);

        TextView tvCameraId = (TextView) rootView.findViewById(R.id.tv_camera_id);
        TextView tvAntibanding = (TextView) rootView.findViewById(R.id.tv_antibanding);
        TextView tvShutterSound = (TextView) rootView.findViewById(R.id.tv_shutter_sound);
        TextView tvColorEffect = (TextView) rootView.findViewById(R.id.tv_color_effect);
        TextView tvFacing = (TextView) rootView.findViewById(R.id.tv_facing);
        TextView tvFlashMode = (TextView) rootView.findViewById(R.id.tv_flash_mode);
        TextView tvFocusMode = (TextView) rootView.findViewById(R.id.tv_focus_mode);
        TextView tvJpegThumbnail = (TextView) rootView.findViewById(R.id.tv_jpeg_thumbnail);
        TextView tvImageOrientation = (TextView) rootView.findViewById(R.id.tv_image_orientation);
        TextView tvPictureFormat = (TextView) rootView.findViewById(R.id.tv_picture_format);
        TextView tvPreviewFormat = (TextView) rootView.findViewById(R.id.tv_preview_format);
        TextView tvPreviewFramerate = (TextView) rootView.findViewById(R.id.tv_preview_framerate);
        TextView tvPictureSize = (TextView) rootView.findViewById(R.id.tv_picture_size);
        TextView tvPreviewSize = (TextView) rootView.findViewById(R.id.tv_preview_size);
        TextView tvPreviewFpsRange = (TextView) rootView.findViewById(R.id.tv_preview_fps_range);
        TextView tvSceneMode = (TextView) rootView.findViewById(R.id.tv_scene_mode);
        TextView tvVideoQualityProfile = (TextView) rootView.findViewById(R.id.tv_video_quality_profile);
        TextView tvTimelapseQualityProfile = (TextView) rootView.findViewById(R.id.tv_timelapse_quality_profile);
        TextView tvHighSpeedQualityProfile = (TextView) rootView.findViewById(R.id.tv_highspeed_quality_profile);
        TextView tvVideoSize = (TextView) rootView.findViewById(R.id.tv_video_size);
        TextView tvWhiteBalance = (TextView) rootView.findViewById(R.id.tv_white_balance);
        TextView tvAutoExposure = (TextView) rootView.findViewById(R.id.tv_auto_exposure);
        TextView tvAutoWhiteBalance = (TextView) rootView.findViewById(R.id.tv_auto_white_balance);
        TextView tvSmoothZoom = (TextView) rootView.findViewById(R.id.tv_smooth_zoom);
        TextView tvVideoSnapshot = (TextView) rootView.findViewById(R.id.tv_video_snapshot);
        TextView tvVideoStabilization = (TextView) rootView.findViewById(R.id.tv_video_stabilizatioin);
        TextView tvZoomSupported = (TextView) rootView.findViewById(R.id.tv_zoom_supported);

        CameraData data = DataManager.getCameraData(activity, cameraId);
        if(data != null) {
            tvAntibanding.setText(data.getAntibanding());
            tvAutoExposure.setText(data.getAutoExposure());
            tvAutoWhiteBalance.setText(data.getAutoWhiteBalance());
            tvCameraId.setText(data.getCameraId());
            tvColorEffect.setText(data.getColorEffect());
            tvFacing.setText(data.getFacing());
            tvFlashMode.setText(data.getFlashMode());
            tvFocusMode.setText(data.getFocusMode());
            tvHighSpeedQualityProfile.setText(data.getHighSpeedQualityProfile());
            tvImageOrientation.setText(data.getImageOrientation());
            tvJpegThumbnail.setText(data.getJpegThumbnailSize());
            tvPictureFormat.setText(data.getPictureFormat());
            tvPreviewFormat.setText(data.getPreviewFormat());
            tvPictureSize.setText(data.getPictureSize());
            tvPreviewSize.setText(data.getPreviewSize());
            tvPreviewFpsRange.setText(data.getPreviewFpsRange());
            tvPreviewFramerate.setText(data.getPreviewFramerate());
            tvSceneMode.setText(data.getSceneMode());
            tvShutterSound.setText(data.getShutterSound());
            tvSmoothZoom.setText(data.getSmoothZoom());
            tvTimelapseQualityProfile.setText(data.getTimelapseQualityProfile());
            tvVideoQualityProfile.setText(data.getVideoQualityProfile());
            tvVideoSize.setText(data.getVideoSize());
            tvVideoSnapshot.setText(data.getVideoSnapshot());
            tvVideoStabilization.setText(data.getVideoStabilization());
            tvWhiteBalance.setText(data.getWhiteBalance());
            tvZoomSupported.setText(data.getZoom());
        }
        
		return rootView;
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }
}
