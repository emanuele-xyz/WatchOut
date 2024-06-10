package watchout.admin;

import watchout.Pitch;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Player {
    private int id;
    private String address;
    private int port;
    private int pitchStartX;
    private int pitchStartY;

    public Player() {}

    public Player(int id, String address, int port, int pitchStartX, int pitchStartY) {
        this.id = id;
        this.address = address;
        this.port = port;
        this.pitchStartX = pitchStartX;
        this.pitchStartY = pitchStartY;
    }

    public Player(int id, String address, int port) {
        this(id, address, port, 0, 0);
        // NOTE: coin toss for deciding whether x or y will be "fixed"
        if (Math.random() < 0.5) {
            // NOTE: fix x
            // NOTE: coin toss for deciding whether x should be 0 or (Pitch.Side - 1)
            this.pitchStartX = Math.random() < 0.5 ? 0 : (Pitch.SIDE - 1);
            this.pitchStartY = Pitch.getRandomStartingPitchCoordinate();
        } else {
            this.pitchStartX = Pitch.getRandomStartingPitchCoordinate();
            // NOTE: fix y
            // NOTE: coin toss for deciding whether y should be 0 or (Pitch.Side - 1)
            this.pitchStartY = Math.random() < 0.5 ? 0 : (Pitch.SIDE - 1);
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
