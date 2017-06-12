package machinelearningdemo.dto;

/**
 * Created by cdz on 2017/6/12.
 */
public class Kkflow {
    private String kkid;
    private int flow;

    public Kkflow() {
    }

    public Kkflow(String kkid, int flow) {
        this.kkid = kkid;
        this.flow = flow;
    }

    public String getKkid() {
        return kkid;
    }

    public void setKkid(String kkid) {
        this.kkid = kkid;
    }

    public int getFlow() {
        return flow;
    }

    public void setFlow(int flow) {
        this.flow = flow;
    }
}
