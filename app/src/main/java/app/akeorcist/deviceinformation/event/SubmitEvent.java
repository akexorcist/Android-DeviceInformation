package app.akeorcist.deviceinformation.event;

/**
 * Created by Ake on 2/25/2015.
 */
public class SubmitEvent {
    public static final String EVENT_CONNECTION_UNAVAILABLE = "connection_unavailable";
    public static final String EVENT_SEND_FINISHED = "send_finished";
    public static final String EVENT_SEND_FAILED = "send_failed";
    public static final String EVENT_DEVICE_EXIST = "device_exist";
    public static final String EVENT_DEVICE_NOT_EXIST = "device_not_exist";

    private String event = "";
    private String message = "";

    public SubmitEvent(String event) {
        this.event = event;
    }

    public String getEvent() {
        return event;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
