package advent_of_code.year2022.day7;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

final class Directory implements DirectoryContent {

    private final String path;
    private final Collection<DirectoryContent> files;

    private Directory(String path, Collection<DirectoryContent> files) {
        this.path = path;
        this.files = files;
    }

    Directory(String path) {
        this(path, new ArrayList<>());
    }

    public long size(Map<String, Long> cachedSize) {
        if (cachedSize.containsKey(path)) {
            return cachedSize.get(path);
        }

        long size = files
            .stream()
            .mapToLong(content ->
                switch (content) {
                    case Directory dir -> cachedSize.getOrDefault(dir.path, dir.size(cachedSize));
                    case File file -> file.size();
                    default -> throw new IllegalStateException("Unexpected value: " + content);
                }
            )
            .sum();
        cachedSize.put(path, size);
        return size;
    }

    boolean isBelow(long size, Map<String, Long> cachedSize) {
        return size(cachedSize) <= size;
    }

    void addContent(Collection<? extends DirectoryContent> files) {
        this.files.addAll(files);
    }

    public String path() {
        return path;
    }

    public boolean isAbove(long size, Map<String, Long> cachedSize) {
        return size(cachedSize) >= size;
    }
}
