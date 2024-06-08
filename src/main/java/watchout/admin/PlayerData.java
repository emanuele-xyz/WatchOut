package watchout.admin;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PlayerData {
    private int id;
    private String address;
    private int port;
    private int pitchStartX;
    private int pitchStartY;

    public PlayerData() {}

    public PlayerData(int id, String address, int port, int pitchStartX, int pitchStartY) {
        this.id = id;
        this.address = address;
        this.port = port;
        this.pitchStartX = pitchStartX;
        this.pitchStartY = pitchStartY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPitchStartX() {
        return pitchStartX;
    }

    public void setPitchStartX(int pitchStartX) {
        this.pitchStartX = pitchStartX;
    }

    public int getPitchStartY() {
        return pitchStartY;
    }

    public void setPitchStartY(int pitchStartY) {
        this.pitchStartY = pitchStartY;
    }
}
