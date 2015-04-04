package app.akeorcist.deviceinformation.event;

/**
 * Created by Ake on 2/25/2015.
 */
public class DeviceNameResultEvent {
    public static final String RESULT_FOUND = "found";
    public static final String RESULT_NOT_FOUND = "not_found";
    public static final String RESULT_FAILURE = "failure";

    private String result;
    private String deviceName = "";
    private String deviceBrand = "";
    private String deviceModel = "";
    private String deviceImage = "";
    private String message;

    public DeviceNameResultEvent(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setDeviceName(String name) {
        deviceName = name;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceBrand(String brand) {
        deviceBrand = brand;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceModel(String model) {
        deviceModel = model;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceImage(String image) {
        deviceImage = image;
    }

    public String getDeviceImage() {
        return deviceImage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
