package watchout.player;

public class Context {
    private static Context instance;

    public synchronized static Context getInstance() {
        if (instance == null) {
            instance = new Context();
        }
        return instance;
    }

    private int id;
    private int port;
    private int pitchStartX;
    private int pitchStartY;

    private Context() {}

    public synchronized int getId() {
        return id;
    }

    public synchronized void setId(int id) {
        this.id = id;
    }

    public synchronized int getPort() {
        return port;
    }

    public synchronized void setPort(int port) {
        this.port = port;
    }

    public synchronized int getPitchStartX() {
        return pitchStartX;
    }

    public synchronized void setPitchStartX(int pitchStartX) {
        this.pitchStartX = pitchStartX;
    }

    public synchronized int getPitchStartY() {
        return pitchStartY;
    }

    public synchronized void setPitchStartY(int pitchStartY) {
        this.pitchStartY = pitchStartY;
    }
}
