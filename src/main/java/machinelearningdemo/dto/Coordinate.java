package machinelearningdemo.dto;

/**
 * Created by cdz on 2017/1/16.
 */
public class Coordinate {

    private double lat;
    private double lng;
    private int count;
    private long time;

    public long getTime() {
        return time;
    }

    public Coordinate() {
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Coordinate(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Coordinate(Coordinate c) {
        if (c.getLat() != 0)
            this.lat = c.getLat();
        if (c.getLat() != 0)
            this.lng = c.getLng();
        if (c.getLat() != 0)
            this.time = c.getTime();
        if (c.getLat() != 0)
            this.count = c.getCount();
    }

    public String getKkid() {
        return kkid;
    }

    public void setKkid(String kkid) {
        this.kkid = kkid;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    private String kkid;
}
