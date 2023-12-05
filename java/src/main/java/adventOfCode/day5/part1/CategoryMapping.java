package adventOfCode.day5.part1;

import java.util.ArrayList;
import java.util.List;

record CategoryMapping(String destination, List<Mapping> mappings) {
    public CategoryMapping(String destination) {
        this(destination, new ArrayList<>());
    }

    public void addMapping(Mapping mapping) {
        mappings.add(mapping);
    }
}
