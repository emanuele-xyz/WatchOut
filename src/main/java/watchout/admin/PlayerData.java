package watchout.admin;

import watchout.Pitch;

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

    public PlayerData(int id, String address, int port) {
        this(id, address, port, 0, 0);
        if (Math.random() < 0.5) {
            this.pitchStartX = Pitch.getRandomStartingPitchCoordinate();
        } else {
            this.pitchStartY = Pitch.getRandomStartingPitchCoordinate();
        }
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
