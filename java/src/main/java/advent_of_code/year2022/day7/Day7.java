package advent_of_code.year2022.day7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class Day7 {

    public static final int TOTAL_DISK_SPACE = 70000000;
    public static final int UPDATE_SIZE = 30000000;

    public Long part1(List<String> lines) {
        List<Instruction> instructions = getInstructions(lines);
        Map<String, Directory> directories = getDirectories(instructions);
        Map<String, Long> cachedSize = new ConcurrentHashMap<>();

        return directories
            .values()
            .stream()
            .filter(dir -> dir.isBelow(100000, cachedSize))
            .mapToLong(directory -> directory.size(cachedSize))
            .sum();
    }

    public Long part2(List<String> lines) {
        List<Instruction> instructions = getInstructions(lines);
        Map<String, Directory> directories = getDirectories(instructions);
        Map<String, Long> cachedSize = new ConcurrentHashMap<>();

        var usedSpace = directories.get("/").size(cachedSize);
        var freeSpace = TOTAL_DISK_SPACE - usedSpace;
        var spaceToFree = UPDATE_SIZE - freeSpace;

        return directories
            .values()
            .stream()
            .mapToLong(directory -> directory.size(cachedSize))
            .filter(size -> size >= spaceToFree)
            .min()
            .orElseThrow();
    }

    private static List<Instruction> getInstructions(List<String> lines) {
        List<Instruction> instructions = new ArrayList<>();
        Instruction.ListFiles currentListFileInstruction = new Instruction.ListFiles();
        boolean isLs = false;
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.startsWith("$")) {
                if (line.startsWith("$ ls")) {
                    isLs = true;
                    currentListFileInstruction = new Instruction.ListFiles();
                } else {
                    if (isLs) {
                        instructions.add(currentListFileInstruction);
                    }
                    isLs = false;
                    String[] split = line.split(" ");
                    instructions.add(new Instruction.ChangeDirectory(split[split.length - 1]));
                }
            } else {
                String[] split = line.split(" ");
                if (line.startsWith("dir")) {
                    currentListFileInstruction.add(new Directory(split[1]));
                } else {
                    String name = split[1];
                    long size = Long.parseLong(split[0]);
                    currentListFileInstruction.add(new File(name, size));
                }
            }
        }
        instructions.add(currentListFileInstruction);
        return instructions;
    }

    private static Map<String, Directory> getDirectories(List<Instruction> instructions) {
        Map<String, Directory> directories = new HashMap<>();
        Path currentPath = new Path();
        for (Instruction instruction : instructions) {
            switch (instruction) {
                case Instruction.ChangeDirectory cd -> {
                    currentPath.process(cd.path());
                }
                case Instruction.ListFiles ls -> {
                    directories.putIfAbsent(
                        currentPath.current(),
                        new Directory(currentPath.current())
                    );
                    var files = ls.result().stream().filter(File.class::isInstance).toList();
                    var innerDirectories = ls
                        .result()
                        .stream()
                        .filter(Directory.class::isInstance)
                        .map(dir -> {
                            String innerDirectoryPath = currentPath.with(((Directory) dir).path());
                            directories.putIfAbsent(
                                innerDirectoryPath,
                                new Directory(innerDirectoryPath)
                            );
                            return directories.get(innerDirectoryPath);
                        })
                        .toList();
                    directories.get(currentPath.current()).addContent(files);
                    directories.get(currentPath.current()).addContent(innerDirectories);
                }
                default -> throw new IllegalStateException("Unexpected value: " + instruction);
            }
        }
        return directories;
    }
}
