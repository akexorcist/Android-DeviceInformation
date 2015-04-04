package app.akeorcist.deviceinformation.data.device;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Build;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import app.akeorcist.deviceinformation.data.device.hardware.AndroidManager;
import app.akeorcist.deviceinformation.data.device.hardware.BatteryManager;
import app.akeorcist.deviceinformation.data.device.hardware.BuildManager;
import app.akeorcist.deviceinformation.data.device.hardware.CommunicationManager;
import app.akeorcist.deviceinformation.data.device.hardware.CpuManager;
import app.akeorcist.deviceinformation.data.device.hardware.GpuManager;
import app.akeorcist.deviceinformation.data.device.hardware.MemoryManager;
import app.akeorcist.deviceinformation.data.device.hardware.StorageManager;
import app.akeorcist.deviceinformation.model.SimpleData;

/**
 * Created by Akexorcist on 2/26/15 AD.
 */
public class HardwareManager {
    private static final int DATA_COUNT = 8;

    private static ArrayList<SimpleData> androidDataList = null;
    private static ArrayList<SimpleData> buildDataList = null;
    private static ArrayList<SimpleData> batteryDataList = null;
    private static ArrayList<SimpleData> communicationDataList = null;
    private static ArrayList<SimpleData> gpuDataList = null;
    private static ArrayList<SimpleData> memoryDataList = null;
    private static ArrayList<SimpleData> storageDataList = null;
    private static ArrayList<SimpleData> cpuDataList = null;
    private static String cpuRawData;

    public static void initialData(Activity activity, GLSurfaceView glSurfaceView, OnGLSurfaceViewLoadedListener listener) {
        initialAndroidData();
        initialBuildData();
        initialBatteryData(activity);
        initialCommunicationData(activity);
        initialCpuData();
        initialGpuData(glSurfaceView, activity, listener);
        initialMemoryData();
        initialStorageData();
    }

    public static void destroy() {
        if(androidDataList != null)
            androidDataList.clear();
        androidDataList = null;
        if(buildDataList != null)
            buildDataList.clear();
        buildDataList = null;
        if(batteryDataList != null)
            batteryDataList.clear();
        batteryDataList = null;
        if(communicationDataList != null)
            communicationDataList.clear();
        communicationDataList = null;
        if(gpuDataList != null)
            gpuDataList.clear();
        gpuDataList = null;
        if(memoryDataList != null)
            memoryDataList.clear();
        memoryDataList = null;
        if(storageDataList != null)
            storageDataList.clear();
        storageDataList = null;
        if(cpuDataList != null)
            cpuDataList.clear();
        cpuDataList = null;
    }

    public static int getHardwareDataCount() {
        return DATA_COUNT;
    }

    private static void initialAndroidData() {
        androidDataList = new ArrayList<>();
        androidDataList.add(new SimpleData("System Type", AndroidManager.getSystemType()));
        androidDataList.add(new SimpleData("Android Version", Build.VERSION.RELEASE));
        androidDataList.add(new SimpleData("Version Code", AndroidManager.getVersionCode()));
        androidDataList.add(new SimpleData("API Version", Build.VERSION.SDK_INT + ""));
        androidDataList.add(new SimpleData("Incremental", Build.VERSION.INCREMENTAL));
        androidDataList.add(new SimpleData("Codename", Build.VERSION.CODENAME));
    }

    private static void initialBatteryData(Context context) {
        batteryDataList = new ArrayList<>();
        batteryDataList.add(new SimpleData("Battery", BatteryManager.getCapacity(context)));
    }

    @SuppressWarnings("deprecation")
    private static void initialBuildData() {
        buildDataList = new ArrayList<>();
        buildDataList.add(new SimpleData("Board", Build.BOARD));
        buildDataList.add(new SimpleData("Bootloader", Build.BOOTLOADER));
        buildDataList.add(new SimpleData("Brand", Build.BRAND));
        buildDataList.add(new SimpleData("Characteristic", InfoManager.HardwareInfo.getChar()));
        buildDataList.add(new SimpleData("CPU ABI", Build.CPU_ABI));
        buildDataList.add(new SimpleData("CPU ABI 2", Build.CPU_ABI2));
        buildDataList.add(new SimpleData("Device", Build.DEVICE));
        buildDataList.add(new SimpleData("Display", Build.DISPLAY));
        buildDataList.add(new SimpleData("Fingerprint", Build.FINGERPRINT));
        buildDataList.add(new SimpleData("Hardware", Build.HARDWARE));
        buildDataList.add(new SimpleData("Host", Build.HOST));
        buildDataList.add(new SimpleData("ID", Build.ID));
        buildDataList.add(new SimpleData("Manufacturer", Build.MANUFACTURER));
        buildDataList.add(new SimpleData("Model", Build.MODEL));
        buildDataList.add(new SimpleData("Product", Build.PRODUCT));
        buildDataList.add(new SimpleData("Radio", BuildManager.getRadio()));
        buildDataList.add(new SimpleData("Serial", BuildManager.getSerial()));
        buildDataList.add(new SimpleData("Supported ABIS", BuildManager.getSupportABIS()));
        buildDataList.add(new SimpleData("Supported 32-bit ABIS", BuildManager.getSupport32ABIS()));
        buildDataList.add(new SimpleData("Supported 64-bit ABIS", BuildManager.getSupport64ABIS()));
        buildDataList.add(new SimpleData("Tags", Build.TAGS));
        buildDataList.add(new SimpleData("Time", Build.TIME + ""));
        buildDataList.add(new SimpleData("Type", Build.TYPE));
        buildDataList.add(new SimpleData("User", Build.USER));
    }

