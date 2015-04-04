package app.akeorcist.deviceinformation.event;

/**
 * Created by Ake on 2/25/2015.
 */
public class PagerControlEvent {
    public static final String MOVE_NEXT = "move_next";
    public static final String MOVE_PREV = "move_prev";
    public static final String MOVE_POSITION = "move_position";
    private String command;
    private int page;

    public PagerControlEvent(String command) {
        this.command = command;
    }

    public PagerControlEvent(int page) {
        this.command = MOVE_POSITION;
        this.page = page;
    }

    public String getCommand() {
        return command;
    }

    public int getPage() {
        return page;
    }
}
