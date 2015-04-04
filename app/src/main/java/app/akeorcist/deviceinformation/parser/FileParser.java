package app.akeorcist.deviceinformation.parser;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import app.akeorcist.deviceinformation.constants.Features;
import app.akeorcist.deviceinformation.data.file.FileManager;
import app.akeorcist.deviceinformation.model.Camera2Data;
import app.akeorcist.deviceinformation.model.CameraData;
import app.akeorcist.deviceinformation.model.FeatureData;
import app.akeorcist.deviceinformation.model.ScreenData;
import app.akeorcist.deviceinformation.model.SensorData;
import app.akeorcist.deviceinformation.model.SimpleData;
import app.akeorcist.deviceinformation.utility.AppPreferences;
import app.akeorcist.deviceinformation.utility.DevicePreferences;

/**
 * Created by Akexorcist on 3/11/15 AD.
 */
public class FileParser {

    private static final String FIELD_HARDWARE_INFO = "Hardware Info";
    private static final String FIELD_SCREEN_INFO = "Screen Info";
    private static final String FIELD_CAMERA_INFO = "Camera Info";
    private static final String FIELD_CAMERA2_INFO = "Camera 2 Info";
    private static final String FIELD_SENSOR_INFO = "Sensor Info";
    private static final String FIELD_FEATURE_INFO = "Feature Info";

    private static final String FIELD_ANDROID_INFO = "Android Info";
    private static final String FIELD_BATTERY_INFO = "Battery Info";
    private static final String FIELD_BUILD_INFO = "Build Info";
    private static final String FIELD_COMMUNICATION_INFO = "Communication Info";
    private static final String FIELD_CPU_INFO = "CPU Info";
    private static final String FIELD_GPU_INFO = "GPU Info";
    private static final String FIELD_MEMORY_INFO = "Memory Info";
    private static final String FIELD_STORAGE_INFO = "Storage Info";

    private static final String FIELD_NAME = "Name";
    private static final String FIELD_VENDOR = "Vendor";
    private static final String FIELD_TYPE = "Type";
    private static final String FIELD_VERSION = "Version";
    private static final String FIELD_POWER = "Power";
    private static final String FIELD_MAX_RANGE = "Max Range";
    private static final String FIELD_RESOLUTION = "Resolution";
    private static final String FIELD_MIN_DELAY = "Min Delay";
    private static final String FIELD_MAX_DELAY = "Max Delay";
    private static final String FIELD_FIFO_RESERVED = "FIFO Reserved";
    private static final String FIELD_FIFO_MAX = "FIFO Max";

    private static final String FIELD_RESOLUTION_PX = "Resolution (PX)";
    private static final String FIELD_RESOLUTION_DP = "Resolution (DP)";
    private static final String FIELD_DPI_X = "DPI (X)";
    private static final String FIELD_DPI_Y = "DPI (Y)";
    private static final String FIELD_DPI = "DPI";
    private static final String FIELD_SIZE = "Size";
    private static final String FIELD_DENSITY = "Density";
    private static final String FIELD_MULTITOUCH = "Multitouch";

    private static final String FIELD_ANTIBANDING = "Antibanding";
    private static final String FIELD_AUTO_EXPOSURE_LOCK = "Auto Exposure Lock Supported";
    private static final String FIELD_AUTO_WHITE_BALANCE_LOCK = "Auto White Balance Lock Supported";
    private static final String FIELD_CAMERA_FACING = "Camera Facing";
    private static final String FIELD_CAMERA_ID = "Camera ID";
    private static final String FIELD_SHUTTER_SOUND = "Can Disable Shutter Sound";
    private static final String FIELD_COLOR_EFFECT = "Color Effect";
    private static final String FIELD_FLASH_MODE = "Flash Mode";
    private static final String FIELD_FOCUS_MODE = "Focus Mode";
    private static final String FIElD_HIGH_SPEED_QUALITY = "High Speed Quality Profile (PX)";
    private static final String FIELD_IMAGE_ORIENTATION = "Image Orientation";
    private static final String FIELD_JPEG_THUMBNAIL_SIZE = "JPEG Thumbnail Size (PX)";
    private static final String FIELD_PICTURE_FORMAT = "Picture Format";
    private static final String FIELD_PICTURE_SIZE = "Picture Size (PX)";
    private static final String FIELD_PREVIEW_FPS_RANGE = "Preview FPS Range";
    private static final String FIELD_PREVIEW_FORMAT = "Preview Format";
    private static final String FIELD_PREVIEW_FRAMERATE = "Preview Framerate (FPS)";
    private static final String FIELD_PREVIEW_SIZE = "Preview Size (PX)";
    private static final String FIELD_SCENE_MODE = "Scene Mode";
    private static final String FIELD_SMOOTH_ZOOM = "Smooth Zoom Supported";
    private static final String FIELD_TIMELAPSE_QUALITY_PROFILE = "Timelapse Quality Profile (PX)";
    private static final String FIELD_VIDEO_QUALITY_PROFILE = "Video Quality Profile (PX)";
    private static final String FIELD_VIDEO_SIZE = "Video Size";
    private static final String FIELD_VIDEO_SNAPSHOT = "Video Snapshot Supported";
    private static final String FIELD_VIDEO_STABILIZATION = "Video Stabilization Supported";
    private static final String FIELD_WHITE_BALANCE = "White Balance";
    private static final String FIELD_ZOOM = "Zoom Supported";

