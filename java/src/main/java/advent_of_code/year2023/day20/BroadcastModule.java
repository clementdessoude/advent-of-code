package advent_of_code.year2023.day20;

import java.util.List;

record BroadcastModule(
    String name,
    List<String> destinationModules
) implements Receiver {

    @Override
    public List<Pulse> process(Pulse pulse) {
        return sendPulse(pulse.type());
    }
}
