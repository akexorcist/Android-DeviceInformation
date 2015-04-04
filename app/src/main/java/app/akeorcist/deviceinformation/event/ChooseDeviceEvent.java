package app.akeorcist.deviceinformation.event;

/**
 * Created by Ake on 2/25/2015.
 */
public class ChooseDeviceEvent {
    private String brand;
    private String name;
    private String model;
    private String version;
    private String fingerprint;

    public ChooseDeviceEvent(String brand, String name, String model, String version, String fingerprint) {
        this.brand = brand;
        this.name = name;
        this.model = model;
        this.version = version;
        this.fingerprint = fingerprint;
    }

    public String getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public String getVersion() {
        return version;
    }
}
