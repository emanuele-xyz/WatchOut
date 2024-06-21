package watchout.player;

import watchout.simulators.Buffer;
import watchout.simulators.Measurement;

import java.util.ArrayList;
import java.util.List;

public class HRBuffer implements Buffer {
    private static final int WINDOW_SIZE = 8;
    private static final int WINDOW_OVERLAP = 4;

    private final List<Measurement> measurements;
    private final List<Measurement> averages;

    public HRBuffer() {
        measurements = new ArrayList<>();
        averages = new ArrayList<>();
    }

    @Override
    public void addMeasurement(Measurement m) {
        synchronized (measurements) {
            measurements.add(m);
            if (measurements.size() == WINDOW_SIZE) {
                double avgValue = measurements.stream().mapToDouble(Measurement::getValue).average().getAsDouble();
                long avgTimestamp = (long) measurements.stream().mapToLong(Measurement::getTimestamp).average().getAsDouble();
                synchronized (averages) {
                    averages.add(new Measurement("NA", "NA", avgValue, avgTimestamp));
                }
                measurements.subList(0, WINDOW_OVERLAP).clear();
            }
        }
    }

    @Override
    public List<Measurement> readAllAndClean() {
        synchronized (averages) {
            List<Measurement> out = new ArrayList<>(averages);
            averages.clear();
            return out;
        }
    }
}
