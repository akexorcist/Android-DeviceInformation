package app.akeorcist.deviceinformation.event;

/**
 * Created by Akexorcist on 6/10/15 AD.
 */
public class DeviceSwitcherBackable {
    private boolean isDeviceSwitcherBackable;
    public DeviceSwitcherBackable(boolean isDeviceSwitcherBackable) {
        this.isDeviceSwitcherBackable = isDeviceSwitcherBackable;
    }

    public boolean isDeviceSwitcherBackable() {
        return isDeviceSwitcherBackable;
    }
}