    private static final String FIELD_AE_COMPENSATION_RANGE = "AE Compensation Range";
    private static final String FIELD_AE_COMPENSATION_STEP = "AE Compensation Step";
    private static final String FIELD_ARRAY_SIZE = "Active Array Size";
    private static final String FIELD_AVAILABLE_AE_ANTIBANDING_MODE = "Available AE Antibanding Mode";
    private static final String FIELD_AVAILABLE_AE_MODE = "Available AE Mode";
    private static final String FIELD_AVAILABLE_AF_MODE = "Available AF Mode";
    private static final String FIELD_AVAILABLE_AWB_MODE = "Available AWB Mode";
    private static final String FIELD_AVAILABLE_APERTURE = "Available Aperture";
    private static final String FIELD_AVAILABLE_AE_TARGET_FPS_RANGE = "Available Available AE Target FPS Range";
    private static final String FIELD_AVAILABLE_EDGE_MODE = "Available Edge Mode";
    private static final String FIELD_AVAILABLE_EFFECT = "Available Effect";
    private static final String FIELD_AVAILABLE_FACE_DETECT_MODE = "Available Face Detect Mode";
    private static final String FIELD_AVAILBLE_FILTER_DENSITY = "Available Filter Density";
    private static final String FIELD_AVAILABLE_FLASH = "Available Flash";
    private static final String FIELD_AVAILABLE_FOCAL_LENGTH = "Available Focal Length";
    private static final String FIELD_AVAILABLE_HOT_PIXEL_MAP_MODE = "Available Hot Pixel Map Mode";
    private static final String FIELD_AVAILABLE_HOT_PIXEL_MODE = "Available Hot Pixel Mode";
    private static final String FIELD_AVAILABLE_JPEG_THUMBNAIL_SIZE = "Available JPEG Thumbnail Size";
    private static final String FIELD_AVAILABLE_MAX_DIGITAL_ZOOM = "Available Max Digital Zoom";
    private static final String FIELD_AVAILABLE_NOISE_REDUCTION_MODE = "Available Noise Reduction Mode";
    private static final String FIELD_AVAILABLE_OPTICAL_STABILIZATION = "Available Optical Stabilization";
    private static final String FIELD_AVAILABLE_REQUEST_CAPABILITY = "Available Request Capability";
    private static final String FIELD_AVAILABLE_SCENE_MODE = "Available Scene Mode";
    private static final String FIELD_AVAILABLE_TEST_PATTERN_MODE = "Available Test Pattern Mode";
    private static final String FIELD_AVAILABLE_TONE_MAP_MODE = "Available Tone Map Mode";
    private static final String FIELD_AVAILABLE_VIDEO_STABILIZATION_MODE = "Available Video Stabilization Mode";
    private static final String FIELD_CAMERA_ID_2 = "Camera ID";
    private static final String FIELD_COLOR_CORRECTIOn_ABERRATION_MODE = "Color Correction Aberration Mode";
    private static final String FIELD_COLOR_FILTER_ARRANGEMENT = "Color Filter Arrangement";
    private static final String FIELD_EXPOSURE_TIME_RANGE = "Exposure Time Range";
    private static final String FIELD_FOCUS_DISTANCE_CALIBRATION = "Focus Distance Calibration";
    private static final String FIELD_HIGH_SPEED_VIDEO_FPS_RANGE = "High Speed Video FPS Range";
    private static final String FIELD_HIGH_SPEED_VIDEO_SIZE = "High Speed Video Size";
    private static final String FIELD_HYPERFOCAL_DISTANCE = "Hyper Focal Distance Supported";
    private static final String FIELD_LENS_FACING = "Lens Facing";
    private static final String FIELD_MAX_ANALOG_SENSITIVITY = "Max Analog Sensitivity";
    private static final String FIELD_MAX_FACE_COUNT = "Max Face Count";
    private static final String FIELD_MAX_FRAME_DURATION = "Max Frame Duration";
    private static final String FIELD_MAX_OUTPUT_PROC = "Max Number Output Proc";
    private static final String FIELD_MAX_OUTPUT_PROC_STALL = "Max Number Output Proc Stall";
    private static final String FIELD_MAX_OUTPUT_RAW = "Max Number Output RAW";
    private static final String FIELD_MAX_REGION_AE = "Max Region AE";
    private static final String FIELD_MAX_REGION_AF = "Max Region AF";
    private static final String FIELD_MAX_REGION_AWB = "Max Region AWB";
    private static final String FIELD_MINIMUM_FOCUS_DISTANCE = "Minimum Focus Distance";
    private static final String FIELD_PARTIAL_RESULT_COUNT = "Partial Result Count";
    private static final String FIELD_PHYSICAL_SIZE = "Physical Size";
    private static final String FIELD_PIPELINE_MAX_DEPTH = "Pipeline Max Depth";
    private static final String FIELD_PIXEL_ARRAY_SIZE = "Pixel Array Size";
    private static final String FIELD_REFERENCE_ILLUMINANT_1 = "Reference Illuminant 1";
    private static final String FIELD_REFERENCE_ILLUMINANT_2 = "Reference Illuminant 2";
    private static final String FIELD_SCALE_CROPPING_TYPE = "Scale Cropping Type";
    private static final String FIELD_SENSITIVITY_RANGE = "Sensitivity Range";
    private static final String FIELD_SUPPORT_SENSOR_ORIENTATION = "Sensor Orientation";
    private static final String FIELD_SUPPORT_HARDWARE_LEVEL = "Support Hardware Level";
    private static final String FIELD_SUPPORT_IMAGE_FORMAT = "Support Image Format";
    private static final String FIELD_SUPPORT_OUTPUT_SIZE = "Support Output Size";
    private static final String FIELD_SYNC_MAX_LATENCY = "Sync Max Latency";
    private static final String FIELD_TIMESTAMP_SOURCE = "Timestamp Source";
    private static final String FIELD_TONE_MAP_MAX_CURVE_POINT = "Tone Map Max Curve Point";
    private static final String FIELD_WHITE_LEVEL = "White level";

