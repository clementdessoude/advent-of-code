package advent_of_code.year2024.day9;

import java.util.ArrayList;
import java.util.List;

final class DiskMap {

    private final List<Space> spaces = new ArrayList<>();

    public DiskMap(List<Integer> memoryRepresentation) {
        int fileId = 0;
        for (int i = 0; i < memoryRepresentation.size(); i++) {
            var isFreeSpace = i % 2 == 1;
            if (isFreeSpace) {
                for (int j = 0; j < memoryRepresentation.get(i); j++) {
                    spaces.add(new FreeSpace());
                }
            } else {
                for (int j = 0; j < memoryRepresentation.get(i); j++) {
                    spaces.add(new FileChunk(fileId));
                }
                fileId++;
            }
        }
    }

    public Long checksum() {
        long result = 0L;
        for (int i = 0; i < spaces.size(); i++) {
            var space = spaces.get(i);
            if (space instanceof FileChunk fileChunk) {
                result += i * fileChunk.fileId();
            }
        }

        return result;
    }

    public DiskMap compact() {
        int firstFreeSpaceIndex = firstFreeSpaceIndex();
        int spaceToProcess = spaces.size() - 1;

        while (spaceToProcess > firstFreeSpaceIndex) {
            if (spaces.get(spaceToProcess) instanceof FileChunk) {
                var space = spaces.remove(spaceToProcess);
                spaces.set(firstFreeSpaceIndex, space);
                firstFreeSpaceIndex = firstFreeSpaceIndex(firstFreeSpaceIndex);
            }
            spaceToProcess--;
        }

        return this;
    }

    private int firstFreeSpaceIndex() {
        return firstFreeSpaceIndex(0);
    }

    private int firstFreeSpaceIndex(int start) {
        for (int i = start; i < spaces.size(); i++) {
            if (spaces.get(i) instanceof FreeSpace) {
                return i;
            }
        }

        return -1;
    }

    public String toString() {
        return String.join(
            "",
            spaces
                .stream()
                .map(s -> {
                    if (s instanceof FileChunk fileChunk) {
                        return String.valueOf(fileChunk.fileId());
                    } else {
                        return ".";
                    }
                })
                .toList()
        );
    }
}
