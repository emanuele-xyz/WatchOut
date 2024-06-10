package watchout.common;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HeartbeatStatResult {
    private double result;

    public HeartbeatStatResult() {
        this.result = 0;
    }

    public HeartbeatStatResult(double result) {
        this.result = result;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }
}
