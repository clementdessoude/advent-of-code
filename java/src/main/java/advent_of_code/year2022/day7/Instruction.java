package advent_of_code.year2022.day7;

import java.util.ArrayList;
import java.util.Collection;

sealed interface Instruction permits Instruction.ChangeDirectory, Instruction.ListFiles {
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
