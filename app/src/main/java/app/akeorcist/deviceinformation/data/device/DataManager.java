package app.akeorcist.deviceinformation.data.device;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import app.akeorcist.deviceinformation.model.AppData;
import app.akeorcist.deviceinformation.model.Camera2Data;
import app.akeorcist.deviceinformation.model.CameraData;
import app.akeorcist.deviceinformation.model.FeatureData;
import app.akeorcist.deviceinformation.model.ScreenData;
import app.akeorcist.deviceinformation.model.SensorData;
import app.akeorcist.deviceinformation.model.SimpleData;
import app.akeorcist.deviceinformation.parser.FileParser;
import app.akeorcist.deviceinformation.utility.AppPreferences;
import app.akeorcist.deviceinformation.utility.DevicePreferences;

/**
 * Created by Akexorcist on 3/6/15 AD.
 */
public class DataManager {
    public static void buildDeviceSpecific(Context context) {
        DevicePreferences.setDeviceBrand(context, Build.BRAND);
        DevicePreferences.setDeviceModel(context, Build.MODEL);
        DevicePreferences.setDeviceVersion(context, Build.VERSION.RELEASE);
        DevicePreferences.setDeviceFingerprint(context, Build.FINGERPRINT);
    }

    public static String createDataJson() {
        try {
            // Hardware Info -- Android Info
            JSONObject androidObject = new JSONObject();
            ArrayList<SimpleData> androidData = HardwareManager.getAndroidDataList();
            for(SimpleData data : androidData) {
                androidObject.put(data.getTitle(), data.getDetail());
            }

            // Hardware Info -- Battery Info
            JSONObject batteryObject = new JSONObject();
            ArrayList<SimpleData> batteryData = HardwareManager.getBatteryDataList();
            for(SimpleData data : batteryData) {
                batteryObject.put(data.getTitle(), data.getDetail());
            }

            // Hardware Info -- Build Info
            JSONObject buildObject = new JSONObject();
            ArrayList<SimpleData> buildData = HardwareManager.getBuildDataList();
            for(SimpleData data : buildData) {
                buildObject.put(data.getTitle(), data.getDetail());
            }

            // Hardware Info -- Communication Info
            JSONObject commObject = new JSONObject();
            ArrayList<SimpleData> commData = HardwareManager.getCommunicationDataList();
            for(SimpleData data : commData) {
                commObject.put(data.getTitle(), data.getDetail());
            }

            // Hardware Info -- CPU Info
            JSONObject cpuObject = new JSONObject();
            ArrayList<SimpleData> cpuData = HardwareManager.getCpuDataList();
            for(SimpleData data : cpuData) {
                cpuObject.put(data.getTitle(), data.getDetail());
            }

            // Hardware Info -- GPU Info
            JSONObject gpuObject = new JSONObject();
            ArrayList<SimpleData> gpuData = HardwareManager.getGpuDataList();
            for(SimpleData data : gpuData) {
                gpuObject.put(data.getTitle(), data.getDetail());
            }

            // Hardware Info -- GPU Info
            JSONObject memoryObject = new JSONObject();
            ArrayList<SimpleData> memoryData = HardwareManager.getMemoryDataList();
            for(SimpleData data : memoryData) {
                memoryObject.put(data.getTitle(), data.getDetail());
            }

            // Hardware Info -- Storage Info
            JSONObject storageObject = new JSONObject();
            ArrayList<SimpleData> storageData = HardwareManager.getStorageDataList();
            for(SimpleData data : storageData) {
                storageObject.put(data.getTitle(), data.getDetail());
            }

            // Hardware Info
            JSONObject hwswObject = new JSONObject();
            hwswObject.put("Android Info", androidObject);
            hwswObject.put("Battery Info", batteryObject);
            hwswObject.put("Build Info", buildObject);
            hwswObject.put("Communication Info", commObject);
            hwswObject.put("CPU Info", cpuObject);
            hwswObject.put("GPU Info", gpuObject);
            hwswObject.put("Memory Info", memoryObject);
            hwswObject.put("Storage Info", storageObject);

            // Sensor Info
            JSONArray sensorArray = new JSONArray();
            int sensorCount = SensorListManager.getSensorDataCount();
            for(int i = 0 ; i < sensorCount ; i++) {
                SensorData sensorData = SensorListManager.getSensorData(i);
                JSONObject sensorDataObject = new JSONObject();
                sensorDataObject.put("Name", sensorData.getName());
                sensorDataObject.put("Vendor", sensorData.getVendor());
                sensorDataObject.put("Type", sensorData.getType());
                sensorDataObject.put("Version", sensorData.getVersion());
                sensorDataObject.put("Power", sensorData.getPower());
                sensorDataObject.put("Max Range", sensorData.getMaxRange());
                sensorDataObject.put("Resolution", sensorData.getResolution());
                sensorDataObject.put("Min Delay", sensorData.getMinDelay());
                sensorDataObject.put("Max Delay", sensorData.getMaxDelay());
                sensorDataObject.put("FIFO Reserved", sensorData.getFifoReserved());
                sensorDataObject.put("FIFO Max", sensorData.getFifoMax());
                sensorArray.put(sensorDataObject);
            }


            // Screen Info
            ScreenData screenData = ScreenManager.getScreenData();
            JSONObject screenObject = new JSONObject();
            screenObject.put("Resolution (PX)", screenData.getResolutionPx());
            screenObject.put("Resolution (DP)", screenData.getResolutionDp());
            screenObject.put("DPI (X)", screenData.getDpiX());
            screenObject.put("DPI (Y)", screenData.getDpiY());
            screenObject.put("DPI", screenData.getDpi());
            screenObject.put("Size", screenData.getSize());
            screenObject.put("Density", screenData.getDensity());
            screenObject.put("Multitouch", screenData.getMultitouch());


            // Camera Info
            JSONArray cameraArray = new JSONArray();
            int cameraCount = CameraManager.getCameraCount();
            for(int i = 0 ; i < cameraCount ; i++) {
                CameraData cameraData = CameraManager.getCameraData(i);
                JSONObject cameraDataObject = new JSONObject();
                cameraDataObject.put("Camera ID", cameraData.getCameraId());
                cameraDataObject.put("Antibanding", cameraData.getAntibanding());
                cameraDataObject.put("Camera Facing", cameraData.getFacing());
                cameraDataObject.put("Color Effect", cameraData.getColorEffect());
                cameraDataObject.put("Can Disable Shutter Sound", cameraData.getShutterSound());
                cameraDataObject.put("Flash Mode", cameraData.getFlashMode());
                cameraDataObject.put("Focus Mode", cameraData.getFocusMode());
                cameraDataObject.put("JPEG Thumbnail Size (PX)", cameraData.getJpegThumbnailSize());
                cameraDataObject.put("Image Orientation", cameraData.getImageOrientation());
                cameraDataObject.put("Picture Format", cameraData.getPictureFormat());
                cameraDataObject.put("Preview Format", cameraData.getPreviewFormat());
                cameraDataObject.put("Picture Size (PX)", cameraData.getPictureSize());
                cameraDataObject.put("Preview Framerate (FPS)", cameraData.getPreviewFramerate());
                cameraDataObject.put("Preview Size (PX)", cameraData.getPreviewSize());
                cameraDataObject.put("Preview FPS Range", cameraData.getPreviewFpsRange());
                cameraDataObject.put("Scene Mode", cameraData.getSceneMode());
                cameraDataObject.put("Video Quality Profile (PX)", cameraData.getVideoQualityProfile());
                cameraDataObject.put("Timelapse Quality Profile (PX)", cameraData.getTimelapseQualityProfile());
                cameraDataObject.put("High Speed Quality Profile (PX)", cameraData.getHighSpeedQualityProfile());
                cameraDataObject.put("Video Size", cameraData.getVideoSize());
                cameraDataObject.put("White Balance", cameraData.getWhiteBalance());
                cameraDataObject.put("Auto Exposure Lock Supported", cameraData.getAutoExposure());
                cameraDataObject.put("Auto White Balance Lock Supported", cameraData.getAutoExposure());
                cameraDataObject.put("Smooth Zoom Supported", cameraData.getSmoothZoom());
                cameraDataObject.put("Video Snapshot Supported", cameraData.getVideoSnapshot());
                cameraDataObject.put("Video Stabilization Supported", cameraData.getVideoStabilization());
                cameraDataObject.put("Zoom Supported", cameraData.getZoom());
                cameraArray.put(cameraDataObject);
            }


            // Camera Info
            JSONArray camera2Array = new JSONArray();
            int camera2Count = Camera2Manager.getCameraCount();
            for(int i = 0 ; i < camera2Count ; i++) {
                Camera2Data camera2Data = Camera2Manager.getCameraData(i);
                JSONObject camera2DataObject = new JSONObject();
                camera2DataObject.put("Camera ID", camera2Data.getCameraId());
                camera2DataObject.put("Active Array Size", camera2Data.getActiveArraySize());
                camera2DataObject.put("AE Compensation Range", camera2Data.getAECompensationRange());
                camera2DataObject.put("AE Compensation Step", camera2Data.getAECompensationStep());
                camera2DataObject.put("Available AE Antibanding Mode", camera2Data.getAvailableAEAntibandingMode());
                camera2DataObject.put("Available AE Mode", camera2Data.getAvailableAEMode());
                camera2DataObject.put("Available AF Mode", camera2Data.getAvailableAFMode());
                camera2DataObject.put("Available Available AE Target FPS Range", camera2Data.getAvailableAETargetFpsRange());
                camera2DataObject.put("Available Aperture", camera2Data.getAvailableAperture());
                camera2DataObject.put("Available AWB Mode", camera2Data.getAvailableAWBMode());
                camera2DataObject.put("Available Edge Mode", camera2Data.getAvailableEdgeMode());
                camera2DataObject.put("Available Effect", camera2Data.getAvailableEffect());
                camera2DataObject.put("Available Face Detect Mode", camera2Data.getAvailableFaceDetectMode());
                camera2DataObject.put("Available Filter Density", camera2Data.getAvailableFilterDensity());
                camera2DataObject.put("Available Flash", camera2Data.getAvailableFlash());
                camera2DataObject.put("Available Focal Length", camera2Data.getAvailableFocalLength());
                camera2DataObject.put("Available Hot Pixel Map Mode", camera2Data.getAvailableHotPixelMapMode());
                camera2DataObject.put("Available Hot Pixel Mode", camera2Data.getAvailableHotPixelMode());
                camera2DataObject.put("Available JPEG Thumbnail Size", camera2Data.getAvailableJpegThumbnailSize());
                camera2DataObject.put("Available Max Digital Zoom", camera2Data.getAvailableMaxDigitalZoom());
                camera2DataObject.put("Available Noise Reduction Mode", camera2Data.getAvailableNoiseReductionMode());
                camera2DataObject.put("Available Optical Stabilization", camera2Data.getAvailableOpticalStabilization());
                camera2DataObject.put("Available Request Capability", camera2Data.getAvailableRequestCapability());
                camera2DataObject.put("Available Scene Mode", camera2Data.getAvailableSceneMode());
                camera2DataObject.put("Available Test Pattern Mode", camera2Data.getAvailableTestPartternMode());
                camera2DataObject.put("Available Tone Map Mode", camera2Data.getAvailableToneMapMode());
                camera2DataObject.put("Available Video Stabilization Mode", camera2Data.getAvailableVideoStabilizationMode());
                camera2DataObject.put("Color Filter Arrangement", camera2Data.getColorFilterArrangement());
                camera2DataObject.put("Exposure Time Range", camera2Data.getExposureTimeRange());
                camera2DataObject.put("Focus Distance Calibration", camera2Data.getFocusDistanceCalibration());
                camera2DataObject.put("Hyper Focal Distance Supported", camera2Data.getHyperfocalDistance());
                camera2DataObject.put("Lens Facing", camera2Data.getLensFacing());
                camera2DataObject.put("Max Analog Sensitivity", camera2Data.getMaxAnalogSensitivity());
                camera2DataObject.put("Max Face Count", camera2Data.getMaxFaceCount());
                camera2DataObject.put("Max Frame Duration", camera2Data.getMaxFrameDutaion());
                camera2DataObject.put("Max Number Output Proc", camera2Data.getMaxNumOutputProc());
                camera2DataObject.put("Max Number Output Proc Stall", camera2Data.getMaxNumOutputProcStall());
                camera2DataObject.put("Max Number Output RAW", camera2Data.getMaxNumOutputRaw());
                camera2DataObject.put("Max Region AE", camera2Data.getMaxRegionAE());
                camera2DataObject.put("Max Region AF", camera2Data.getMaxRegionAF());
                camera2DataObject.put("Max Region AWB", camera2Data.getMaxRegionAWB());
                camera2DataObject.put("Minimum Focus Distance", camera2Data.getMinimumFocusDistance());
                camera2DataObject.put("Partial Result Count", camera2Data.getPartialResultCount());
                camera2DataObject.put("Physical Size", camera2Data.getPhysicalSize());
                camera2DataObject.put("Pipeline Max Depth", camera2Data.getPipelineMaxDepth());
                camera2DataObject.put("Pixel Array Size", camera2Data.getPixelArraySize());
                camera2DataObject.put("Reference Illuminant 1", camera2Data.getReferenceIlluminant1());
                camera2DataObject.put("Reference Illuminant 2", camera2Data.getReferenceIlluminant2());
                camera2DataObject.put("Scale Cropping Type", camera2Data.getScaleCroppingType());
                camera2DataObject.put("Sensitivity Range", camera2Data.getSensitivityRange());
                camera2DataObject.put("Sensor Orientation", camera2Data.getSensorOrientation());
                camera2DataObject.put("Color Correction Aberration Mode", camera2Data.getColorCorrectionAberrationMode());
                camera2DataObject.put("Support Hardware Level", camera2Data.getSupportHardwareLevel());
                camera2DataObject.put("High Speed Video FPS Range", camera2Data.getHighSpeedVideoFpsRange());
                camera2DataObject.put("High Speed Video Size", camera2Data.getHighSpeedVideoSize());
                camera2DataObject.put("Support Image Format", camera2Data.getSupportImageFormat());
                camera2DataObject.put("Support Output Size", camera2Data.getSupportOutputSize());
                camera2DataObject.put("Sync Max Latency", camera2Data.getSyncMaxLatency());
                camera2DataObject.put("Timestamp Source", camera2Data.getTimestampSource());
                camera2DataObject.put("Tone Map Max Curve Point", camera2Data.getToneMapMaxCurvePoint());
                camera2DataObject.put("White level", camera2Data.getWhiteLevel());
                camera2Array.put(camera2DataObject);
            }


            // Feature Info - Supported Feature
            int supportFeatureCount = FeatureManager.getSupportFeatureCount();
            JSONArray supportFeatureObject = new JSONArray();
            for(int i = 0 ; i < supportFeatureCount ; i++) {
                FeatureData data = FeatureManager.getSupportFeature(i);
                supportFeatureObject.put(data.getName());
            }

            // Feature Info - Unsupported Feature
            int unsupportFeatureCount = FeatureManager.getUnsupportFeatureCount();
            JSONArray unsupportFeatureObject = new JSONArray();
            for(int i = 0 ; i < unsupportFeatureCount ; i++) {
                FeatureData data = FeatureManager.getUnsupportFeature(i);
                unsupportFeatureObject.put(data.getName());
            }

            // Feature Info
            JSONObject featureObject = new JSONObject();
            featureObject.put("Supported", supportFeatureObject);
            featureObject.put("Unsupported", unsupportFeatureObject);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Hardware Info", hwswObject);
            jsonObject.put("Sensor Info", sensorArray);
            jsonObject.put("Screen Info", screenObject);
            jsonObject.put("Camera Info", cameraArray);
            jsonObject.put("Camera 2 Info", camera2Array);
            jsonObject.put("Feature Info", featureObject);

            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getFingerprint(Context context) {
        return FileParser.getFingerprint(context);
    }

    public static ArrayList<SimpleData> getAndroidData(Context context) {
        return FileParser.getAndroidData(context);
    }

    public static ArrayList<SimpleData> getBatteryData(Context context) {
        return FileParser.getBatteryData(context);
    }

    public static ArrayList<SimpleData> getBuildData(Context context) {
        return FileParser.getBuildData(context);
    }

    public static ArrayList<SimpleData> getCommunicationData(Context context) {
        return FileParser.getCommunicationData(context);
    }

    public static ArrayList<SimpleData> getCpuData(Context context) {
        return FileParser.getCpuData(context);
    }

    public static ArrayList<SimpleData> getGpuData(Context context) {
        return FileParser.getGpuData(context);
    }

    public static ArrayList<SimpleData> getMemoryData(Context context) {
        return FileParser.getMemoryData(context);
    }

    public static ArrayList<SimpleData> getStorageData(Context context) {
        return FileParser.getStorageData(context);
    }

    public static ArrayList<SensorData> getSensorData(Context context) {
        return FileParser.getSensorData(context);
    }

    public static SensorData getSensorData(Context context, int position) {
        return FileParser.getSensorData(context, position);
    }

    public static int getSensorDataCount(Context context) {
        return FileParser.getSensorDataCount(context);
    }

    public static ScreenData getScreenData(Context context) {
        return FileParser.getScreenData(context);
    }

    public static CameraData getCameraData(Context context, int position) {
        return FileParser.getCameraData(context, position);
    }

    public static int getCameraDataCount(Context context) {
        return FileParser.getCameraDataCount(context);
    }

    public static String getCameraId(Context context, int position) {
        return FileParser.getCameraId(context, position);
    }

    public static Camera2Data getCamera2Data(Context context, int position) {
        return FileParser.getCamera2Data(context, position);
    }

    public static int getCamera2DataCount(Context context) {
        return FileParser.getCamera2DataCount(context);
    }

    public static String getCamera2Id(Context context, int position) {
        return FileParser.getCamera2Id(context, position);
    }

    public static FeatureData getSupportedFeatureData(Context context, int position) {
        return FileParser.getSupportedFeatureData(context, position);
    }

    public static int getSupportedFeatureDataCount(Context context) {
        return FileParser.getSupportedFeatureDataCount(context);
    }

    public static FeatureData getUnsupportedFeatureData(Context context, int position) {
        return FileParser.getUnsupportedFeatureData(context, position);
    }

    public static int getUnsupportedFeatureDataCount(Context context) {
        return FileParser.getUnsupportedFeatureDataCount(context);
    }

    public static int getSystemAppDataCount(Context context) {
        return 0;
    }

    public static ArrayList<AppData> getAppList(Context context, int type) {
        PackageManager packageManager = context.getPackageManager();
        List<ApplicationInfo> applicationInfos = packageManager.getInstalledApplications(0);

        ArrayList<AppData> appDataArrayList = new ArrayList<>();

        for(ApplicationInfo applicationInfo : applicationInfos) {
            try {
                if((applicationInfo.flags  & ApplicationInfo.FLAG_SYSTEM) == type) {
                    String pack = applicationInfo.packageName;
                    boolean same = false;
                    for(int i = 0 ; i < appDataArrayList.size() ; i++) {
                        if(pack.equals(appDataArrayList.get(i).getPackage()))
                            same = true;
                    }
                    if(!same) {
                        String name;
                        try{
                            name = (String)packageManager.getApplicationLabel(applicationInfo);
                        } catch(Resources.NotFoundException e) {
                            name = pack;
                        }
                        PackageInfo packageInfo = packageManager.getPackageInfo(pack, 0);
                        String versionName = packageInfo.versionName;
                        String versionCode = packageInfo.versionCode + "";
                        int resId = applicationInfo.icon;

                        AppData appData = new AppData(name, pack, resId, versionName, versionCode, packageInfo.firstInstallTime);

                        packageInfo = packageManager.getPackageInfo(pack, PackageManager.GET_PERMISSIONS);
                        String[] requestedPermissions = packageInfo.requestedPermissions;
                        if(requestedPermissions != null) {
                            for (String requestPermission : requestedPermissions) {
                                appData.addPermission(requestPermission);
                            }

                            ArrayList<String> appPermission = appData.getPermissions();
                            Collections.sort(appPermission, new Comparator<String>() {
                                public int compare(String s1, String s2) {
                                    return s1.compareToIgnoreCase(s2);
                                }
                            });
                        }

                        ActivityInfo[] activityInfos = packageInfo.activities;
                        if(activityInfos != null) {
                            for (ActivityInfo activityInfo : activityInfos) {
                                appData.addActivityInfo(activityInfo);
                            }
                        }

                        packageInfo = packageManager.getPackageInfo(pack, PackageManager.GET_CONFIGURATIONS);
                        FeatureInfo[] featureInfos = packageInfo.reqFeatures;
                        if(featureInfos != null) {
                            for (FeatureInfo featureInfo : featureInfos) {
                                appData.addFeaturerInfo(featureInfo);
                            }
                        }

                        packageInfo = packageManager.getPackageInfo(pack, PackageManager.GET_PROVIDERS);
                        ProviderInfo[] providerInfos = packageInfo.providers;
                        if(providerInfos != null) {
                            for (ProviderInfo providerInfo : providerInfos) {
                                appData.addProviderInfo(providerInfo);
                            }
                        }

                        packageInfo = packageManager.getPackageInfo(pack, PackageManager.GET_RECEIVERS);
                        ActivityInfo[] receiverInfos = packageInfo.receivers;
                        if(receiverInfos != null) {
                            for (ActivityInfo receiverInfo : receiverInfos) {
                                appData.addReceiverInfo(receiverInfo);
                            }
                        }
                        appDataArrayList.add(appData);
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        Collections.sort(appDataArrayList, new Comparator<AppData>() {
            public int compare(AppData s1, AppData s2) {
                return s1.getName().compareToIgnoreCase(s2.getName());
            }
        });

        return appDataArrayList;
    }
}
