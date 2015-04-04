package app.akeorcist.deviceinformation.event;

/**
 * Created by Akexorcist on 3/11/15 AD.
 */
public class ViewEvent {
    public static final String EVENT_VIEW_SHOW_UP = "view_show_up";
    public static final String EVENT_MENU_SELECTED = "menu_selected";
    public static final String EVENT_REFRESH_LIST = "refresh_list";

    private String eventState;

    public ViewEvent(String eventState) {
        this.eventState = eventState;
    }

    public String getEventState() {
        return eventState;
    }

    public void setEventState(String eventState) {
        this.eventState = eventState;
    }
}
