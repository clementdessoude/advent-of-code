package adventOfCode.day16;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

final class Grid {
    private final List<String> lines;
    private final BeamDirections beamDirections = new BeamDirections();

    Grid(List<String> lines) {
        this.lines = lines.stream().filter(s -> !s.isBlank()).toList();
    }

    private char charAt(int x, int y) {
        return lines.get(x).charAt(y);
    }

    private char charAt(Position position) {
        return charAt(position.x(), position.y());
    }

    int lineNumber() {
        return lines.size();
    }

    int columnNumber() {
        return lines.get(0).length();
    }

    public Long energizedCount() {
        solve();

        return IntStream
            .range(0, lines.size())
            .mapToLong(i -> IntStream.range(0, lines.get(0).length())
                                     .map(j -> beamDirections
                                         .get(new Position(i, j))
                                         .isEmpty() ? 0 : 1)
                                     .sum()
            ).sum();
    }

    record WaitingBeam(Position position, BeamDirection direction) {
        Optional<Position> next(Grid grid) {
            return position.next(direction, grid);
        }
    }

    void solve() {
        Queue<WaitingBeam> waitingBeams = new LinkedList<>();
        Position startupPosition = new Position(0, -1);
        waitingBeams.add(new WaitingBeam(startupPosition, BeamDirection.RIGHT));
        beamDirections.add(startupPosition, BeamDirection.RIGHT);

        while (!waitingBeams.isEmpty()) {
            WaitingBeam waitingBeam = waitingBeams.poll();

            Optional<Position> next = waitingBeam.next(this);
            while (next.isPresent()) {
                Position nextPosition = next.get();
                var obj = charAt(nextPosition);
                if (obj == '.') {
                    boolean hasBeenAdded = beamDirections.add(
                        nextPosition,
                        waitingBeam.direction()
                    );
                    if (hasBeenAdded) {
                        next = nextPosition.next(waitingBeam.direction, this);
                    } else {
                        next = Optional.empty();
                    }
                    continue;
                }

                if (obj == '-') {
                    if (waitingBeam.direction.isHorizontal()) {
                        boolean hasBeenAdded = beamDirections.add(
                            nextPosition,
                            waitingBeam.direction()
                        );
                        if (hasBeenAdded) {
                            next = nextPosition.next(
                                waitingBeam.direction,
                                this
                            );
                        } else {
                            next = Optional.empty();
                        }
                        continue;
                    }

                    boolean hasBeenAddedLeft = beamDirections.add(
                        nextPosition,
                        BeamDirection.LEFT
                    );
                    if (hasBeenAddedLeft) {
                        waitingBeams.add(new WaitingBeam(
                            nextPosition,
                            BeamDirection.LEFT
                        ));
                    }

                    boolean hasBeenAddedRight = beamDirections.add(
                        nextPosition,
                        BeamDirection.RIGHT
                    );
                    if (hasBeenAddedRight) {
                        waitingBeams.add(new WaitingBeam(
                            nextPosition,
                            BeamDirection.RIGHT
                        ));
                    }

                    next = Optional.empty();
                    continue;
                }

                if (obj == '|') {
                    if (waitingBeam.direction.isHorizontal()) {
                        boolean hasBeenAddedUp = beamDirections.add(
                            nextPosition,
                            BeamDirection.UP
                        );
                        if (hasBeenAddedUp) {
                            waitingBeams.add(new WaitingBeam(
                                nextPosition,
                                BeamDirection.UP
                            ));
                        }

                        boolean hasBeenAddedDown = beamDirections.add(
                            nextPosition,
                            BeamDirection.DOWN
                        );
                        if (hasBeenAddedDown) {
                            waitingBeams.add(new WaitingBeam(
                                nextPosition,
                                BeamDirection.DOWN
                            ));
                        }

                        next = Optional.empty();
                        continue;
                    }

                    boolean hasBeenAdded = beamDirections.add(
                        nextPosition,
                        waitingBeam.direction()
                    );
                    if (hasBeenAdded) {
                        next = nextPosition.next(waitingBeam.direction, this);
                    } else {
                        next = Optional.empty();
                    }
                    continue;
                }

                if (obj == '\\') {
                    boolean hasBeenAdded = beamDirections.add(
                        nextPosition,
                        waitingBeam.direction()
                    );
                    if (hasBeenAdded) {
                        var newBeamDirection = switch (waitingBeam.direction()) {
                            case UP -> BeamDirection.LEFT;
                            case DOWN -> BeamDirection.RIGHT;
                            case LEFT -> BeamDirection.UP;
                            case RIGHT -> BeamDirection.DOWN;
                        };
                        waitingBeam = new WaitingBeam(
                            nextPosition,
                            newBeamDirection
                        );
                        next = nextPosition.next(newBeamDirection, this);
                    } else {
                        next = Optional.empty();
                    }
                    continue;
                }

                if (obj == '/') {
                    boolean hasBeenAdded = beamDirections.add(
                        nextPosition,
                        waitingBeam.direction()
                    );
                    if (hasBeenAdded) {
                        var newBeamDirection = switch (waitingBeam.direction()) {
                            case UP -> BeamDirection.RIGHT;
                            case DOWN -> BeamDirection.LEFT;
                            case LEFT -> BeamDirection.DOWN;
                            case RIGHT -> BeamDirection.UP;
                        };
                        waitingBeam = new WaitingBeam(
                            nextPosition,
                            newBeamDirection
                        );
                        next = nextPosition.next(newBeamDirection, this);
                    } else {
                        next = Optional.empty();
                    }
                    continue;
                }

                next = Optional.empty();
            }
        }
    }

    List<String> showDirections() {
        return IntStream.range(0, lines.size())
                        .mapToObj(i -> IntStream
                            .range(0, lines.get(0).length())
                            .mapToObj(j -> {
                                if (charAt(i, j) != '.') {
                                    return String.valueOf(charAt(i, j));
                                }
                                Set<BeamDirection> directions = beamDirections.get(
                                    new Position(i, j));
                                if (directions.size() > 1) {
                                    return String.valueOf(directions.size());
                                }
                                if (directions.isEmpty()) {
                                    return ".";
                                }

                                BeamDirection beamDirection = directions
                                    .stream()
                                    .findAny()
                                    .get();
                                return switch (beamDirection) {
                                    case UP -> "^";
                                    case DOWN -> "v";
                                    case LEFT -> "<";
                                    case RIGHT -> ">";
                                };
                            })
                            .collect(Collectors.joining()))
                        .toList();
    }

    List<String> showEnergized() {
        return IntStream.range(0, lines.size())
                        .mapToObj(i -> IntStream
                            .range(0, lines.get(0).length())
                            .mapToObj(j -> {
                                Set<BeamDirection> directions = beamDirections.get(
                                    new Position(i, j));
                                if (directions.isEmpty()) {
                                    return ".";
                                }

                                return "#";
                            })
                            .collect(Collectors.joining()))
                        .toList();
    }
}
