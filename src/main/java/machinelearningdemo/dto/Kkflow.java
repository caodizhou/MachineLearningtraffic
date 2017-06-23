package machinelearningdemo.dto;

/**
 * Created by cdz on 2017/6/12.
 */
public class Kkflow {
    private String kkid;
    private int[] flow;
    private double[] avgSpeed;

    public Kkflow() {
    }

    public Kkflow(String kkid, int[] flow) {
        this.kkid = kkid;
        this.flow = flow;
    }

    public Kkflow(String kkid, int flow[], double[] avgSpeed) {
        this.kkid = kkid;
        this.flow = flow;
        this.avgSpeed = avgSpeed;
    }


    public String getKkid() {
        return kkid;
    }

    public void setKkid(String kkid) {
        this.kkid = kkid;
    }

    public int[] getFlow() {
        return flow;
    }

    public void setFlow(int[] flow) {
        this.flow = flow;
    }

    public double[] getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(double[] avgSpeed) {
        this.avgSpeed = avgSpeed;
    }
}
