package advent_of_code.year2020.day12;

import advent_of_code.utils.Location;

import java.util.List;

class Ferry {
    private Location location = new Location(0, 0);
    private Location waypoint = new Location(10, -1);
    private int direction = 0;
    private final List<Instruction> instructions;

    Ferry(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    void run() {
        for (Instruction instruction : instructions) {
            run(instruction);
        }
    }

    private void run(Instruction instruction) {
        switch (instruction) {
            case North(int value) -> location = location.up(value);
            case South(int value) -> location = location.down(value);
            case East(int value) -> location = location.right(value);
            case West(int value) -> location = location.left(value);
            case Left(int value) -> direction += value;
            case Right(int value) -> direction -= value;
            case Forward(int value) -> forward(value);
        }
    }

    private void forward(int value) {
        int angle = ((direction % 360) + 360) % 360;
        if (angle == 0) {
            location = location.right(value);
        } else if (angle == 90) {
            location = location.up(value);
        } else if (angle == 180) {
            location = location.left(value);
        } else if (angle == 270) {
            location = location.down(value);
        }
    }

    void runPart2() {
        for (Instruction instruction : instructions) {
//            System.out.println(location);
//            System.out.println("waypoint: " + waypoint);
//            System.out.println(instruction);
            runPart2(instruction);
        }
//        System.out.println(location);
    }

    private void runPart2(Instruction instruction) {
        switch (instruction) {
            case North(int value) -> waypoint = waypoint.up(value);
            case South(int value) -> waypoint = waypoint.down(value);
            case East(int value) -> waypoint = waypoint.right(value);
            case West(int value) -> waypoint = waypoint.left(value);
            case Left(int value) -> rotateWayPoint(value);
            case Right(int value) -> rotateWayPoint(360 - value);
            case Forward(int value) -> forwardPart2(value);
        }
    }

    private void forwardPart2(int value) {
        location = location.up(-value * waypoint.y()).right(value * waypoint.x());
    }

    private void rotateWayPoint(int angle) {
        if (angle == 90) {
            waypoint = new Location(waypoint.y(), -waypoint.x());
        } else if (angle == 180) {
            waypoint = new Location(-waypoint.x(), -waypoint.y());
        } else if (angle == 270) {
            waypoint = new Location(-waypoint.y(), waypoint.x());
        }
    }

    long distance() {
        return (long) Math.abs(location.x()) + Math.abs(location.y());
    }
}
