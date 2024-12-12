package advent_of_code.year2024.day12;

import java.util.List;

class Day12 {

    Long part1(List<String> lines) {
        Garden garden = new Garden(lines);
        return garden.regions().stream().mapToLong(Region::fencePrice).sum();
    }

    Long part2(List<String> lines) {
        Garden garden = new Garden(lines);
        return garden.regions().stream().mapToLong(Region::fencePriceWithBulkDiscount).sum();
    }
}
