package app.akeorcist.deviceinformation.event;

import app.akeorcist.deviceinformation.model.SubDevice;

/**
 * Created by Ake on 2/25/2015.
 */
public class ConfirmSwitchEvent {
    private SubDevice subDevice;

    public ConfirmSwitchEvent(SubDevice subDevice) {
        this.subDevice = subDevice;
    }

    public SubDevice getSubDevice() {
        return subDevice;
    }

}
