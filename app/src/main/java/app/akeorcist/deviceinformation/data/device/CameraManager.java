package app.akeorcist.deviceinformation.data.device;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.os.Build;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.akeorcist.deviceinformation.model.CameraData;
import app.akeorcist.deviceinformation.utility.StringUtils;

/**
 * Created by Ake on 3/1/2015.
 */
public class CameraManager {
    private static final int DATA_COUNT = 27;
    private static ArrayList<CameraData> cameraDataList = null;

    public static void initialData(Activity activity) {
        cameraDataList = new ArrayList<>();
        int cameraCount = 0;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            cameraCount = Camera.getNumberOfCameras();
        } else {
            Camera cam = Camera.open();
            if(cam == null)
                cameraCount = 0;
            else
                cameraCount = 1;
        }

        for(int i = 0 ; i < cameraCount ; i++) {
            CameraData data = null;
            Camera.Parameters params = null;

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                    Camera.CameraInfo ci = new Camera.CameraInfo();
                    Camera.getCameraInfo(i, ci);
                    Camera mCamera = Camera.open(i);
                    params = mCamera.getParameters();
                    mCamera.release();
                    data = new CameraData();
                    data.setShutterSound(canDisableShutterSound(ci));
                    data.setImageOrientation(getImageOrientation(ci));
                    data.setFacing(getCameraFacing(ci));
                } else {
                    Camera mCamera = Camera.open();
                    params = mCamera.getParameters();
                    mCamera.release();
                    data = new CameraData();
                    data.setShutterSound("unknown");
                    data.setImageOrientation("unknown");
                    data.setFacing("unknown");
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
                Toast.makeText(activity, "Failed to connect to some camera\nPlease restart device before", Toast.LENGTH_LONG).show();
            }

