package advent_of_code.year2023.day17;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

final class Position {

    private final int i;
    private final int j;
    private final int heatLossToGetThere;
    private HeatLossPath heatLossPath;

    Position(int i, int j, int heatLossToGetThere) {
        this.i = i;
        this.j = j;
        this.heatLossToGetThere = heatLossToGetThere;
        this.heatLossPath = new HeatLossPath(Long.MAX_VALUE, new LinkedList<>());
    }

    public int i() {
        return i;
    }

    public int j() {
        return j;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (Position) obj;
        return this.i == that.i && this.j == that.j;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }

    public int heatLossToGetThere() {
        return heatLossToGetThere;
    }

    //    public boolean canGo(Direction direction) {
    //        return heatLossPath.canGo(direction);
    //    }

    public Direction direction(Position from) {
        if (i == from.i()) {
            if (j > from.j) {
                return Direction.RIGHT;
            } else {
                return Direction.LEFT;
            }
        } else {
            if (i > from.i) {
                return Direction.DOWN;
            } else {
                return Direction.UP;
            }
        }
    }

    record HeatLossPath(long heatLoss, List<Position> path) {
        //        boolean canGo(Direction other) {
        //            if (direction == other.opposite()) {
        //                return false;
        //            }
        //            if (times == 3) {
        //                return direction != other;
        //            }
        //            return true;
        //        }
    }
}
