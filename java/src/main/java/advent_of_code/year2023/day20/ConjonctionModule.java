package advent_of_code.year2023.day20;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

final class ConjonctionModule implements Receiver {

    private final String name;
    private final List<String> destinationModules;
    private final Map<String, PulseType> recorded = new HashMap<>();

    ConjonctionModule(String name, List<String> destinationModules) {
        this.name = name;
        this.destinationModules = destinationModules;
    }

    public void addInput(String name) {
        recorded.put(name, PulseType.LOW);
    }

    @Override
    public List<Pulse> process(Pulse pulse) {
        recorded.put(pulse.from(), pulse.type());

        if (shouldSendLowPulse()) {
            return sendPulse(PulseType.LOW);
        }

        return sendPulse(PulseType.HIGH);
    }

    private boolean shouldSendLowPulse() {
        return recorded.values().stream().allMatch(PulseType.HIGH::equals);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public List<String> destinationModules() {
        return destinationModules;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (ConjonctionModule) obj;
        return (
            Objects.equals(this.name, that.name) &&
            Objects.equals(this.destinationModules, that.destinationModules) &&
            Objects.equals(this.recorded, that.recorded)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, destinationModules, recorded);
    }

    @Override
    public String toString() {
        return recorded
            .values()
            .stream()
            .map(v -> v == PulseType.LOW ? "0" : "1")
            .collect(Collectors.joining());
    }

    public boolean hasHigh() {
        return recorded.values().stream().anyMatch(v -> v == PulseType.HIGH);
    }
}
