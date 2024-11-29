package advent_of_code.year2022.day7;

import java.util.Deque;
import java.util.LinkedList;

final class Path {

    private final Deque<String> paths = new LinkedList<>();

    Path() {}

    void process(String path) {
        if ("..".equals(path)) {
            paths.pop();
        } else {
            paths.push(path);
        }
    }

    String current() {
        return "/" + String.join("/", paths.reversed());
    }

    String with(String path) {
        if (paths.isEmpty()) {
            return "/" + path;
        }
        return current() + "/" + path;
    }
}
