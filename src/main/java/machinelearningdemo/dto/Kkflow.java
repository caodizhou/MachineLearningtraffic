package machinelearningdemo.dto;

/**
 * Created by cdz on 2017/6/12.
 */
public class Kkflow {
    private String kkid;
    private double[] flow;
    private double[] avgSpeed;

    public Kkflow() {
    }

    public Kkflow(String kkid, double[] flow) {
        this.kkid = kkid;
        this.flow = flow;
    }

    public Kkflow(String kkid, double flow[], double[] avgSpeed) {
        this.kkid = kkid;
        this.flow = flow;
        this.avgSpeed = avgSpeed;
    }

    public double[] todoublearray(){
        return flow;
    }

    public String getKkid() {
        return kkid;
    }

    public void setKkid(String kkid) {
        this.kkid = kkid;
    }

    public double[] getFlow() {
        return flow;
    }

    public void setFlow(double[] flow) {
        this.flow = flow;
    }

    public double[] getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(double[] avgSpeed) {
        this.avgSpeed = avgSpeed;
    }
}