            if(data != null && params != null) {
                data.setCameraId(i + "");
                data.setAntibanding(getSupportedAntibanding(params));
                data.setColorEffect(getSupportedColorEffects(params));
                data.setFlashMode(getSupportedFlashModes(params));
                data.setFocusMode(getSupportedFocusModes(params));
                data.setJpegThumbnailSize(getSupportedJpegThumbnailSizes(params));
                data.setPictureFormat(getSupportedPictureFormat(params));
                data.setPreviewFormat(getSupportedPreviewFormat(params));
                data.setPreviewFramerate(getSupportedPreviewFrameRate(params));
                data.setPictureSize(getSupportedPictureSize(params));
                data.setPreviewSize(getSupportedPreviewSize(params));
                data.setPreviewFpsRange(getSupportedPreviewFpsRange(params));
                data.setSceneMode(getSupportedSceneMode(params));
                data.setVideoQualityProfile(getSupportedVideoSize(params));
                data.setTimelapseQualityProfile(getQualityTimeLapseProfile(params, i));
                data.setHighSpeedQualityProfile(getQualityHighSpeedProfile(params, i));
                data.setVideoSize(getSupportedVideoSize(params));
                data.setWhiteBalance(getSupportedWhiteBalance(params));
                data.setAutoExposure(isAutoExposureLockSupported(params));
                data.setAutoWhiteBalance(isAutoWhiteBalanceLockSupported(params));
                data.setSmoothZoom(isSmoothZoomSupported(params));
                data.setVideoSnapshot(isVideoSnapshotSupported(params));
                data.setVideoStabilization(isVideoStabilizationSupported(params));
                data.setZoom(isZoomSupported(params));

                cameraDataList.add(data);
            }
        }
    }

    public static void destroy() {
        if(cameraDataList != null)
            cameraDataList.clear();
        cameraDataList = null;
    }

    public static int getCameraCount() {
        return cameraDataList.size();
    }

    public static CameraData getCameraData(int position) {
        return cameraDataList.get(position);
    }

    public static int getCameraDataCount() {
        return DATA_COUNT;
    }

    @SuppressWarnings("deprecation")
    private static String isAutoExposureLockSupported(Camera.Parameters params) {
        try {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                if(params.isAutoExposureLockSupported()) {
                    return "yes";
                } else {
                    return "no";
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "unknown";
    }

    @SuppressWarnings("deprecation")
    private static String isAutoWhiteBalanceLockSupported(Camera.Parameters params) {
        try {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                if(params.isAutoWhiteBalanceLockSupported()) {
                    return "yes";
                } else {
                    return "no";
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return "unknown";
    }
    @SuppressWarnings("deprecation")
    private static String isSmoothZoomSupported(Camera.Parameters params) {
        try {
            if(params.isSmoothZoomSupported()) {
                return "yes";
            } else {
                return "no";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "unknown";
    }

    @SuppressWarnings("deprecation")
    private static String isVideoSnapshotSupported(Camera.Parameters params) {
        try {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                if(params.isVideoSnapshotSupported()) {
                    return "yes";
                } else {
                    return "no";
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "unknown";
    }

    @SuppressWarnings("deprecation")
    private static String isVideoStabilizationSupported(Camera.Parameters params) {
        try {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                if(params.isVideoStabilizationSupported()) {
                    return "yes";
                } else {
                    return "no";
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "unknown";
    }

    @SuppressWarnings("deprecation")
    private static String isZoomSupported(Camera.Parameters params) {
        try {
            if(params.isZoomSupported()) {
                return "yes";
            } else {
                return "no";
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "unknown";
    }

    @SuppressWarnings("deprecation")
    private static String getSupportedAntibanding(Camera.Parameters params) {
        List<String> values = params.getSupportedAntibanding();
        if(values != null) {
            String str = "";
            for(String value : values) {
                str += value + " ";
            }
            return StringUtils.wrapUnknownLower(str).trim();
        }
        return "unknown";
    }

    @SuppressWarnings("deprecation")
    private static String getSupportedColorEffects(Camera.Parameters params) {
        List<String> effects = params.getSupportedColorEffects();
        if(effects != null) {
            String str = "";
            for(String effect : effects) {
                str += effect + " ";
            }
            return StringUtils.wrapUnknownLower(str).trim();
        }
        return "unknown";
    }

    @SuppressWarnings("deprecation")
    private static String getSupportedFlashModes(Camera.Parameters params) {
        List<String> modes = params.getSupportedFlashModes();
        if(modes != null) {
            String str = "";
            for(String mode : modes) {
                str += mode + " ";
            }
            return StringUtils.wrapUnknownLower(str).trim();
        }
        return "unknown";
    }

    @SuppressWarnings("deprecation")
    private static String getSupportedFocusModes(Camera.Parameters params) {
        List<String> modes = params.getSupportedFocusModes();
        if(modes != null) {
            String str = "";
            for(String mode : modes) {
                str += mode + " ";
            }
            return StringUtils.wrapUnknownLower(str).trim();
        }
        return "unknown";
    }

    @SuppressWarnings("deprecation")
    private static String getSupportedJpegThumbnailSizes(Camera.Parameters params) {
        List<Camera.Size> sizes = params.getSupportedJpegThumbnailSizes();
        if(sizes != null) {
            String str = "";
            for(Camera.Size size : sizes) {
                str += size.width + "x" + size.height + " ";
            }
            return StringUtils.wrapUnknownLower(str).trim();
        }
        return "unknown";
    }



    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private static String getCameraFacing(Camera.CameraInfo ci) {
        int facing = ci.facing;
        if(facing == Camera.CameraInfo.CAMERA_FACING_BACK)
            return "back";
        else if(facing == Camera.CameraInfo.CAMERA_FACING_FRONT)
            return "front";
        return "unknown";
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private static String getImageOrientation(Camera.CameraInfo ci) {
        return StringUtils.wrapUnknownLower(ci.orientation + "");
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private static String canDisableShutterSound(Camera.CameraInfo ci) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return StringUtils.wrapUnknownLower(ci.canDisableShutterSound + "");
        }
        return "unknown";
    }

    @SuppressWarnings("deprecation")
    private static String getSupportedPictureFormat(Camera.Parameters params) {
        List<Integer> formats = params.getSupportedPictureFormats();
        if(formats != null) {
            return StringUtils.wrapUnknownLower(getSupportFormat(formats)).trim();
        }
        return "unknown";
    }

    @SuppressWarnings("deprecation")
    private static String getSupportedPreviewFormat(Camera.Parameters params) {
        List<Integer> formats = params.getSupportedPreviewFormats();
        if(formats != null) {
            return StringUtils.wrapUnknownLower(getSupportFormat(formats)).trim();
        }
        return "unknown";
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private static String getSupportFormat(List<Integer> formats) {
        String str = "";
        for(Integer format : formats) {
            if(format.intValue() == ImageFormat.JPEG) {
                str += "JPEG ";
            } else if(format.intValue() == ImageFormat.YV12) {
                str += "YV12 ";
            } else if(format.intValue() == ImageFormat.YUY2) {
                str += "YUY2 ";
            } else if(format.intValue() == ImageFormat.RGB_565) {
                str += "RGB_565 ";
            } else if(format.intValue() == ImageFormat.NV16) {
                str += "NV16 ";
            } else if(format.intValue() == ImageFormat.NV21) {
                str += "NV21 ";
            } else if(format.intValue() == ImageFormat.RAW10) {
                str += "RAW10 ";
            } else if(format.intValue() == ImageFormat.YUV_420_888) {
                str += "YUV_420_888 ";
            } else if(format.intValue() == ImageFormat.RAW_SENSOR) {
                str += "RAW_SENSOR ";
            } else {
                str += "unknown (" + format.intValue() + ")";
            }
        }
        return str;
    }

    @SuppressWarnings("deprecation")
    private static String getSupportedPreviewFrameRate(Camera.Parameters params) {
        List<Integer> rates = params.getSupportedPreviewFrameRates();
        if(rates != null) {
            String str = "";
            str = rates.get(0) + "-" + rates.get(rates.size() - 1);
            return StringUtils.wrapUnknownLower(str).trim();
        } else {
            return "unknown";
        }
    }

    @SuppressWarnings("deprecation")
    private static String getSupportedPictureSize(Camera.Parameters params) {
        List<Camera.Size> sizes = params.getSupportedPictureSizes();
        if(sizes != null) {
            String str = "";
            for(Camera.Size size : sizes)
                str += size.width + "x" + size.height + " ";
            return StringUtils.wrapUnknownLower(str).trim();
        }
        return "unknown";
    }

    @SuppressWarnings("deprecation")
    private static String getSupportedPreviewSize(Camera.Parameters params) {
        List<Camera.Size> sizes = params.getSupportedPreviewSizes();
        if(sizes != null) {
            String str = "";
            for(Camera.Size size : sizes) {
                str += size.width + "x" + size.height + " ";
            }
            return StringUtils.wrapUnknownLower(str).trim();
        }
        return "unknown";
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private static String getSupportedPreviewFpsRange(Camera.Parameters params) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            List<int[]> ranges = params.getSupportedPreviewFpsRange();
            if(ranges != null) {
                String str = "";
                for(int i = 0 ; i < ranges.size() ; i++) {
                    str += (float)ranges.get(i)[0] / 1000 + "-" + (float)ranges.get(i)[ranges.get(i).length - 1] / 1000 + " ";
                }
                return StringUtils.wrapUnknownLower(str).trim();
            }
            return "unknown";
        }
        return "unknown";
    }

    @SuppressWarnings("deprecation")
    private static String getSupportedSceneMode(Camera.Parameters params) {
        List<String> modes = params.getSupportedSceneModes();
        if(modes != null) {
            String str = "";
            for(String mode : modes) {
                str += mode + " ";
            }
            return StringUtils.wrapUnknownLower(str).trim();
        } else {
            return "unknown";
        }
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private static String getSupportedVideoSize(Camera.Parameters params) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            List<Camera.Size> sizes = params.getSupportedVideoSizes();
            if(sizes != null) {
                String str = "";
                for(Camera.Size size : sizes) {
                    str += size.width + "x" + size.height + " ";
                }
                return StringUtils.wrapUnknownLower(str).trim();
            }
            return "unknown";
        }
        return "unknown";
    }

    @SuppressWarnings("deprecation")
    private static String getSupportedWhiteBalance(Camera.Parameters params) {
        List<String> modes = params.getSupportedWhiteBalance();
        if(modes != null) {
            String str = "";
            for(String mode : modes)
                str += mode + " ";
            return StringUtils.wrapUnknownLower(str).trim();
        } else {
            return "unknown";
        }
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private static String getQualityProfile(Camera.Parameters params, int cameraId) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            String str = "";
            if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_QCIF)) {
                str += "176x144 ";
            }
            if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_QVGA)) {
                str += "320x240 ";
            }
            if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_CIF)) {
                str += "352x288 ";
            }
            if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_480P)) {
                str += "720x480 ";
            }
            if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_720P)) {
                str += "1280x720 ";
            }
            if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_1080P)) {
                str += "1920x1080 ";
            }
            if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_2160P)) {
                str += "3840x2160 ";
            }
            if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_LOW)) {
                str += "lowest-quality ";
            }
            if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_HIGH)) {
                str += "highest-quality ";
            }
            if(CamcorderProfile.hasProfile(cameraId, 0x09)) {
                str += "unknown(0x09) ";
            }
            if(CamcorderProfile.hasProfile(cameraId, 0x0A)) {
                str += "unknown(0x0A) ";
            }
            if(CamcorderProfile.hasProfile(cameraId, 0x0B)) {
                str += "unknown(0x0B) ";
            }
            if(str.isEmpty()) {
                str += "unknown";
            }
            return StringUtils.wrapUnknownLower(str).trim();
        } else {
            return "unknown";
        }
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private static String getQualityTimeLapseProfile(Camera.Parameters params, int cameraId) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            String str = "";
            if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_TIME_LAPSE_QCIF)) {
                str += "176x144 ";
            }
            if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_TIME_LAPSE_QVGA)) {
                str += "320x240 ";
            }
            if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_TIME_LAPSE_CIF)) {
                str += "352x288 ";
            }
            if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_TIME_LAPSE_480P)) {
                str += "720x480 ";
            }
            if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_TIME_LAPSE_720P)) {
                str += "1280x720 ";
            }
            if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_TIME_LAPSE_1080P)) {
                str += "1920x1080 ";
            }
            if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_TIME_LAPSE_2160P)) {
                str += "3840x2160 ";
            }
            if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_TIME_LAPSE_LOW)) {
                str += "lowest-quality ";
            }
            if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_TIME_LAPSE_HIGH)) {
                str += "highest-quality ";
            }
            if(CamcorderProfile.hasProfile(cameraId, 0x3F1)) {
                str += "Unknown(0x3F1) ";
            }
            if(CamcorderProfile.hasProfile(cameraId, 0x3F2)) {
                str += "Unknown(0x3F2) ";
            }
            if(CamcorderProfile.hasProfile(cameraId, 0x3F3)) {
                str += "Unknown(0x3F3) ";
            }
            if(str.isEmpty()) {
                str += "unknown";
            }
            return StringUtils.wrapUnknownLower(str).trim();
        } else {
            return "unknown";
        }
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private static String getQualityHighSpeedProfile(Camera.Parameters params, int cameraId) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            String str = "";
            if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_HIGH_SPEED_480P)) {
                str += "720x480 ";
            }
            if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_HIGH_SPEED_720P)) {
                str += "1280x720 ";
            }
            if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_HIGH_SPEED_1080P)) {
                str += "1920x1080 ";
            }
            if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_HIGH_SPEED_2160P)) {
                str += "3840x2160 ";
            }
            if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_HIGH_SPEED_LOW)) {
                str += "lowest-quality ";
            }
            if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_HIGH_SPEED_HIGH)) {
                str += "highest-quality ";
            }
            if(CamcorderProfile.hasProfile(cameraId, 0x7D6)) {
                str += "Unknown (0x7D6) ";
            }
            if(CamcorderProfile.hasProfile(cameraId, 0x7D7)) {
                str += "Unknown (0x7D7) ";
            }
            if(CamcorderProfile.hasProfile(cameraId, 0x7D8)) {
                str += "Unknown (0x7D8) ";
            }
            if(str.isEmpty()) {
                str += "unknown";
            }
            return StringUtils.wrapUnknownLower(str).trim();
        } else {
            return "unknown";
        }
    }
}
