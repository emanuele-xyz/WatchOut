package watchout;

import java.util.Arrays;

public class Pitch {
    public static final int SIDE = 10;
    public static final int[] HOME_BASE_COORDS = {4, 5};

    public static int getRandomStartingPitchCoordinate() {
        return (int) (Math.random() * Pitch.SIDE);
    }

    public static double getDistance(int x0, int y0, int x1, int y1) {
        return Math.sqrt((x0 - x1) * (x0 - x1) + (y0 - y1) * (y0 - y1));
    }

    public static double getDistanceFromHomeBase(int x, int y) {
        return Arrays.stream(HOME_BASE_COORDS)
                .asDoubleStream()
                .flatMap(a -> Arrays.stream(HOME_BASE_COORDS).mapToDouble(b -> getDistance(x, y, (int)a, b)))
                .min()
                .getAsDouble();
    }
}
