package app.akeorcist.deviceinformation.event;

/**
 * Created by Ake on 2/25/2015.
 */
public class WelcomeEvent {
    public static final String EVENT_NEXT = "next";
    public static final String EVENT_SKIP = "skip";

    private String event = "";

    public WelcomeEvent(String event) {
        this.event = event;
    }

    public String getEvent() {
        return event;
    }
}
