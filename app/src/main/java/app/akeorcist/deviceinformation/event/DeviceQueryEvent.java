package app.akeorcist.deviceinformation.event;

import java.util.ArrayList;

import app.akeorcist.deviceinformation.model.DeviceList;
import app.akeorcist.deviceinformation.model.SubDevice;

/**
 * Created by Ake on 2/25/2015.
 */
public class DeviceQueryEvent {
    public static final String RESULT_SUCCESS = "found";
    public static final String RESULT_FAILURE = "failure";
    public static final String EVENT_BRAND = "brand";
    public static final String EVENT_NAME = "name";
    public static final String EVENT_SUB = "sub_name";

    private ArrayList<String> arrBrandList;
    private ArrayList<DeviceList> arrDeviceList;
    private ArrayList<SubDevice> arrSubList;
    private String result;
    private String event;
    private String brand;
    private String name;
    private String message;

    public DeviceQueryEvent(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public String getEvent() {
        return event;
    }

    public void setBrandList(ArrayList<String> arrBrandList) {
        this.arrBrandList = arrBrandList;
    }

    public ArrayList<String> getBrandList() {
        return arrBrandList;
    }

    public void setDeviceList(ArrayList<DeviceList> arrDeviceList) {
        this.arrDeviceList = arrDeviceList;
    }

    public ArrayList<DeviceList> getDeviceList() {
        return arrDeviceList;
    }

    public void setSubList(ArrayList<SubDevice> arrSubList) {
        this.arrSubList = arrSubList;
    }

    public ArrayList<SubDevice> getSubList() {
        return arrSubList;
    }

    public void setBrandType() {
        event = EVENT_BRAND;
    }

    public void setNameType() {
        event = EVENT_NAME;
    }

    public void setSubType() {
        event = EVENT_SUB;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
