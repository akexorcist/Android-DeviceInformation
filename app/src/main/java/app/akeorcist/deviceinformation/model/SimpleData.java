package app.akeorcist.deviceinformation.model;

/**
 * Created by Akexorcist on 2/26/15 AD.
 */
public class SimpleData {
    private String title;
    private String detail;

    public SimpleData(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
