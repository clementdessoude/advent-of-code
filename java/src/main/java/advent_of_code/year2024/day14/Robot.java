package advent_of_code.year2024.day14;

import advent_of_code.utils.Location;
import advent_of_code.utils.Pair;

final class Robot {

    private Location location;
    private final int vx;
    private final int vy;
    private final int maxX;
    private final int maxY;

    public Robot(int x, int y, int vx, int vy, Pair<Integer, Integer> roomSize) {
        this.location = new Location(x, y);
        this.vx = vx;
        this.vy = vy;
        this.maxX = roomSize.first();
        this.maxY = roomSize.second();
    }

    public int quadrantAfter(int seconds) {
        move(seconds);
        return quadrant();
    }

    public int quadrant() {
        int limitX = (maxX - 1) / 2;
        int limitY = (maxY - 1) / 2;

        if (location.x() == limitX || location.y() == limitY) {
            return 0;
        }

        if (location.x() < limitX) {
            return location.y() < limitY ? 1 : 2;
        }

        return location.y() < limitY ? 3 : 4;
    }

    Robot move(int seconds) {
        for (int i = 0; i < seconds; i++) {
            move();
        }

        return this;
    }

    void move() {
        int x = (location.x() + vx) % maxX;
        int y = (location.y() + vy) % maxY;

        location = new Location(x < 0 ? x + maxX : x, y < 0 ? y + maxY : y);
    }

    public Location location() {
        return location;
    }
}
