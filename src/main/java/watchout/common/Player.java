package watchout.common;

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

    public Player(watchout.admin.Player player) {
        this(player.getId(), player.getAddress(), player.getPort(), player.getPitchStartX(), player.getPitchStartY());
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

    @Override
    public String toString() {
        return "Player {" +
                "id=" + id +
                ", address='" + address + ':' +
                ", port=" + port +
                ", startX=" + pitchStartX +
                ", startY=" + pitchStartY +
                '}';
    }
}
