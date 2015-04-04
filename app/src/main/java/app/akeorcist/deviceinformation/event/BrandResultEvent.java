package app.akeorcist.deviceinformation.event;

import java.util.ArrayList;

/**
 * Created by Akexorcist on 3/29/15 AD.
 */
public class BrandResultEvent {
    ArrayList<String> arrBrandList;

    public BrandResultEvent(ArrayList<String> arrBrandList) {
        this.arrBrandList = arrBrandList;
    }

    public ArrayList<String> getBrandList() {
        return arrBrandList;
    }
}
