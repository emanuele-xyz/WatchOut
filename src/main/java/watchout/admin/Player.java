package watchout.admin;

import watchout.utils.Pitch;

public class Player {
    private final int id;
    private final String address;
    private final int port;
    private final int pitchStartX;
    private final int pitchStartY;

    public Player(int id, String address, int port) {
        this.id = id;
        this.address = address;
        this.port = port;
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

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public int getPitchStartX() {
        return pitchStartX;
    }

    public int getPitchStartY() {
        return pitchStartY;
    }
}
