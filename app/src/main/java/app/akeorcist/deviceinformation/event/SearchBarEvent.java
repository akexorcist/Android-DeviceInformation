package app.akeorcist.deviceinformation.event;

/**
 * Created by Akexorcist on 6/10/15 AD.
 */
public class SearchBarEvent {
    private boolean isSearchBarExpanded;

    public SearchBarEvent(boolean isSearchBarExpanded) {
        this.isSearchBarExpanded = isSearchBarExpanded;
    }

    public boolean isSearchBarExpanded() {
        return isSearchBarExpanded;
    }
}
