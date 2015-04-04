package app.akeorcist.deviceinformation.model;

/**
 * Created by Akexorcist on 2/26/15 AD.
 */
public class SensorData {

    private String name;
    private String vendor;
    private String type;
    private String version;
    private String power;
    private String maxRange;
    private String resolution;
    private String minDelay;
    private String maxDelay;
    private String fifoReserved;
    private String fifoMax;
    private String unit;

    public SensorData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public SensorData setName(String name) {
        this.name = name;
        return this;
    }

    public String getVendor() {
        return vendor;
    }

    public SensorData setVendor(String vendor) {
        this.vendor = vendor;
        return this;
    }

    public String getType() {
        return type;
    }

    public SensorData setType(String type) {
        this.type = type;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public SensorData setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getPower() {
        return power;
    }

    public SensorData setPower(String power) {
        this.power = power;
        return this;
    }

    public String getMaxRange() {
        return maxRange;
    }

    public SensorData setMaxRange(String maxRange) {
        this.maxRange = maxRange;
        return this;
    }

    public String getResolution() {
        return resolution;
    }

    public SensorData setResolution(String resolution) {
        this.resolution = resolution;
        return this;
    }

    public String getMinDelay() {
        return minDelay;
    }

    public SensorData setMinDelay(String minDelay) {
        this.minDelay = minDelay;
        return this;
    }

    public String getMaxDelay() {
        return maxDelay;
    }

    public SensorData setMaxDelay(String maxDelay) {
        this.maxDelay = maxDelay;
        return this;
    }

    public String getFifoReserved() {
        return fifoReserved;
    }

    public SensorData setFifoReserved(String fifoReserved) {
        this.fifoReserved = fifoReserved;
        return this;
    }

    public String getFifoMax() {
        return fifoMax;
    }

    public SensorData setFifoMax(String fifoMax) {
        this.fifoMax = fifoMax;
        return this;
    }

    public String getUnit() {
        return unit;
    }

    public SensorData setUnit(String unit) {
        this.unit = unit;
        return this;
    }
}