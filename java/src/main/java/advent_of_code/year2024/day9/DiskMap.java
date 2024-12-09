package advent_of_code.year2024.day9;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public DiskMap compactKeepingFileCoherence() {
        Map<Integer, Integer> startOfFileChunks = new HashMap<>();
        for (int i = 0; i < spaces.size(); i++) {
            if (spaces.get(i) instanceof FileChunk fileChunk) {
                int finalI = i;
                startOfFileChunks.computeIfAbsent(fileChunk.fileId(), k -> finalI);
            }
        }

        var fileIndexes = startOfFileChunks.values().stream().sorted().toList().reversed();
        for (var index : fileIndexes) {
            int fileSize = 0;
            FileChunk currentFile = (FileChunk) spaces.get(index);
            Space fileChunk = currentFile;
            while (fileChunk instanceof FileChunk fc && fc.fileId() == currentFile.fileId()) {
                fileSize++;
                if (index + fileSize >= spaces.size()) {
                    break;
                }
                fileChunk = spaces.get(index + fileSize);
            }

            var firstFreeSpaceOfSizeIndex = firstFreeSpaceOfSizeIndex(fileSize);
            if (firstFreeSpaceOfSizeIndex == -1 || firstFreeSpaceOfSizeIndex >= index) {
                continue;
            }
            for (int i = 0; i < fileSize; i++) {
                Space tmp = spaces.get(index + i);
                spaces.set(index + i, new FreeSpace());
                spaces.set(firstFreeSpaceOfSizeIndex + i, tmp);
            }
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

    private int firstFreeSpaceOfSizeIndex(int size) {
        int availableSpace = 0;
        for (int i = 0; i < spaces.size(); i++) {
            if (spaces.get(i) instanceof FreeSpace) {
                if (availableSpace + 1 >= size) {
                    return i - availableSpace;
                } else {
                    availableSpace++;
                }
            } else {
                availableSpace = 0;
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
