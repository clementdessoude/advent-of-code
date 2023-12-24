package adventOfCode.day24;

public record Hail(
    double x,
    double y,
    double z,
    double vx,
    double vy,
    double vz
) {

    boolean willIntersect(Hail other, Area area) {
        double numerator = other.y - this.y - other.vy / other.vx * other.x + this.vy / this.vx * this.x;
        double denominator = this.vy / this.vx - other.vy / other.vx;

        if (denominator == 0) {
            return false;
        }

        double crossedX = numerator / denominator;
        double crossedY = this.y + this.vy / this.vx * (crossedX - this.x);

        if (area.doesNotContains(crossedX, crossedY)) {
            return false;
        }

        return isInFuture(crossedX) && other.isInFuture(crossedX);
    }

    private boolean isInFuture(double crossedX) {
        return (crossedX - x) / vx > 0;
    }
}
