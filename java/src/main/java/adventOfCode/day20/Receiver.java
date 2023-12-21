package adventOfCode.day20;

import java.util.List;

interface Receiver {
    List<String> destinationModules();
    String name();
    List<Pulse> process(Pulse pulse);

    default List<Pulse> sendPulse(PulseType type) {
        return destinationModules()
            .stream()
            .map(destinationName -> new Pulse(name(), destinationName, type))
            .toList();
    }
}
