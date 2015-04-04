package app.akeorcist.deviceinformation.model;

/**
 * Created by Akexorcist on 2/27/15 AD.
 */
public class ScreenData {
    private String resolutionPx;
    private String resolutionDp;
    private String dpiX;
    private String dpiY;
    private String dpi;
    private String size;
    private String density;
    private String multitouch;

    public ScreenData() { }

    public String getResolutionPx() {
        return resolutionPx;
    }

    public ScreenData setResolutionPx(String resolutionPx) {
        this.resolutionPx = resolutionPx;
        return this;
    }

    public String getResolutionDp() {
        return resolutionDp;
    }

    public ScreenData setResolutionDp(String resolutionDp) {
        this.resolutionDp = resolutionDp;
        return this;
    }

    public String getDpiX() {
        return dpiX;
    }

    public ScreenData setDpiX(String dpiX) {
        this.dpiX = dpiX;
        return this;
    }

    public String getDpiY() {
        return dpiY;
    }

    public ScreenData setDpiY(String dpiY) {
        this.dpiY = dpiY;
        return this;
    }

    public String getDpi() {
        return dpi;
    }

    public ScreenData setDpi(String dpi) {
        this.dpi = dpi;
        return this;
    }

    public String getSize() {
        return size;
    }

    public ScreenData setSize(String size) {
        this.size = size;
        return this;
    }

    public String getDensity() {
        return density;
    }

    public ScreenData setDensity(String density) {
        this.density = density;
        return this;
    }

    public String getMultitouch() {
        return multitouch;
    }

    public ScreenData setMultitouch(String multitouch) {
        this.multitouch = multitouch;
        return this;
    }
}
