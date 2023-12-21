
package adventOfCode.day20;

import java.util.*;
import java.util.stream.Collectors;

public class Day20 {

    public Long part1(List<String> lines) {
        Map<String, Receiver> receivers = lines
            .stream()
            .map(Day20::parseRow)
            .collect(Collectors.toMap(
                Receiver::name,
                receiver -> receiver
            ));

        receivers
            .values()
            .forEach(receiver -> {
                for (var destination: receiver.destinationModules()) {
                    if (receivers.get(destination) instanceof ConjonctionModule conjonctionModule) {
                        conjonctionModule.addInput(receiver.name());
                    }
                }
            });

        Map<PulseType, Long> pulseTypeCount = new HashMap<>(2);
        pulseTypeCount.put(PulseType.LOW, 0L);
        pulseTypeCount.put(PulseType.HIGH, 0L);

        int startingHashcode = receivers.hashCode();
        pushButton(pulseTypeCount, receivers);

        long cycle = 1;
        while (receivers.hashCode() != startingHashcode && cycle < 1000) {
            System.out.println("Pushed " + cycle);

            pushButton(pulseTypeCount, receivers);
            cycle++;
        }
        System.out.println("Pushed " + cycle);

        long factor = 1000L / cycle;
        return factor * factor * pulseTypeCount.get(PulseType.LOW) * pulseTypeCount.get(PulseType.HIGH);
    }

    private static boolean pushButton(
        Map<PulseType, Long> pulseTypeCount,
        Map<String, Receiver> receivers
    ) {
        Queue<Pulse> pulses = new LinkedList<>();
        pulses.add(new Pulse("button", "broadcaster", PulseType.LOW));

        long countPulseSentToRx = 0L;
        while (!pulses.isEmpty()) {
            Pulse currentPulse = pulses.poll();
            pulseTypeCount.put(currentPulse.type(), pulseTypeCount.get(currentPulse.type()) + 1);

            if (currentPulse.destination().equals("rx") && currentPulse.type() == PulseType.LOW) {
                countPulseSentToRx++;
            }
            Receiver receiver = receivers
                .get(currentPulse.destination());

            if (receiver == null) {
                continue;
            }
            List<Pulse> newPulses = receiver.process(currentPulse);
            pulses.addAll(newPulses);
        }

        return countPulseSentToRx == 1;
    }

    private static Receiver parseRow(String row) {
        String[] split = row.split(" -> ");
        String code = split[0];
        List<String> destinationModules = Arrays.stream(split[1].split(", ")).toList();

        if (code.equals("broadcaster")) {
            return new BroadcastModule(code, destinationModules);
        }

        if (code.charAt(0) == '%') {
            return new FlipFlop(code.substring(1), destinationModules, false);
        }

        return new ConjonctionModule(code.substring(1), destinationModules);
    }

    public Long part2(List<String> lines) {
        Map<String, Receiver> receivers = lines
            .stream()
            .map(Day20::parseRow)
            .collect(Collectors.toMap(
                Receiver::name,
                receiver -> receiver
            ));

        receivers
            .values()
            .forEach(receiver -> {
                for (var destination: receiver.destinationModules()) {
                    if (receivers.get(destination) instanceof ConjonctionModule conjonctionModule) {
                        conjonctionModule.addInput(receiver.name());
                    }
                }
            });

        Map<PulseType, Long> pulseTypeCount = new HashMap<>(2);
        pulseTypeCount.put(PulseType.LOW, 0L);
        pulseTypeCount.put(PulseType.HIGH, 0L);

        Map<String, List<String>> states = new HashMap<>(receivers.size());

        List<FlipFlop> list = receivers
            .values()
            .stream()
            .filter(receiver -> receiver instanceof FlipFlop)
//            .filter(receiver -> Set.of("lm", "nf", "jd", "nk").contains(receiver.name()))
            .map(e -> (FlipFlop) e)
            .toList();


        System.out.println(list
                               .stream()
                               .map(m -> m.name())
                               .collect(Collectors.joining(",")));

        long cycle = 1;
        while (!pushButton(pulseTypeCount, receivers)) {
//            for (var module: receivers.values()) {
//                states.putIfAbsent(module.name(), new ArrayList<>());
//                states.get(module.name()).add(module.toString());
//            }
//            boolean b = list
//                .stream().allMatch(flipFlop -> flipFlop.isOn);

                var printed = list
                    .stream()
                    .mapToInt(m -> m.isOn ? 1 : 0)
                    .sum();

            if (printed > 44) {
//                System.out.println(printed);
                System.out.println(cycle);
            }
            cycle++;
        }

        // lm	nf	jd	nk

        // rz, mr, kv, jg => 1 always
        // dr, qz => 4
        // qb => 0
        // sk => 7
        // sv => 2


        return cycle;
    }

    public long test() {
        List<Long> ints = List.of(2025L,1863L,1691L,1955L);

        for (long i = 1; i < 1_000_000_000; i++) {
            long value = 2048 * i;
            boolean b = ints
                .stream()
                .allMatch((Long v) -> {
                    long p = findP(value, v);

                    long l = p * (2048 + v) - v;
                    return value > l;
                });

            if (b) {
                return value;
            }
        }

        return -1;
    }

    public long findP(long i, long value) {
        long l = i / (2048 + value);

        return i % (2048 + value) == 0 ? l : l + 1;
    }
}

