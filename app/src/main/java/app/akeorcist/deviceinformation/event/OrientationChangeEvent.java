package app.akeorcist.deviceinformation.event;

/**
 * Created by Akexorcist on 3/29/15 AD.
 */
public class OrientationChangeEvent {
    private int screenOrientation;

    public OrientationChangeEvent(int screenOrientation) {
        this.screenOrientation = screenOrientation;
    }

    public int getScreenOrientation() {
        return screenOrientation;
    }
}
