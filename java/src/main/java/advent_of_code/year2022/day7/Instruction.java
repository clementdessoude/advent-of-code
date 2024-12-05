package advent_of_code.year2022.day7;

import java.util.ArrayList;
import java.util.Collection;

interface Instruction {
    record ChangeDirectory(String path) implements Instruction {}

    final class ListFiles implements Instruction {

        private final Collection<DirectoryContent> result = new ArrayList<>();

        void add(DirectoryContent file) {
            result.add(file);
        }

        Collection<DirectoryContent> result() {
            return result;
        }
    }
}
