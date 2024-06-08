package watchout;

public class Pitch {
    public static final int SIDE = 10;

    public static int getRandomStartingPitchCoordinate() {
        return (int) (Math.random() * Pitch.SIDE);
    }
}
