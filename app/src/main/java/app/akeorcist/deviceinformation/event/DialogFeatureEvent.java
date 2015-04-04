package app.akeorcist.deviceinformation.event;

/**
 * Created by Akexorcist on 3/15/15 AD.
 */
public class DialogFeatureEvent {
    private String name;
    private String version;
    private String description;

    public DialogFeatureEvent(String name, String version, String description) {
        this.name = name;
        this.version = version;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getDescription() {
        return description;
    }
}
