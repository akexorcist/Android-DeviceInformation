package app.akeorcist.deviceinformation.data.device;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.MediaRecorder;
import android.os.Build;
import android.util.Log;
import android.util.Range;
import android.util.Rational;
import android.util.Size;
import android.util.SizeF;

import java.util.ArrayList;

import app.akeorcist.deviceinformation.utility.StringUtils;
import app.akeorcist.deviceinformation.model.Camera2Data;

/**
 * Created by Ake on 2/28/2015.
 */
public class Camera2Manager {
    private static final int DATA_COUNT = 61;
    private static ArrayList<Camera2Data> cameraDataList = null;

    private static String str = "";

    public static void addString(String string) {
        str += string + "\n";
    }

    public static int getCameraCount() {
        return cameraDataList.size();
    }

    public static int getCameraDataCount(int position) {
        return DATA_COUNT;
    }

    public static Camera2Data getCameraData(int position) {
        return cameraDataList.get(position);
    }

    @SuppressLint("NewApi")
    public static void initialData(Activity activity) {
        cameraDataList = new ArrayList<>();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CameraManager manager = (CameraManager) activity.getSystemService(Context.CAMERA_SERVICE);
            try {
                String[] cameraList = manager.getCameraIdList();
                for (int i = 0; i < cameraList.length; i++) {
                    CameraCharacteristics cc = manager.getCameraCharacteristics(cameraList[i]);
                    Camera2Data data = new Camera2Data();
                    data.setCameraId(cameraList[i]);
                    data.setActiveArraySize(getActiveArraySize(cc));
                    data.setAECompensationRange(getAECompensationRange(cc));
                    data.setAECompensationStep(getAECompensationStep(cc));
                    data.setAvailableAEAntibandingMode(getAvailableAEAntibandingMode(cc));
                    data.setAvailableAEMode(getAvailableAEMode(cc));
                    data.setAvailableAFMode(getAvailableAFMode(cc));
                    data.setAvailableAETargetFpsRange(getAvailableAETargetFPSRange(cc));
                    data.setAvailableAperture(getAvailableAperture(cc));
                    data.setAvailableAWBMode(getAvailableAWBMode(cc));
                    data.setAvailableEdgeMode(getAvailableEdgeMode(cc));
                    data.setAvailableEffect(getAvailableEffect(cc));
                    data.setAvailableFaceDetectMode(getAvailableFaceDetectMode(cc));
                    data.setAvailableFilterDensity(getAvailableFilterDensity(cc));
                    data.setAvailableFlash(getAvailableFlash(cc));
                    data.setAvailableFocalLength(getAvailableFocalLength(cc));
                    data.setAvailableHotPixelMapMode(getAvailableHotPixelMapMode(cc));
                    data.setAvailableHotPixelMode(getAvailableHotPixelMode(cc));
                    data.setAvailableJpegThumbnailSize(getAvailableJpegThumbnailSize(cc));
                    data.setAvailableMaxDigitalZoom(getAvailableMaxDigitalZoom(cc));
                    data.setAvailableNoiseReductionMode(getAvailableNoiseReductionMode(cc));
                    data.setAvailableOpticalStabilization(getAvailableOpticalStabilization(cc));
                    data.setAvailableRequestCapability(getAvailableRequestCapability(cc));
                    data.setAvailableSceneMode(getAvailableSceneMode(cc));
                    data.setAvailableTestPartternMode(getAvailableTestPatternMode(cc));
                    data.setAvailableToneMapMode(getAvailableToneMapMode(cc));
                    data.setAvailableVideoStabilizationMode(getAvailableVideoStabilizationMode(cc));
                    data.setColorFilterArrangement(getColorFilterArrangement(cc));
                    data.setExposureTimeRange(getExposureTimeRange(cc));
                    data.setFocusDistanceCalibration(getFocusDistanceCalibration(cc));
                    data.setHyperfocalDistance(getHyperfocalDistance(cc));
                    data.setLensFacing(getLensFacing(cc));
                    data.setMaxAnalogSensitivity(getMaxAnalogSensitivity(cc));
                    data.setMaxFaceCount(getMaxFaceCount(cc));
                    data.setMaxFrameDutaion(getMaxFrameDuration(cc));
                    data.setMaxNumOutputProc(getMaxNumOutputProc(cc));
                    data.setMaxNumOutputProcStall(getMaxNumOutputProcStall(cc));
                    data.setMaxNumOutputRaw(getMaxNumOutputRaw(cc));
                    data.setMaxRegionAE(getMaxRegionAE(cc));
                    data.setMaxRegionAF(getMaxRegionAF(cc));
                    data.setMaxRegionAWB(getMaxRegionAWB(cc));
                    data.setMinimumFocusDistance(getMinimumFocusDistance(cc));
                    data.setPartialResultCount(getPartialResultCount(cc));
                    data.setPhysicalSize(getPhysicalSize(cc));
                    data.setPipelineMaxDepth(getPipelineMaxDepth(cc));
                    data.setPixelArraySize(getPixelArraySize(cc));
                    data.setReferenceIlluminant1(getReferenceIlluminant1(cc));
                    data.setReferenceIlluminant2(getReferenceIlluminant2(cc));
                    data.setScaleCroppingType(getScaleCroppingType(cc));
                    data.setSensitivityRange(getSensitivityRange(cc));
                    data.setSensorOrientation(getSensorOrientation(cc));
                    data.setColorCorrectionAberrationMode(getColorCorrectionAberrationMode(cc));
                    data.setSupportHardwareLevel(getSupportHardwareLevel(cc));
                    data.setHighSpeedVideoFpsRange(getHighSpeedVideoFpsRange(cc));
                    data.setHighSpeedVideoSize(getHighSpeedVideoSize(cc));
                    data.setSupportImageFormat(getSupportImageFormat(cc));
                    data.setSupportOutputSize(getSupportOutputSize(cc));
                    data.setSyncMaxLatency(getSyncMaxLatency(cc));
                    data.setTimestampSource(getTimestampSource(cc));
                    data.setToneMapMaxCurvePoint(getToneMapMaxCurvePoint(cc));
                    data.setWhiteLevel(getWhiteLevel(cc));

                    cameraDataList.add(data);
                }

            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static void destroy() {
        if(cameraDataList != null)
            cameraDataList.clear();
        cameraDataList = null;
    }

    @SuppressLint("NewApi")
    private static String getToneMapMaxCurvePoint(CameraCharacteristics cc) {
        Integer value = cc.get(CameraCharacteristics.TONEMAP_MAX_CURVE_POINTS);
        if(value != null) {
            return StringUtils.wrapUnknownLower(value + "").trim();
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getAvailableToneMapMode(CameraCharacteristics cc) {
        int[] modes = cc.get(CameraCharacteristics.TONEMAP_AVAILABLE_TONE_MAP_MODES);
        if(modes != null) {
            String str = "";
            for(int mode : modes) {
                if(mode == CameraCharacteristics.TONEMAP_MODE_CONTRAST_CURVE) {
                    str += "contrast-curve ";
                } else if(mode == CameraCharacteristics.TONEMAP_MODE_FAST) {
                    str += "fast ";
                } else if(mode == CameraCharacteristics.TONEMAP_MODE_HIGH_QUALITY) {
                    str += "high-quality ";
                } else {
                    str += "unknown (" + mode + ") ";
                }
            }
            return StringUtils.wrapUnknownLower(str).trim();
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getSyncMaxLatency(CameraCharacteristics cc) {
        Integer value = cc.get(CameraCharacteristics.SYNC_MAX_LATENCY);
        String str = "";
        if(value != null) {
            if(value.intValue() == CameraCharacteristics.SYNC_MAX_LATENCY_PER_FRAME_CONTROL) {
                str += "per-frame-control ";
            } else if(value.intValue() == CameraCharacteristics.SYNC_MAX_LATENCY_UNKNOWN) {
                str += "unknown ";
            } else {
                str += "unknown (" + value + ") ";
            }
        }
        return StringUtils.wrapUnknownLower(str).trim();
    }

    @SuppressLint("NewApi")
    private static String getMaxFaceCount(CameraCharacteristics cc) {
        Integer value = cc.get(CameraCharacteristics.STATISTICS_INFO_MAX_FACE_COUNT);
        if(value != null) {
            return StringUtils.wrapUnknownLower(value + "").trim();
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getAvailableHotPixelMapMode(CameraCharacteristics cc) {
        boolean[] modes = cc.get(CameraCharacteristics.STATISTICS_INFO_AVAILABLE_HOT_PIXEL_MAP_MODES);
        if(modes != null) {
            String str = "";
            for (boolean mode : modes) {
                str += mode + " ";
            }
            return StringUtils.wrapUnknownLower(str).trim();
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getAvailableFaceDetectMode(CameraCharacteristics cc) {
        int[] modes = cc.get(CameraCharacteristics.STATISTICS_INFO_AVAILABLE_FACE_DETECT_MODES);
        if(modes != null) {
            String str = "";
            for (int mode : modes) {
                if (mode == CameraCharacteristics.STATISTICS_FACE_DETECT_MODE_OFF) {
                    str += "off ";
                } else if (mode == CameraCharacteristics.STATISTICS_FACE_DETECT_MODE_SIMPLE) {
                    str += "simple ";
                } else if (mode == CameraCharacteristics.STATISTICS_FACE_DETECT_MODE_FULL) {
                    str += "full ";
                } else {
                    str += "unknown (" + mode + ") ";
                }
            }
            return StringUtils.wrapUnknownLower(str).trim();
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getReferenceIlluminant2(CameraCharacteristics cc) {
        Byte value = cc.get(CameraCharacteristics.SENSOR_REFERENCE_ILLUMINANT2);
        if(value != null) {
            return getSensorReference(value.intValue());
        }
        return "unknown";
    }


    @SuppressLint("NewApi")
    private static String getReferenceIlluminant1(CameraCharacteristics cc) {
        Integer value = cc.get(CameraCharacteristics.SENSOR_REFERENCE_ILLUMINANT1);
        if(value != null) {
            return getSensorReference(value);
        }
        return "unknown";
    }

    private static String getSensorReference(Integer value) {
        if(value.intValue() == CameraCharacteristics.SENSOR_REFERENCE_ILLUMINANT1_CLOUDY_WEATHER) {
            return "cloudy-weather";
        } else if(value.intValue() == CameraCharacteristics.SENSOR_REFERENCE_ILLUMINANT1_COOL_WHITE_FLUORESCENT) {
            return "cool-white-fluorescent";
        } else if(value.intValue() == CameraCharacteristics.SENSOR_REFERENCE_ILLUMINANT1_D50) {
            return "d50";
        } else if(value.intValue() == CameraCharacteristics.SENSOR_REFERENCE_ILLUMINANT1_D55) {
            return "d55";
        } else if(value.intValue() == CameraCharacteristics.SENSOR_REFERENCE_ILLUMINANT1_D65) {
            return "d65";
        } else if(value.intValue() == CameraCharacteristics.SENSOR_REFERENCE_ILLUMINANT1_D75) {
            return "d75";
        } else if(value.intValue() == CameraCharacteristics.SENSOR_REFERENCE_ILLUMINANT1_DAY_WHITE_FLUORESCENT) {
            return "day-white-fluorescsnt";
        } else if(value.intValue() == CameraCharacteristics.SENSOR_REFERENCE_ILLUMINANT1_DAYLIGHT) {
            return "daylight";
        } else if(value.intValue() == CameraCharacteristics.SENSOR_REFERENCE_ILLUMINANT1_DAYLIGHT_FLUORESCENT) {
            return "daylight-fluorescsnt";
        } else if(value.intValue() == CameraCharacteristics.SENSOR_REFERENCE_ILLUMINANT1_FINE_WEATHER) {
            return "fine-weather";
        } else if(value.intValue() == CameraCharacteristics.SENSOR_REFERENCE_ILLUMINANT1_FLASH) {
            return "flash";
        } else if(value.intValue() == CameraCharacteristics.SENSOR_REFERENCE_ILLUMINANT1_FLUORESCENT) {
            return "fluorescent";
        } else if(value.intValue() == CameraCharacteristics.SENSOR_REFERENCE_ILLUMINANT1_ISO_STUDIO_TUNGSTEN) {
            return "iso-studio-tungsten";
        } else if(value.intValue() == CameraCharacteristics.SENSOR_REFERENCE_ILLUMINANT1_SHADE) {
            return "shade";
        } else if(value.intValue() == CameraCharacteristics.SENSOR_REFERENCE_ILLUMINANT1_STANDARD_A) {
            return "standard-a";
        } else if(value.intValue() == CameraCharacteristics.SENSOR_REFERENCE_ILLUMINANT1_STANDARD_B) {
            return "standard-b";
        } else if(value.intValue() == CameraCharacteristics.SENSOR_REFERENCE_ILLUMINANT1_STANDARD_C) {
            return "standard-c";
        } else if(value.intValue() == CameraCharacteristics.SENSOR_REFERENCE_ILLUMINANT1_TUNGSTEN) {
            return "tungsten";
        } else if(value.intValue() == CameraCharacteristics.SENSOR_REFERENCE_ILLUMINANT1_WHITE_FLUORESCENT) {
            return "white-fluorescent";
        } else {
            return "unknown (" + value.intValue() + ")";
        }
    }

    @SuppressLint("NewApi")
    private static String getSensorOrientation(CameraCharacteristics cc) {
        Integer value = cc.get(CameraCharacteristics.SENSOR_ORIENTATION);
        if(value != null) {
            return StringUtils.wrapUnknownLower(value + "").trim();
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getMaxAnalogSensitivity(CameraCharacteristics cc) {
        Integer value = cc.get(CameraCharacteristics.SENSOR_MAX_ANALOG_SENSITIVITY);
        if(value != null) {
            return StringUtils.wrapUnknownLower(value + "").trim();
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getWhiteLevel(CameraCharacteristics cc) {
        Integer value = cc.get(CameraCharacteristics.SENSOR_INFO_WHITE_LEVEL);
        if(value != null) {
            return StringUtils.wrapUnknownLower(value + "").trim();
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getTimestampSource(CameraCharacteristics cc) {
        Integer value = cc.get(CameraCharacteristics.SENSOR_INFO_TIMESTAMP_SOURCE);
        if(value != null) {
            if(value.intValue() == CameraCharacteristics.SENSOR_INFO_TIMESTAMP_SOURCE_UNKNOWN) {
                return "unknown";
            } else if(value.intValue() == CameraCharacteristics.SENSOR_INFO_TIMESTAMP_SOURCE_REALTIME) {
                return "realtime";
            } else {
                return "unknown (" + value.intValue() + ")";
            }
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getSensitivityRange(CameraCharacteristics cc) {
        Range<Integer> value = cc.get(CameraCharacteristics.SENSOR_INFO_SENSITIVITY_RANGE);
        if(value != null) {
            return StringUtils.wrapUnknownLower(value.toString()).trim();
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getPixelArraySize(CameraCharacteristics cc) {
        Size value = cc.get(CameraCharacteristics.SENSOR_INFO_PIXEL_ARRAY_SIZE);
        if(value != null) {
            return StringUtils.wrapUnknownLower(value.toString()).trim();
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getPhysicalSize(CameraCharacteristics cc) {
        SizeF value = cc.get(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE);
        if(value != null) {
            return StringUtils.wrapUnknownLower(value.toString()).trim();
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getMaxFrameDuration(CameraCharacteristics cc) {
        Long value = cc.get(CameraCharacteristics.SENSOR_INFO_MAX_FRAME_DURATION);
        if(value != null) {
            return StringUtils.wrapUnknownLower(value + "").trim();
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getExposureTimeRange(CameraCharacteristics cc) {
        Range<Long> value = cc.get(CameraCharacteristics.SENSOR_INFO_EXPOSURE_TIME_RANGE);
        if(value != null) {
            return StringUtils.wrapUnknownLower(value.toString()).trim();
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getColorFilterArrangement(CameraCharacteristics cc) {
        Integer value = cc.get(CameraCharacteristics.SENSOR_INFO_COLOR_FILTER_ARRANGEMENT);
        if(value != null) {
            if(value.intValue() == CameraCharacteristics.SENSOR_INFO_COLOR_FILTER_ARRANGEMENT_BGGR) {
                return "bggr";
            } else if(value.intValue() == CameraCharacteristics.SENSOR_INFO_COLOR_FILTER_ARRANGEMENT_GBRG) {
                return "gbrg";
            } else if(value.intValue() == CameraCharacteristics.SENSOR_INFO_COLOR_FILTER_ARRANGEMENT_GRBG) {
                return "grbg";
            } else if(value.intValue() == CameraCharacteristics.SENSOR_INFO_COLOR_FILTER_ARRANGEMENT_RGB) {
                return "rgb";
            } else if(value.intValue() == CameraCharacteristics.SENSOR_INFO_COLOR_FILTER_ARRANGEMENT_RGGB) {
                return "rggb";
            } else {
                return "unknown (" + value + ")";
            }
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getActiveArraySize(CameraCharacteristics cc) {
        Rect value = cc.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
        if(value != null) {
            return StringUtils.wrapUnknownLower(value.width() + "x" + value.height());
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getAvailableTestPatternMode(CameraCharacteristics cc) {
        int[] modes = cc.get(CameraCharacteristics.SENSOR_AVAILABLE_TEST_PATTERN_MODES);
        if(modes != null) {
            String str = "";
            for (int mode : modes) {
                if (mode == CameraCharacteristics.SENSOR_TEST_PATTERN_MODE_COLOR_BARS) {
                    str += "color-bars ";
                } else if (mode == CameraCharacteristics.SENSOR_TEST_PATTERN_MODE_COLOR_BARS_FADE_TO_GRAY) {
                    str += "color-bars-fade-to-gray ";
                } else if (mode == CameraCharacteristics.SENSOR_TEST_PATTERN_MODE_CUSTOM1) {
                    str += "custom1 ";
                } else if (mode == CameraCharacteristics.SENSOR_TEST_PATTERN_MODE_OFF) {
                    str += "off ";
                } else if (mode == CameraCharacteristics.SENSOR_TEST_PATTERN_MODE_PN9) {
                    str += "pn9 ";
                } else if (mode == CameraCharacteristics.SENSOR_TEST_PATTERN_MODE_SOLID_COLOR) {
                    str += "solid-color ";
                } else {
                    str += "unknown (" + mode + ") ";
                }
            }
            return StringUtils.wrapUnknownLower(str);
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getScaleCroppingType(CameraCharacteristics cc) {
        Integer value = cc.get(CameraCharacteristics.SCALER_CROPPING_TYPE);
        if(value != null) {
            return StringUtils.wrapUnknownLower(value + "");
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getAvailableMaxDigitalZoom(CameraCharacteristics cc) {
        Float value = cc.get(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM);
        if(value != null) {
            return StringUtils.wrapUnknownLower(value + "");
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getPipelineMaxDepth(CameraCharacteristics cc) {
        Byte value = cc.get(CameraCharacteristics.REQUEST_PIPELINE_MAX_DEPTH);
        if(value != null) {
            return StringUtils.wrapUnknownLower(value + "");
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getPartialResultCount(CameraCharacteristics cc) {
        Integer value = cc.get(CameraCharacteristics.REQUEST_PARTIAL_RESULT_COUNT);
        if(value != null) {
            return StringUtils.wrapUnknownLower(value + "");
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getMaxNumOutputRaw(CameraCharacteristics cc) {
        Integer value = cc.get(CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_RAW);
        if(value != null) {
            return StringUtils.wrapUnknownLower(value + "");
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getMaxNumOutputProcStall(CameraCharacteristics cc) {
        Integer value = cc.get(CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_PROC_STALLING);
        if(value != null) {
            return StringUtils.wrapUnknownLower(value + "");
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getMaxNumOutputProc(CameraCharacteristics cc) {
        Integer value = cc.get(CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_PROC);
        if(value != null) {
            return StringUtils.wrapUnknownLower(value + "");
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getAvailableRequestCapability(CameraCharacteristics cc) {
        int[] values = cc.get(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES);
        if(values != null) {
            String str = "";
            for (int value : values) {
                if (value == CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES_BACKWARD_COMPATIBLE) {
                    str += "backward-compatible ";
                } else if (value == CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES_MANUAL_POST_PROCESSING) {
                    str += "manual-post-processing ";
                } else if (value == CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES_MANUAL_SENSOR) {
                    str += "manual-sensor ";
                } else if (value == CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES_RAW) {
                    str += "raw ";
                } else {
                    str += "unknown (" + value + ") ";
                }
            }
            return StringUtils.wrapUnknownLower(str);
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getAvailableNoiseReductionMode(CameraCharacteristics cc) {
        int[] modes = cc.get(CameraCharacteristics.NOISE_REDUCTION_AVAILABLE_NOISE_REDUCTION_MODES);
        if(modes != null) {
            String str = "";
            for (int mode : modes) {
                if (mode == CameraCharacteristics.NOISE_REDUCTION_MODE_OFF) {
                    str += "off ";
                } else if (mode == CameraCharacteristics.NOISE_REDUCTION_MODE_FAST) {
                    str += "fast ";
                } else if (mode == CameraCharacteristics.NOISE_REDUCTION_MODE_HIGH_QUALITY) {
                    str += "high-quality ";
                } else {
                    str += "unknown (" + mode + ") ";
                }
            }
            return StringUtils.wrapUnknownLower(str);
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getMinimumFocusDistance(CameraCharacteristics cc) {
        Float value = cc.get(CameraCharacteristics.LENS_INFO_MINIMUM_FOCUS_DISTANCE);
        if(value != null) {
            return StringUtils.wrapUnknownLower(value + "");
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getHyperfocalDistance(CameraCharacteristics cc) {
        Float value = cc.get(CameraCharacteristics.LENS_INFO_HYPERFOCAL_DISTANCE);
        if(value != null) {
            return StringUtils.wrapUnknownLower(value + "");
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getFocusDistanceCalibration(CameraCharacteristics cc) {
        Integer configs = cc.get(CameraCharacteristics.LENS_INFO_FOCUS_DISTANCE_CALIBRATION);
        if(configs != null) {
            String str = "";
            if(configs.intValue() == CameraCharacteristics.LENS_INFO_FOCUS_DISTANCE_CALIBRATION_APPROXIMATE) {
                return "approximate";
            } else if(configs.intValue() == CameraCharacteristics.LENS_INFO_FOCUS_DISTANCE_CALIBRATION_CALIBRATED) {
                return "calibrated";
            } else if(configs.intValue() == CameraCharacteristics.LENS_INFO_FOCUS_DISTANCE_CALIBRATION_UNCALIBRATED) {
                return "uncalibrated";
            }
            return "unknown (" + configs.intValue() + ") ";
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getAvailableOpticalStabilization(CameraCharacteristics cc) {
        int[] values = cc.get(CameraCharacteristics.LENS_INFO_AVAILABLE_OPTICAL_STABILIZATION);
        if(values != null) {
            String str = "";
            for (float value : values) {
                str += value + " ";
            }
            return StringUtils.wrapUnknownLower(str.trim());
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getAvailableFocalLength(CameraCharacteristics cc) {
        float[] values = cc.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS);
        if(values != null) {
            String str = "";
            for (float value : values) {
                str += value + " ";
            }
            return StringUtils.wrapUnknownLower(str.trim());
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getAvailableFilterDensity(CameraCharacteristics cc) {
        float[] values = cc.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FILTER_DENSITIES);
        if(values != null) {
            String str = "";
            for (float value : values) {
                str += value + " ";
            }
            return StringUtils.wrapUnknownLower(str.trim());
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getAvailableAperture(CameraCharacteristics cc) {
        float[] values = cc.get(CameraCharacteristics.LENS_INFO_AVAILABLE_APERTURES);
        if(values != null) {
            String str = "";
            for (float value : values) {
                str += value + " ";
            }
            return StringUtils.wrapUnknownLower(str.trim());
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getLensFacing(CameraCharacteristics cc) {
        Integer configs = cc.get(CameraCharacteristics.LENS_FACING);
        if(configs != null) {
            if(configs.intValue() == CameraCharacteristics.LENS_FACING_FRONT) {
                return "front";
            } else if(configs.intValue() == CameraCharacteristics.LENS_FACING_BACK) {
                return "back";
            }
            return "unknown (" + configs.intValue() + ")";
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getAvailableJpegThumbnailSize(CameraCharacteristics cc) {
        Size[] sizes = cc.get(CameraCharacteristics.JPEG_AVAILABLE_THUMBNAIL_SIZES);
        if(sizes != null && sizes.length > 0) {
            String str = "";
            for (Size size : sizes) {
                str += size.toString() + " ";
            }
            return StringUtils.wrapUnknownLower(str.trim());
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getSupportHardwareLevel(CameraCharacteristics cc) {
        Integer level = cc.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
        if(level != null) {
            if(level.intValue() == CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_FULL) {
                str += "full ";
            } else if(level.intValue() == CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY) {
                str += "legacy ";
            } else if(level.intValue() == CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LIMITED) {
                str += "limited ";
            } else {
                str += "unknown (" + level + ") ";
            }
        }
        return StringUtils.wrapUnknownLower(str.trim());
    }

    @SuppressLint("NewApi")
    private static String getAvailableHotPixelMode(CameraCharacteristics cc) {
        int[] modes = cc.get(CameraCharacteristics.HOT_PIXEL_AVAILABLE_HOT_PIXEL_MODES);
        if(modes != null) {
            String str = "";
            for (int mode : modes) {
                if (mode == CameraCharacteristics.HOT_PIXEL_MODE_OFF) {
                    str += "off ";
                } else if (mode == CameraCharacteristics.HOT_PIXEL_MODE_FAST) {
                    str += "fast ";
                } else if (mode == CameraCharacteristics.HOT_PIXEL_MODE_HIGH_QUALITY) {
                    str += "high-quality ";
                } else {
                    str += "unknown (" + mode + ") ";
                }
            }
            return StringUtils.wrapUnknownLower(str.trim());
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getAvailableFlash(CameraCharacteristics cc) {
        Boolean available = cc.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
        if(available != null) {
            return StringUtils.wrapUnknownLower("" + available);
        }
        return "unknown ";
    }

    @SuppressLint("NewApi")
    private static String getAvailableEdgeMode(CameraCharacteristics cc) {
        int[] modes = cc.get(CameraCharacteristics.EDGE_AVAILABLE_EDGE_MODES);
        if(modes != null) {
            String str = "";
            for (int mode : modes) {
                if (mode == CameraCharacteristics.EDGE_MODE_OFF) {
                    str += "off ";
                } else if (mode == CameraCharacteristics.EDGE_MODE_FAST) {
                    str += "fast ";
                } else if (mode == CameraCharacteristics.EDGE_MODE_HIGH_QUALITY) {
                    str += "high-quality ";
                } else {
                    str += "unknown (" + mode + ") ";
                }
            }
            return StringUtils.wrapUnknownLower(str.trim());
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getMaxRegionAE(CameraCharacteristics cc) {
        Integer max = cc.get(CameraCharacteristics.CONTROL_MAX_REGIONS_AE);
        if(max != null) {
            return StringUtils.wrapUnknownLower("" + max);
        }
        return "unknown ";
    }

    @SuppressLint("NewApi")
    private static String getMaxRegionAF(CameraCharacteristics cc) {
        Integer max = cc.get(CameraCharacteristics.CONTROL_MAX_REGIONS_AF);
        if(max != null) {
            return StringUtils.wrapUnknownLower("" + max);
        }
        return "unknown ";
    }

    @SuppressLint("NewApi")
    private static String getMaxRegionAWB(CameraCharacteristics cc) {
        Integer max = cc.get(CameraCharacteristics.CONTROL_MAX_REGIONS_AWB);
        if(max != null) {
            return StringUtils.wrapUnknownLower("" + max);
        }
        return "unknown ";
    }

    @SuppressLint("NewApi")
    private static String getAvailableAWBMode(CameraCharacteristics cc) {
        int[] modes = cc.get(CameraCharacteristics.CONTROL_AWB_AVAILABLE_MODES);
        if(modes != null) {
            String str = "";
            for (int mode : modes) {
                if (mode == CameraCharacteristics.CONTROL_AWB_MODE_OFF) {
                    str += "off ";
                } else if (mode == CameraCharacteristics.CONTROL_AWB_MODE_AUTO) {
                    str += "auto ";
                } else if (mode == CameraCharacteristics.CONTROL_AWB_MODE_CLOUDY_DAYLIGHT) {
                    str += "cloudy-daylight ";
                } else if (mode == CameraCharacteristics.CONTROL_AWB_MODE_DAYLIGHT) {
                    str += "daylight ";
                } else if (mode == CameraCharacteristics.CONTROL_AWB_MODE_FLUORESCENT) {
                    str += "fluorescent ";
                } else if (mode == CameraCharacteristics.CONTROL_AWB_MODE_INCANDESCENT) {
                    str += "incandescent ";
                } else if (mode == CameraCharacteristics.CONTROL_AWB_MODE_SHADE) {
                    str += "shade ";
                } else if (mode == CameraCharacteristics.CONTROL_AWB_MODE_TWILIGHT) {
                    str += "twilight ";
                } else if (mode == CameraCharacteristics.CONTROL_AWB_MODE_WARM_FLUORESCENT) {
                    str += "warm-fluorescent ";
                } else {
                    str += "unknown (" + mode + ") ";
                }
            }
            return StringUtils.wrapUnknownLower(str.trim());
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getAvailableVideoStabilizationMode(CameraCharacteristics cc) {
        int[] modes = cc.get(CameraCharacteristics.CONTROL_AVAILABLE_VIDEO_STABILIZATION_MODES);
        if(modes != null) {
            String str = "";
            for (int mode : modes) {
                if (mode == CameraCharacteristics.CONTROL_VIDEO_STABILIZATION_MODE_OFF) {
                    str += "off ";
                } else if (mode == CameraCharacteristics.CONTROL_VIDEO_STABILIZATION_MODE_ON) {
                    str += "on ";
                } else {
                    str += "unknown (" + mode + ") ";
                }
            }
            return StringUtils.wrapUnknownLower(str.trim());
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getAvailableSceneMode(CameraCharacteristics cc) {
        int[] modes = cc.get(CameraCharacteristics.CONTROL_AVAILABLE_SCENE_MODES);
        if(modes != null) {
            String str = "";
            for (int mode : modes) {
                if (mode == CameraCharacteristics.CONTROL_SCENE_MODE_ACTION) {
                    str += "action ";
                } else if (mode == CameraCharacteristics.CONTROL_SCENE_MODE_BARCODE) {
                    str += "barcode ";
                } else if (mode == CameraCharacteristics.CONTROL_SCENE_MODE_BEACH) {
                    str += "beach ";
                } else if (mode == CameraCharacteristics.CONTROL_SCENE_MODE_CANDLELIGHT) {
                    str += "candlelight ";
                } else if (mode == CameraCharacteristics.CONTROL_SCENE_MODE_DISABLED) {
                    str += "disabled ";
                } else if (mode == CameraCharacteristics.CONTROL_SCENE_MODE_FACE_PRIORITY) {
                    str += "face-priority ";
                } else if (mode == CameraCharacteristics.CONTROL_SCENE_MODE_FIREWORKS) {
                    str += "fireworks ";
                } else if (mode == CameraCharacteristics.CONTROL_SCENE_MODE_HIGH_SPEED_VIDEO) {
                    str += "high-speed-video ";
                } else if (mode == CameraCharacteristics.CONTROL_SCENE_MODE_LANDSCAPE) {
                    str += "landscape ";
                } else if (mode == CameraCharacteristics.CONTROL_SCENE_MODE_NIGHT) {
                    str += "night ";
                } else if (mode == CameraCharacteristics.CONTROL_SCENE_MODE_NIGHT_PORTRAIT) {
                    str += "night-portrait ";
                } else if (mode == CameraCharacteristics.CONTROL_SCENE_MODE_PARTY) {
                    str += "party ";
                } else if (mode == CameraCharacteristics.CONTROL_SCENE_MODE_PORTRAIT) {
                    str += "portrait ";
                } else if (mode == CameraCharacteristics.CONTROL_SCENE_MODE_SNOW) {
                    str += "snow ";
                } else if (mode == CameraCharacteristics.CONTROL_SCENE_MODE_SPORTS) {
                    str += "sports ";
                } else if (mode == CameraCharacteristics.CONTROL_SCENE_MODE_STEADYPHOTO) {
                    str += "steadyphoto ";
                } else if (mode == CameraCharacteristics.CONTROL_SCENE_MODE_SUNSET) {
                    str += "sunset ";
                } else if (mode == CameraCharacteristics.CONTROL_SCENE_MODE_THEATRE) {
                    str += "theatre ";
                } else {
                    str += "unknown (" + mode + ") ";
                }
            }
            return StringUtils.wrapUnknownLower(str.trim());
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getAvailableEffect(CameraCharacteristics cc) {
        int[] effects = cc.get(CameraCharacteristics.CONTROL_AVAILABLE_EFFECTS);
        if(effects != null) {
            String str = "";
            for (int effect : effects) {
                if (effect == CameraCharacteristics.CONTROL_EFFECT_MODE_OFF) {
                    str += "off ";
                } else if (effect == CameraCharacteristics.CONTROL_EFFECT_MODE_AQUA) {
                    str += "aqua ";
                } else if (effect == CameraCharacteristics.CONTROL_EFFECT_MODE_BLACKBOARD) {
                    str += "blackboard ";
                } else if (effect == CameraCharacteristics.CONTROL_EFFECT_MODE_MONO) {
                    str += "mono ";
                } else if (effect == CameraCharacteristics.CONTROL_EFFECT_MODE_NEGATIVE) {
                    str += "negative ";
                } else if (effect == CameraCharacteristics.CONTROL_EFFECT_MODE_POSTERIZE) {
                    str += "posterize ";
                } else if (effect == CameraCharacteristics.CONTROL_EFFECT_MODE_SEPIA) {
                    str += "sepia ";
                } else if (effect == CameraCharacteristics.CONTROL_EFFECT_MODE_SOLARIZE) {
                    str += "solarize ";
                } else if (effect == CameraCharacteristics.CONTROL_EFFECT_MODE_WHITEBOARD) {
                    str += "whiteboard ";
                } else {
                    str += "unknown (" + effect + ") ";
                }
            }
            return StringUtils.wrapUnknownLower(str.trim());
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getAECompensationStep(CameraCharacteristics cc) {
        Rational rational = cc.get(CameraCharacteristics.CONTROL_AE_COMPENSATION_STEP);
        if(rational != null) {
            return StringUtils.wrapUnknownLower(rational.toString());
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getAECompensationRange(CameraCharacteristics cc) {
        Range<Integer> range = cc.get(CameraCharacteristics.CONTROL_AE_COMPENSATION_RANGE);
        if(range != null) {
            return StringUtils.wrapUnknownLower(range.toString());
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getAvailableAETargetFPSRange(CameraCharacteristics cc) {
        Range<Integer>[] ranges = cc.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES);
        if(ranges != null && ranges.length > 0) {
            String str = "";
            for (Range<Integer> range : ranges) {
                str += range.toString() + " ";
            }
            return StringUtils.wrapUnknownLower(str.trim());
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getAvailableAEMode(CameraCharacteristics cc) {
        int[] modes = cc.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_MODES);
        if(modes != null) {
            String str = "";
            for (int mode : modes) {
                if (mode == CameraCharacteristics.CONTROL_AE_MODE_OFF) {
                    str += "off ";
                } else if (mode == CameraCharacteristics.CONTROL_AE_MODE_ON) {
                    str += "on ";
                } else if (mode == CameraCharacteristics.CONTROL_AE_MODE_ON_ALWAYS_FLASH) {
                    str += "on-always-flash ";
                } else if (mode == CameraCharacteristics.CONTROL_AE_MODE_ON_AUTO_FLASH) {
                    str += "on-auto-flash ";
                } else if (mode == CameraCharacteristics.CONTROL_AE_MODE_ON_AUTO_FLASH_REDEYE) {
                    str += "on-auto-flash-redeye ";
                } else {
                    str += "unknown (" + mode + ") ";
                }
            }
            return StringUtils.wrapUnknownLower(str).trim();
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getAvailableAEAntibandingMode(CameraCharacteristics cc) {
        int[] modes = cc.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_ANTIBANDING_MODES);
        if(modes != null) {
            String str = "";
            for (int mode : modes) {
                if (mode == CameraCharacteristics.CONTROL_AE_ANTIBANDING_MODE_50HZ) {
                    str += "50hz ";
                } else if (mode == CameraCharacteristics.CONTROL_AE_ANTIBANDING_MODE_60HZ) {
                    str += "60hz ";
                } else if (mode == CameraCharacteristics.CONTROL_AE_ANTIBANDING_MODE_AUTO) {
                    str += "auto ";
                } else if (mode == CameraCharacteristics.CONTROL_AE_ANTIBANDING_MODE_OFF) {
                    str += "off ";
                } else {
                    str += "unknown (" + mode + ") ";
                }
            }
            return StringUtils.wrapUnknownLower(str).trim();
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getAvailableAFMode(CameraCharacteristics cc) {
        int[] modes = cc.get(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES);
        if(modes != null) {
            String str = "";
            for (int mode : modes) {
                if (mode == CameraCharacteristics.CONTROL_AF_MODE_OFF) {
                    str += "off ";
                } else if (mode == CameraCharacteristics.CONTROL_AF_MODE_AUTO) {
                    str += "auto ";
                } else if (mode == CameraCharacteristics.CONTROL_AF_MODE_EDOF) {
                    str += "edof ";
                } else if (mode == CameraCharacteristics.CONTROL_AF_MODE_CONTINUOUS_PICTURE) {
                    str += "continuous-picture ";
                } else if (mode == CameraCharacteristics.CONTROL_AF_MODE_CONTINUOUS_VIDEO) {
                    str += "continuous-video ";
                } else if (mode == CameraCharacteristics.CONTROL_AF_MODE_MACRO) {
                    str += "macro ";
                } else {
                    str += "unknown (" + mode + ") ";
                }
            }
            return StringUtils.wrapUnknownLower(str).trim();
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getHighSpeedVideoSize(CameraCharacteristics cc) {
        StreamConfigurationMap configs = cc.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        Size[] sizes = configs.getHighSpeedVideoSizes();
        if(sizes != null && sizes.length > 0) {
            String str = "";
            for (Size size : sizes) {
                str += size.getWidth() + "x" + size.getHeight() + " ";
            }
            return StringUtils.wrapUnknownLower(str.trim());
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getHighSpeedVideoFpsRange(CameraCharacteristics cc) {
        StreamConfigurationMap configs = cc.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        Range<Integer>[] ranges = configs.getHighSpeedVideoFpsRanges();
        if(ranges != null && ranges.length > 0) {
            String str = "";
            for (Range<Integer> range : ranges) {
                str += range.toString() + " ";
            }
            return StringUtils.wrapUnknownLower(str.trim());
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getColorCorrectionAberrationMode(CameraCharacteristics cc) {
        int[] modes = cc.get(CameraCharacteristics.COLOR_CORRECTION_AVAILABLE_ABERRATION_MODES);
        if(modes != null) {
            String str = "";
            for (int mode : modes) {
                if (mode == CameraCharacteristics.COLOR_CORRECTION_ABERRATION_MODE_FAST) {
                    str += "fast ";
                } else if (mode == CameraCharacteristics.COLOR_CORRECTION_ABERRATION_MODE_HIGH_QUALITY) {
                    str += "high-quality ";
                } else if (mode == CameraCharacteristics.COLOR_CORRECTION_ABERRATION_MODE_OFF) {
                    str += "Off ";
                } else {
                    str += "unknown (" + mode + ") ";
                }
            }
            return StringUtils.wrapUnknownLower(str).trim();
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getSupportImageFormat(CameraCharacteristics cc) {
        StreamConfigurationMap configs = cc.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        int[] sizes = configs.getOutputFormats();
        if(sizes != null) {
            String str = "";
            for (int size : sizes) {
                if (size == ImageFormat.JPEG) {
                    str += "JPEG ";
                } else if (size == ImageFormat.NV16) {
                    str += "NV16 ";
                } else if (size == ImageFormat.NV21) {
                    str += "NV21 ";
                } else if (size == ImageFormat.RAW10) {
                    str += "RAW10 ";
                } else if (size == ImageFormat.RAW_SENSOR) {
                    str += "RAW_SENSOR ";
                } else if (size == ImageFormat.RGB_565) {
                    str += "RGB_565 ";
                } else if (size == ImageFormat.YUV_420_888) {
                    str += "YUV_420_888 ";
                } else if (size == ImageFormat.YUY2) {
                    str += "YUY2 ";
                } else if (size == ImageFormat.YV12) {
                    str += "YV12 ";
                } else if (size == ImageFormat.UNKNOWN) {
                    str += "UNKNOWN (" + size + ") ";
                }
            }
            return StringUtils.wrapUnknownLower(str).trim();
        }
        return "unknown";
    }

    private static String getSupportOutputSize(CameraCharacteristics cc) {
        int[] formats = { ImageFormat.JPEG, ImageFormat.YUV_420_888, ImageFormat.RGB_565, ImageFormat.YUY2, ImageFormat.YV12
                , ImageFormat.NV16, ImageFormat.NV21, ImageFormat.RAW10, ImageFormat.RAW_SENSOR };
        for(int format : formats) {
            String str = getSupportOutputSize(cc, format);
            if(str != null && !str.equals("unknown") && !str.isEmpty())
                return str;
        }
        return "unknown";
    }

    @SuppressLint("NewApi")
    private static String getSupportOutputSize(CameraCharacteristics cc, int format) {
        StreamConfigurationMap configs = cc.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        Size[] sizes = configs.getOutputSizes(MediaRecorder.class);
        if(sizes != null && sizes.length > 0) {
            String str = "";
            for (Size size : sizes) {
                str += size.getWidth() + "x" + size.getHeight() + " ";
            }
            return StringUtils.wrapUnknownLower(str.trim());
        }
        return "unknown";
    }

}
