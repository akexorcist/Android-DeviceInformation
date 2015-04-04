package app.akeorcist.deviceinformation.event;

import app.akeorcist.deviceinformation.model.SubDevice;

/**
 * Created by Ake on 2/25/2015.
 */
public class DeviceSwitchEvent {
    public static final String EVENT_SUCCESS = "success";
    public static final String EVENT_FAILURE = "failure";
    private SubDevice subDevice;
    private String event;
    private String message;

    public DeviceSwitchEvent(String event) {
        this.event = event;
    }

    public void setSubDevice(SubDevice subDevice) {
        this.subDevice = subDevice;
    }

    public SubDevice getSubDevice() {
        return subDevice;
    }

    public String getEvent() {
        return event;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
