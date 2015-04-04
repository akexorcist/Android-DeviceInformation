package app.akeorcist.deviceinformation.event;

/**
 * Created by Ake on 2/25/2015.
 */
public class ChooseBrandEvent {
    private String brand;

    public ChooseBrandEvent(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }
}
