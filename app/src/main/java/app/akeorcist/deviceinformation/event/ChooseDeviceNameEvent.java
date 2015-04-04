package app.akeorcist.deviceinformation.event;

import app.akeorcist.deviceinformation.model.DeviceList;

/**
 * Created by Ake on 2/25/2015.
 */
public class ChooseDeviceNameEvent {
    private String brand;
    private String name;
    private String model;

    public ChooseDeviceNameEvent(String brand, DeviceList deviceList) {
        this.brand = brand;
        this.name = deviceList.getName();
        this.model = deviceList.getModel();
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
}
