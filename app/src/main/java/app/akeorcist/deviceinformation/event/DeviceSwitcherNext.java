package app.akeorcist.deviceinformation.event;

/**
 * Created by Akexorcist on 6/10/15 AD.
 */
public class DeviceSwitcherNext {
    private int page;
    public DeviceSwitcherNext(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }
}