    private static final String FIELD_FEATURE_SUPPORTED = "Supported";
    private static final String FIELD_FEATURE_UNSUPPORTED = "Unsupported";

    public static ArrayList<SimpleData> getAndroidData(Context context) {
        ArrayList<SimpleData> arrAndroidData = new ArrayList<>();
        String strCurrentFile = DevicePreferences.getCurrentDevice(context);
        String strDeviceJson = FileManager.readInternalFile(context, strCurrentFile);
        try {
            JSONObject deviceJsonObject = new JSONObject(strDeviceJson);
            JSONObject hardwareJsonObject = deviceJsonObject.optJSONObject(FIELD_HARDWARE_INFO);
            JSONObject androidJsonObject = hardwareJsonObject.optJSONObject(FIELD_ANDROID_INFO);
            Iterator<String> iterator = androidJsonObject.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = androidJsonObject.optString(key);
                arrAndroidData.add(new SimpleData(key, value));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrAndroidData;
    }

    public static ArrayList<SimpleData> getBatteryData(Context context) {
        ArrayList<SimpleData> arrBatteryData = new ArrayList<>();
        String strCurrentFile = DevicePreferences.getCurrentDevice(context);
        String strDeviceJson = FileManager.readInternalFile(context, strCurrentFile);
        try {
            JSONObject deviceJsonObject = new JSONObject(strDeviceJson);
            JSONObject hardwareJsonObject = deviceJsonObject.optJSONObject(FIELD_HARDWARE_INFO);
            JSONObject batteryJsonObject = hardwareJsonObject.optJSONObject(FIELD_BATTERY_INFO);
            if(batteryJsonObject != null) {
                Iterator<String> iterator = batteryJsonObject.keys();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    String value = batteryJsonObject.optString(key);
                    arrBatteryData.add(new SimpleData(key, value));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrBatteryData;
    }

    public static ArrayList<SimpleData> getBuildData(Context context) {
        ArrayList<SimpleData> arrAndroidData = new ArrayList<>();
        String strCurrentFile = DevicePreferences.getCurrentDevice(context);
        String strDeviceJson = FileManager.readInternalFile(context, strCurrentFile);
        try {
            JSONObject deviceJsonObject = new JSONObject(strDeviceJson);
            JSONObject hardwareJsonObject = deviceJsonObject.optJSONObject(FIELD_HARDWARE_INFO);
            JSONObject buildJsonObject = hardwareJsonObject.optJSONObject(FIELD_BUILD_INFO);
            Iterator<String> iterator = buildJsonObject.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = buildJsonObject.optString(key);
                arrAndroidData.add(new SimpleData(key, value));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrAndroidData;
    }

    public static ArrayList<SimpleData> getCommunicationData(Context context) {
        ArrayList<SimpleData> arrAndroidData = new ArrayList<>();
        String strCurrentFile = DevicePreferences.getCurrentDevice(context);
        String strDeviceJson = FileManager.readInternalFile(context, strCurrentFile);
        try {
            JSONObject deviceJsonObject = new JSONObject(strDeviceJson);
            JSONObject hardwareJsonObject = deviceJsonObject.optJSONObject(FIELD_HARDWARE_INFO);
            JSONObject communicationJsonObject = hardwareJsonObject.optJSONObject(FIELD_COMMUNICATION_INFO);
            Iterator<String> iterator = communicationJsonObject.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = communicationJsonObject.optString(key);
                arrAndroidData.add(new SimpleData(key, value));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrAndroidData;
    }

    public static ArrayList<SimpleData> getCpuData(Context context) {
        ArrayList<SimpleData> arrAndroidData = new ArrayList<>();
        String strCurrentFile = DevicePreferences.getCurrentDevice(context);
        String strDeviceJson = FileManager.readInternalFile(context, strCurrentFile);
        try {
            JSONObject deviceJsonObject = new JSONObject(strDeviceJson);
            JSONObject hardwareJsonObject = deviceJsonObject.optJSONObject(FIELD_HARDWARE_INFO);
            JSONObject cpuJsonObject = hardwareJsonObject.optJSONObject(FIELD_CPU_INFO);
            Iterator<String> iterator = cpuJsonObject.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = cpuJsonObject.optString(key);
                arrAndroidData.add(new SimpleData(key, value));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrAndroidData;
    }

    public static ArrayList<SimpleData> getGpuData(Context context) {
        ArrayList<SimpleData> arrAndroidData = new ArrayList<>();
        String strCurrentFile = DevicePreferences.getCurrentDevice(context);
        String strDeviceJson = FileManager.readInternalFile(context, strCurrentFile);
        try {
            JSONObject deviceJsonObject = new JSONObject(strDeviceJson);
            JSONObject hardwareJsonObject = deviceJsonObject.optJSONObject(FIELD_HARDWARE_INFO);
            JSONObject gpuJsonObject = hardwareJsonObject.optJSONObject(FIELD_GPU_INFO);
            Iterator<String> iterator = gpuJsonObject.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = gpuJsonObject.optString(key);
                arrAndroidData.add(new SimpleData(key, value));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrAndroidData;
    }

    public static ArrayList<SimpleData> getMemoryData(Context context) {
        ArrayList<SimpleData> arrAndroidData = new ArrayList<>();
        String strCurrentFile = DevicePreferences.getCurrentDevice(context);
        String strDeviceJson = FileManager.readInternalFile(context, strCurrentFile);
        try {
            JSONObject deviceJsonObject = new JSONObject(strDeviceJson);
            JSONObject hardwareJsonObject = deviceJsonObject.optJSONObject(FIELD_HARDWARE_INFO);
            JSONObject memoryJsonObject = hardwareJsonObject.optJSONObject(FIELD_MEMORY_INFO);
            Iterator<String> iterator = memoryJsonObject.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = memoryJsonObject.optString(key);
                arrAndroidData.add(new SimpleData(key, value));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrAndroidData;
    }

    public static ArrayList<SimpleData> getStorageData(Context context) {
        ArrayList<SimpleData> arrAndroidData = new ArrayList<>();
        String strCurrentFile = DevicePreferences.getCurrentDevice(context);
        String strDeviceJson = FileManager.readInternalFile(context, strCurrentFile);
        try {
            JSONObject deviceJsonObject = new JSONObject(strDeviceJson);
            JSONObject hardwareJsonObject = deviceJsonObject.optJSONObject(FIELD_HARDWARE_INFO);
            JSONObject storageJsonObject = hardwareJsonObject.optJSONObject(FIELD_STORAGE_INFO);
            Iterator<String> iterator = storageJsonObject.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = storageJsonObject.optString(key);
                arrAndroidData.add(new SimpleData(key, value));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrAndroidData;
    }

    public static ArrayList<SensorData> getSensorData(Context context) {
        String strCurrentFile = DevicePreferences.getCurrentDevice(context);
        String strDeviceJson = FileManager.readInternalFile(context, strCurrentFile);
        try {
            ArrayList<SensorData> sensorDatas = new ArrayList<>();
            JSONObject deviceJsonObject = new JSONObject(strDeviceJson);
            JSONArray sensorJsonArray = deviceJsonObject.optJSONArray(FIELD_SENSOR_INFO);
            if(sensorJsonArray != null) {
                for(int i = 0 ; i < sensorJsonArray.length() ; i++) {
                    JSONObject sensorJsonObject = sensorJsonArray.getJSONObject(i);
                    SensorData sensorData = new SensorData(sensorJsonObject.getString(FIELD_NAME));
                    sensorData.setVendor(sensorJsonObject.getString(FIELD_VENDOR));
                    sensorData.setType(sensorJsonObject.getString(FIELD_TYPE));
                    sensorData.setVersion(sensorJsonObject.getString(FIELD_VERSION));
                    sensorData.setPower(sensorJsonObject.getString(FIELD_POWER));
                    sensorData.setMaxRange(sensorJsonObject.getString(FIELD_MAX_RANGE));
                    sensorData.setResolution(sensorJsonObject.getString(FIELD_RESOLUTION));
                    sensorData.setMinDelay(sensorJsonObject.getString(FIELD_MIN_DELAY));
                    sensorData.setMaxDelay(sensorJsonObject.getString(FIELD_MAX_DELAY));
                    sensorData.setFifoReserved(sensorJsonObject.getString(FIELD_FIFO_RESERVED));
                    sensorData.setFifoMax(sensorJsonObject.getString(FIELD_FIFO_MAX));
                    sensorDatas.add(sensorData);
                }
                return sensorDatas;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static SensorData getSensorData(Context context, int position) {
        String strCurrentFile = DevicePreferences.getCurrentDevice(context);
        String strDeviceJson = FileManager.readInternalFile(context, strCurrentFile);
        try {
            JSONObject deviceJsonObject = new JSONObject(strDeviceJson);
            JSONArray sensorJsonArray = deviceJsonObject.optJSONArray(FIELD_SENSOR_INFO);

            JSONObject sensorJsonObject = sensorJsonArray.getJSONObject(position);
            SensorData sensorData = new SensorData(sensorJsonObject.getString(FIELD_NAME));
            sensorData.setVendor(sensorJsonObject.getString(FIELD_VENDOR));
            sensorData.setType(sensorJsonObject.getString(FIELD_TYPE));
            sensorData.setVersion(sensorJsonObject.getString(FIELD_VERSION));
            sensorData.setPower(sensorJsonObject.getString(FIELD_POWER));
            sensorData.setMaxRange(sensorJsonObject.getString(FIELD_MAX_RANGE));
            sensorData.setResolution(sensorJsonObject.getString(FIELD_RESOLUTION));
            sensorData.setMinDelay(sensorJsonObject.getString(FIELD_MIN_DELAY));
            sensorData.setMaxDelay(sensorJsonObject.getString(FIELD_MAX_DELAY));
            sensorData.setFifoReserved(sensorJsonObject.getString(FIELD_FIFO_RESERVED));
            sensorData.setFifoMax(sensorJsonObject.getString(FIELD_FIFO_MAX));
            return sensorData;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getSensorDataCount(Context context) {
        String strCurrentFile = DevicePreferences.getCurrentDevice(context);
        String strDeviceJson = FileManager.readInternalFile(context, strCurrentFile);
        try {
            JSONObject deviceJsonObject = new JSONObject(strDeviceJson);
            JSONArray sensorJsonArray = deviceJsonObject.optJSONArray(FIELD_SENSOR_INFO);
            return sensorJsonArray.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static ScreenData getScreenData(Context context) {
        String strCurrentFile = DevicePreferences.getCurrentDevice(context);
        String strDeviceJson = FileManager.readInternalFile(context, strCurrentFile);
        try {
            JSONObject deviceJsonObject = new JSONObject(strDeviceJson);
            JSONObject screenJsonObject = deviceJsonObject.getJSONObject(FIELD_SCREEN_INFO);
            ScreenData screenData = new ScreenData();
            screenData.setResolutionPx(screenJsonObject.getString(FIELD_RESOLUTION_PX));
            screenData.setResolutionDp(screenJsonObject.getString(FIELD_RESOLUTION_DP));
            screenData.setDpiX(screenJsonObject.getString(FIELD_DPI_X));
            screenData.setDpiY(screenJsonObject.getString(FIELD_DPI_Y));
            screenData.setDpi(screenJsonObject.getString(FIELD_DPI));
            screenData.setSize(screenJsonObject.getString(FIELD_SIZE));
            screenData.setDensity(screenJsonObject.getString(FIELD_DENSITY));
            screenData.setMultitouch(screenJsonObject.getString(FIELD_MULTITOUCH));
            return screenData;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static CameraData getCameraData(Context context, int position) {
        String strCurrentFile = DevicePreferences.getCurrentDevice(context);
        String strDeviceJson = FileManager.readInternalFile(context, strCurrentFile);
        try {
            JSONObject deviceJsonObject = new JSONObject(strDeviceJson);
            JSONArray cameraJsonArray = deviceJsonObject.optJSONArray(FIELD_CAMERA_INFO);
            JSONObject cameraJsonObject = cameraJsonArray.getJSONObject(position);
            CameraData cameraData = new CameraData();
            cameraData.setAntibanding(cameraJsonObject.getString(FIELD_ANTIBANDING));
            cameraData.setAutoExposure(cameraJsonObject.getString(FIELD_AUTO_EXPOSURE_LOCK));
            cameraData.setAutoWhiteBalance(cameraJsonObject.getString(FIELD_AUTO_WHITE_BALANCE_LOCK));
            cameraData.setFacing(cameraJsonObject.getString(FIELD_CAMERA_FACING));
            cameraData.setCameraId(cameraJsonObject.getString(FIELD_CAMERA_ID));
            cameraData.setShutterSound(cameraJsonObject.getString(FIELD_SHUTTER_SOUND));
            cameraData.setColorEffect(cameraJsonObject.getString(FIELD_COLOR_EFFECT));
            cameraData.setFlashMode(cameraJsonObject.getString(FIELD_FLASH_MODE));
            cameraData.setFocusMode(cameraJsonObject.getString(FIELD_FOCUS_MODE));
            cameraData.setHighSpeedQualityProfile(cameraJsonObject.getString(FIElD_HIGH_SPEED_QUALITY));
            cameraData.setImageOrientation(cameraJsonObject.getString(FIELD_IMAGE_ORIENTATION));
            cameraData.setJpegThumbnailSize(cameraJsonObject.getString(FIELD_JPEG_THUMBNAIL_SIZE));
            cameraData.setPictureFormat(cameraJsonObject.getString(FIELD_PICTURE_FORMAT));
            cameraData.setPictureSize(cameraJsonObject.getString(FIELD_PICTURE_SIZE));
            cameraData.setPreviewFpsRange(cameraJsonObject.getString(FIELD_PREVIEW_FPS_RANGE));
            cameraData.setPreviewFormat(cameraJsonObject.getString(FIELD_PREVIEW_FORMAT));
            cameraData.setPreviewFramerate(cameraJsonObject.getString(FIELD_PREVIEW_FRAMERATE));
            cameraData.setPreviewSize(cameraJsonObject.getString(FIELD_PREVIEW_SIZE));
            cameraData.setSceneMode(cameraJsonObject.getString(FIELD_SCENE_MODE));
            cameraData.setSmoothZoom(cameraJsonObject.getString(FIELD_SMOOTH_ZOOM));
            cameraData.setTimelapseQualityProfile(cameraJsonObject.getString(FIELD_TIMELAPSE_QUALITY_PROFILE));
            cameraData.setVideoQualityProfile(cameraJsonObject.getString(FIELD_VIDEO_QUALITY_PROFILE));
            cameraData.setVideoSize(cameraJsonObject.getString(FIELD_VIDEO_SIZE));
            cameraData.setVideoSnapshot(cameraJsonObject.getString(FIELD_VIDEO_SNAPSHOT));
            cameraData.setVideoStabilization(cameraJsonObject.getString(FIELD_VIDEO_STABILIZATION));
            cameraData.setWhiteBalance(cameraJsonObject.getString(FIELD_WHITE_BALANCE));
            cameraData.setZoom(cameraJsonObject.getString(FIELD_ZOOM));
            return cameraData;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getCameraDataCount(Context context) {
        String strCurrentFile = DevicePreferences.getCurrentDevice(context);
        String strDeviceJson = FileManager.readInternalFile(context, strCurrentFile);
        try {
            JSONObject deviceJsonObject = new JSONObject(strDeviceJson);
            JSONArray cameraJsonArray = deviceJsonObject.optJSONArray(FIELD_CAMERA_INFO);
            return cameraJsonArray.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getCameraId(Context context, int position) {
        String strCurrentFile = DevicePreferences.getCurrentDevice(context);
        String strDeviceJson = FileManager.readInternalFile(context, strCurrentFile);
        try {
            JSONObject deviceJsonObject = new JSONObject(strDeviceJson);
            JSONArray cameraJsonArray = deviceJsonObject.optJSONArray(FIELD_CAMERA_INFO);
            JSONObject cameraJsonObject = cameraJsonArray.getJSONObject(position);
            return cameraJsonObject.getString(FIELD_CAMERA_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "unknown";
    }


    public static Camera2Data getCamera2Data(Context context, int position) {
        String strCurrentFile = DevicePreferences.getCurrentDevice(context);
        String strDeviceJson = FileManager.readInternalFile(context, strCurrentFile);
        try {
            JSONObject deviceJsonObject = new JSONObject(strDeviceJson);
            JSONArray cameraJsonArray = deviceJsonObject.optJSONArray(FIELD_CAMERA2_INFO);
            JSONObject cameraJsonObject = cameraJsonArray.getJSONObject(position);
            Camera2Data cameraData = new Camera2Data();
            cameraData.setActiveArraySize(cameraJsonObject.getString(FIELD_ARRAY_SIZE));
            cameraData.setAECompensationRange(cameraJsonObject.getString(FIELD_AE_COMPENSATION_RANGE));
            cameraData.setAECompensationStep(cameraJsonObject.getString(FIELD_AE_COMPENSATION_STEP));
            cameraData.setAvailableAEAntibandingMode(cameraJsonObject.getString(FIELD_AVAILABLE_AE_ANTIBANDING_MODE));
            cameraData.setAvailableAEMode(cameraJsonObject.getString(FIELD_AVAILABLE_AE_MODE));
            cameraData.setAvailableAETargetFpsRange(cameraJsonObject.getString(FIELD_AVAILABLE_AE_TARGET_FPS_RANGE));
            cameraData.setAvailableAperture(cameraJsonObject.getString(FIELD_AVAILABLE_APERTURE));
            cameraData.setAvailableAFMode(cameraJsonObject.getString(FIELD_AVAILABLE_AF_MODE));
            cameraData.setAvailableAWBMode(cameraJsonObject.getString(FIELD_AVAILABLE_AWB_MODE));
            cameraData.setAvailableEdgeMode(cameraJsonObject.getString(FIELD_AVAILABLE_EDGE_MODE));
            cameraData.setAvailableEffect(cameraJsonObject.getString(FIELD_AVAILABLE_EFFECT));
            cameraData.setAvailableAEAntibandingMode(cameraJsonObject.getString(FIELD_AVAILABLE_AE_ANTIBANDING_MODE));
            cameraData.setAvailableFaceDetectMode(cameraJsonObject.getString(FIELD_AVAILABLE_FACE_DETECT_MODE));
            cameraData.setAvailableFilterDensity(cameraJsonObject.getString(FIELD_AVAILBLE_FILTER_DENSITY));
            cameraData.setAvailableFlash(cameraJsonObject.getString(FIELD_AVAILABLE_FLASH));
            cameraData.setAvailableFocalLength(cameraJsonObject.getString(FIELD_AVAILABLE_FOCAL_LENGTH));
            cameraData.setAvailableHotPixelMapMode(cameraJsonObject.getString(FIELD_AVAILABLE_HOT_PIXEL_MAP_MODE));
            cameraData.setAvailableHotPixelMode(cameraJsonObject.getString(FIELD_AVAILABLE_HOT_PIXEL_MODE));
            cameraData.setAvailableJpegThumbnailSize(cameraJsonObject.getString(FIELD_AVAILABLE_JPEG_THUMBNAIL_SIZE));
            cameraData.setAvailableMaxDigitalZoom(cameraJsonObject.getString(FIELD_AVAILABLE_MAX_DIGITAL_ZOOM));
            cameraData.setAvailableNoiseReductionMode(cameraJsonObject.getString(FIELD_AVAILABLE_NOISE_REDUCTION_MODE));
            cameraData.setAvailableOpticalStabilization(cameraJsonObject.getString(FIELD_AVAILABLE_OPTICAL_STABILIZATION));
            cameraData.setAvailableRequestCapability(cameraJsonObject.getString(FIELD_AVAILABLE_REQUEST_CAPABILITY));
            cameraData.setAvailableSceneMode(cameraJsonObject.getString(FIELD_AVAILABLE_SCENE_MODE));
            cameraData.setAvailableTestPartternMode(cameraJsonObject.getString(FIELD_AVAILABLE_TEST_PATTERN_MODE));
            cameraData.setAvailableToneMapMode(cameraJsonObject.getString(FIELD_AVAILABLE_TONE_MAP_MODE));
            cameraData.setAvailableVideoStabilizationMode(cameraJsonObject.getString(FIELD_AVAILABLE_VIDEO_STABILIZATION_MODE));
            cameraData.setCameraId(cameraJsonObject.getString(FIELD_CAMERA_ID_2));
            cameraData.setColorCorrectionAberrationMode(cameraJsonObject.getString(FIELD_COLOR_CORRECTIOn_ABERRATION_MODE));
            cameraData.setColorFilterArrangement(cameraJsonObject.getString(FIELD_COLOR_FILTER_ARRANGEMENT));
            cameraData.setExposureTimeRange(cameraJsonObject.getString(FIELD_EXPOSURE_TIME_RANGE));
            cameraData.setFocusDistanceCalibration(cameraJsonObject.getString(FIELD_FOCUS_DISTANCE_CALIBRATION));
            cameraData.setHighSpeedVideoFpsRange(cameraJsonObject.getString(FIELD_HIGH_SPEED_VIDEO_FPS_RANGE));
            cameraData.setHighSpeedVideoSize(cameraJsonObject.getString(FIELD_HIGH_SPEED_VIDEO_SIZE));
            cameraData.setHyperfocalDistance(cameraJsonObject.getString(FIELD_HYPERFOCAL_DISTANCE));
            cameraData.setLensFacing(cameraJsonObject.getString(FIELD_LENS_FACING));
            cameraData.setMaxAnalogSensitivity(cameraJsonObject.getString(FIELD_MAX_ANALOG_SENSITIVITY));
            cameraData.setMaxFaceCount(cameraJsonObject.getString(FIELD_MAX_FACE_COUNT));
            cameraData.setMaxFrameDutaion(cameraJsonObject.getString(FIELD_MAX_FRAME_DURATION));
            cameraData.setMaxNumOutputProc(cameraJsonObject.getString(FIELD_MAX_OUTPUT_PROC));
            cameraData.setMaxNumOutputProcStall(cameraJsonObject.getString(FIELD_MAX_OUTPUT_PROC_STALL));
            cameraData.setMaxNumOutputRaw(cameraJsonObject.getString(FIELD_MAX_OUTPUT_RAW));
            cameraData.setMaxRegionAE(cameraJsonObject.getString(FIELD_MAX_REGION_AE));
            cameraData.setMaxRegionAF(cameraJsonObject.getString(FIELD_MAX_REGION_AF));
            cameraData.setMaxRegionAWB(cameraJsonObject.getString(FIELD_MAX_REGION_AWB));
            cameraData.setMinimumFocusDistance(cameraJsonObject.getString(FIELD_MINIMUM_FOCUS_DISTANCE));
            cameraData.setPartialResultCount(cameraJsonObject.getString(FIELD_PARTIAL_RESULT_COUNT));
            cameraData.setPhysicalSize(cameraJsonObject.getString(FIELD_PHYSICAL_SIZE));
            cameraData.setPipelineMaxDepth(cameraJsonObject.getString(FIELD_PIPELINE_MAX_DEPTH));
            cameraData.setPixelArraySize(cameraJsonObject.getString(FIELD_PIXEL_ARRAY_SIZE));
            cameraData.setReferenceIlluminant1(cameraJsonObject.getString(FIELD_REFERENCE_ILLUMINANT_1));
            cameraData.setReferenceIlluminant2(cameraJsonObject.getString(FIELD_REFERENCE_ILLUMINANT_2));
            cameraData.setScaleCroppingType(cameraJsonObject.getString(FIELD_SCALE_CROPPING_TYPE));
            cameraData.setSensitivityRange(cameraJsonObject.getString(FIELD_SENSITIVITY_RANGE));
            cameraData.setSensorOrientation(cameraJsonObject.getString(FIELD_SUPPORT_SENSOR_ORIENTATION));
            cameraData.setSupportHardwareLevel(cameraJsonObject.getString(FIELD_SUPPORT_HARDWARE_LEVEL));
            cameraData.setSupportImageFormat(cameraJsonObject.getString(FIELD_SUPPORT_IMAGE_FORMAT));
            cameraData.setSupportOutputSize(cameraJsonObject.getString(FIELD_SUPPORT_OUTPUT_SIZE));
            cameraData.setSyncMaxLatency(cameraJsonObject.getString(FIELD_SYNC_MAX_LATENCY));
            cameraData.setTimestampSource(cameraJsonObject.getString(FIELD_TIMESTAMP_SOURCE));
            cameraData.setToneMapMaxCurvePoint(cameraJsonObject.getString(FIELD_TONE_MAP_MAX_CURVE_POINT));
            cameraData.setWhiteLevel(cameraJsonObject.getString(FIELD_WHITE_LEVEL));
            return cameraData;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getCamera2DataCount(Context context) {
        String strCurrentFile = DevicePreferences.getCurrentDevice(context);
        String strDeviceJson = FileManager.readInternalFile(context, strCurrentFile);
        try {
            JSONObject deviceJsonObject = new JSONObject(strDeviceJson);
            JSONArray cameraJsonArray = deviceJsonObject.optJSONArray(FIELD_CAMERA2_INFO);
            return cameraJsonArray.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getCamera2Id(Context context, int position) {
        String strCurrentFile = DevicePreferences.getCurrentDevice(context);
        String strDeviceJson = FileManager.readInternalFile(context, strCurrentFile);
        try {
            JSONObject deviceJsonObject = new JSONObject(strDeviceJson);
            JSONArray cameraJsonArray = deviceJsonObject.optJSONArray(FIELD_CAMERA2_INFO);
            JSONObject cameraJsonObject = cameraJsonArray.getJSONObject(position);
            return cameraJsonObject.getString(FIELD_CAMERA_ID_2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "unknown";
    }

    public static FeatureData getSupportedFeatureData(Context context, int position) {
        String strCurrentFile = DevicePreferences.getCurrentDevice(context);
        String strDeviceJson = FileManager.readInternalFile(context, strCurrentFile);
        try {
            JSONObject deviceJsonObject = new JSONObject(strDeviceJson);
            JSONObject featureJsonObject = deviceJsonObject.getJSONObject(FIELD_FEATURE_INFO);
            JSONArray supportedJsonArray = featureJsonObject.optJSONArray(FIELD_FEATURE_SUPPORTED);
            String name = supportedJsonArray.getString(position);
            return Features.getFeatureData(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getSupportedFeatureDataCount(Context context) {
        String strCurrentFile = DevicePreferences.getCurrentDevice(context);
        String strDeviceJson = FileManager.readInternalFile(context, strCurrentFile);
        try {
            JSONObject deviceJsonObject = new JSONObject(strDeviceJson);
            JSONObject featureJsonObject = deviceJsonObject.getJSONObject(FIELD_FEATURE_INFO);
            JSONArray supportedJsonArray = featureJsonObject.optJSONArray(FIELD_FEATURE_SUPPORTED);
            return supportedJsonArray.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static FeatureData getUnsupportedFeatureData(Context context, int position) {
        String strCurrentFile = DevicePreferences.getCurrentDevice(context);
        String strDeviceJson = FileManager.readInternalFile(context, strCurrentFile);
        try {
            JSONObject deviceJsonObject = new JSONObject(strDeviceJson);
            JSONObject featureJsonObject = deviceJsonObject.getJSONObject(FIELD_FEATURE_INFO);
            JSONArray unsupportedJsonArray = featureJsonObject.optJSONArray(FIELD_FEATURE_UNSUPPORTED);
            String name = unsupportedJsonArray.getString(position);
            return Features.getFeatureData(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getUnsupportedFeatureDataCount(Context context) {
        String strCurrentFile = DevicePreferences.getCurrentDevice(context);
        String strDeviceJson = FileManager.readInternalFile(context, strCurrentFile);
        try {
            JSONObject deviceJsonObject = new JSONObject(strDeviceJson);
            JSONObject featureJsonObject = deviceJsonObject.getJSONObject(FIELD_FEATURE_INFO);
            JSONArray unsupportedJsonArray = featureJsonObject.optJSONArray(FIELD_FEATURE_UNSUPPORTED);
            return unsupportedJsonArray.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getFingerprint(Context context) {
        String strCurrentFile = DevicePreferences.getCurrentDevice(context);
        String strDeviceJson = FileManager.readInternalFile(context, strCurrentFile);
        try {
            JSONObject deviceJsonObject = new JSONObject(strDeviceJson);
            JSONObject hardwareJsonObject = deviceJsonObject.optJSONObject(FIELD_HARDWARE_INFO);
            JSONObject buildJsonObject = hardwareJsonObject.optJSONObject(FIELD_BUILD_INFO);
            Iterator<String> iterator = buildJsonObject.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                if("Fingerprint".equals(key))
                    return buildJsonObject.optString(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