    private static void initialCpuData() {
        cpuDataList = new ArrayList<>();
        cpuRawData = CpuManager.getCpuInfo();
        if(!cpuRawData.equals("Unknown")) {
            String[] strCpuList = cpuRawData.trim().split("\n");
            for (int i = 0; i < strCpuList.length; i++) {
                if (!strCpuList[i].equals("")) {
                    try {
                        String[] strCpuRow = strCpuList[i].split(":");
                        String strTitle = strCpuRow[0].trim();
                        String strDetail = strCpuRow[1].trim();
                        cpuDataList.add(new SimpleData(strTitle, strDetail));
                    } catch (IndexOutOfBoundsException e) { }
                }
            }
        }
    }

    private static void initialCommunicationData(Activity activity) {
        communicationDataList = new ArrayList<>();
        communicationDataList.add(new SimpleData("Vibrate Motor", CommunicationManager.hasVibrate(activity)));
        communicationDataList.add(new SimpleData("Microphone", CommunicationManager.hasMicrophone(activity)));
        communicationDataList.add(new SimpleData("Telephony", CommunicationManager.hasTelephony(activity)));
        communicationDataList.add(new SimpleData("Cellular", CommunicationManager.hasCellular(activity)));
        communicationDataList.add(new SimpleData("GPS", CommunicationManager.hasGps(activity)));
        communicationDataList.add(new SimpleData("Bluetooth", CommunicationManager.hasBluetooth(activity)));
        communicationDataList.add(new SimpleData("Bluetooth LE", CommunicationManager.hasBluetoothLE(activity)));
        communicationDataList.add(new SimpleData("WiFi", CommunicationManager.hasWiFi(activity)));
        communicationDataList.add(new SimpleData("WiFi Direct", CommunicationManager.hasWiFiDirect(activity)));
        communicationDataList.add(new SimpleData("Ethernet", CommunicationManager.hasEthernet(activity)));
        communicationDataList.add(new SimpleData("WiMax", CommunicationManager.hasWiMax(activity)));
        communicationDataList.add(new SimpleData("USB OTG", CommunicationManager.hasOTG(activity)));
        communicationDataList.add(new SimpleData("USB Accessory", CommunicationManager.hasAOA(activity)));
        communicationDataList.add(new SimpleData("NFC", CommunicationManager.hasNFC(activity)));
        communicationDataList.add(new SimpleData("NFC HCE", CommunicationManager.hasNFCHost(activity)));
    }

    private static void initialGpuData(GLSurfaceView glSurfaceView, Activity activity, final OnGLSurfaceViewLoadedListener listener) {
        gpuDataList = new ArrayList<>();
        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setRenderer(new GpuManager.Renderer(new GpuManager.Renderer.SurfaceListener() {
            @Override
            public void onSurfaceCreated(GL10 gl, EGLConfig config) {
                gpuDataList.add(new SimpleData("Renderer", gl.glGetString(GL10.GL_RENDERER)));
                gpuDataList.add(new SimpleData("Vendor", gl.glGetString(GL10.GL_VENDOR)));
                gpuDataList.add(new SimpleData("Version", gl.glGetString(GL10.GL_VERSION)));
                gpuDataList.add(new SimpleData("Extensions", gl.glGetString(GL10.GL_EXTENSIONS)));
                listener.onLoaded();
            }
        }));
        gpuDataList.add(new SimpleData("OpenGL Supported", GpuManager.getOpenGLVersion(activity) + ""));
    }

    private static void initialMemoryData() {
        memoryDataList = new ArrayList<>();
        memoryDataList.add(new SimpleData("Total Memory", MemoryManager.getMemory()));
        memoryDataList.add(new SimpleData("Heap Size", MemoryManager.getHeapSize()));
        memoryDataList.add(new SimpleData("Heap Start Size", MemoryManager.getHeapStartSize()));
        memoryDataList.add(new SimpleData("Heap Growth Limit", MemoryManager.getHeapGrowthLimit()));
    }

    private static void initialStorageData() {
        storageDataList = new ArrayList<>();
        storageDataList.add(new SimpleData("Internal Storage", StorageManager.getTotalInternalStorage()));
        storageDataList.add(new SimpleData("SD Card Supported", StorageManager.isSDSupported()));
    }

    public static ArrayList<SimpleData> getAndroidDataList() {
        return androidDataList;
    }

    public static ArrayList<SimpleData> getBatteryDataList() {
        return batteryDataList;
    }

    public static ArrayList<SimpleData> getBuildDataList() {
        return buildDataList;
    }

    public static ArrayList<SimpleData> getCommunicationDataList() {
        return communicationDataList;
    }

    public static ArrayList<SimpleData> getCpuDataList() {
        return cpuDataList;
    }

    public static ArrayList<SimpleData> getGpuDataList() {
        return gpuDataList;
    }

    public static ArrayList<SimpleData> getMemoryDataList() {
        return memoryDataList;
    }

    public static ArrayList<SimpleData> getStorageDataList() {
        return storageDataList;
    }

    public interface OnGLSurfaceViewLoadedListener {
        public void onLoaded();
    }
}