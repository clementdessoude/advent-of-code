package advent_of_code.year2023.day20;

import java.util.List;
import java.util.Objects;

final class FlipFlop implements Receiver {

    private final String name;
    private final List<String> destinationModules;
    public boolean isOn;

    FlipFlop(String name, List<String> destinationModules, boolean isOn) {
        this.name = name;
        this.destinationModules = destinationModules;
        this.isOn = isOn;
    }

    @Override
    public List<Pulse> process(Pulse pulse) {
        if (pulse.type() == PulseType.HIGH) {
            return List.of();
        }

        PulseType newPulseType = isOn ? PulseType.LOW : PulseType.HIGH;
        isOn = !isOn;

        return sendPulse(newPulseType);
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
        var that = (FlipFlop) obj;
        return (
            Objects.equals(this.name, that.name) &&
            Objects.equals(this.destinationModules, that.destinationModules) &&
            this.isOn == that.isOn
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, destinationModules, isOn);
    }

    @Override
    public String toString() {
        return name + ": " + isOn;
    }
}
