package adventOfCode.day2;

import static java.lang.Integer.parseInt;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

public class Day2 {
    private static final Pattern GAME_PATTERN = Pattern.compile(
        "^Game (?<gameIndex>\\d+):(?<balls>(\\s(?<ballCount>\\d+)\\s(?<ballColor>(blue|red|green))([,;])?)*)$"
    );
    private static final Pattern BALL_MATCHER = Pattern.compile("(?<ballCount>\\d+)\\s(?<ballColor>(blue|red|green))");

    public Integer part1(List<String> lines) {
        Map<Color, Integer> maximumCubesByColor = Map.of(
            Color.BLUE, 14,
            Color.GREEN, 13,
            Color.RED, 12
        );
        return lines
            .stream()
            .map(Day2::parse)
            .filter(game -> isPossible(game, maximumCubesByColor))
            .mapToInt(Game::gameIndex)
            .sum();
    }

    private static Game parse(String row) {
        var matcher = GAME_PATTERN.matcher(row);
        matcher.matches();

        var gameIndex = parseInt(matcher.group("gameIndex"));
        var ballsMatcher = BALL_MATCHER.matcher(matcher.group("balls"));
        List<Ball> balls = new ArrayList<>();
        while(ballsMatcher.find()) {
            int ballCount = parseInt(ballsMatcher.group("ballCount"));
            String color = ballsMatcher.group("ballColor");
            balls.add(new Ball(ballCount, colorMap.get(color)));
        }

        return new Game(gameIndex, balls);
    }

    private boolean isPossible(Game game, Map<Color, Integer> maximumCubesByColor) {
        return game.balls.stream().allMatch(ball -> maximumCubesByColor.get(ball.color) >= ball.count);
    }

    public Integer part2(List<String> lines) {
        return lines
            .stream()
            .map(Day2::parse)
            .map(Day2::fewestBallCountByColor)
            .mapToInt(mins -> mins.values().stream().mapToInt(t -> t).reduce(1, (a,b) -> a * b))
            .sum();
    }

    private static Map<Color, Integer> fewestBallCountByColor(Game game) {
        Map<Color, Integer> fewestBallCountByColor = new HashMap<>();
        Arrays.stream(Color.values())
              .forEach(color -> fewestBallCountByColor.put(color, 0));

        game.balls.forEach(ball -> {
            var actualMinimum = fewestBallCountByColor.get(ball.color);
            if (actualMinimum < ball.count) {
                fewestBallCountByColor.put(ball.color, ball.count);
            }
        });

        return fewestBallCountByColor;
    }

    private record Ball(int count, Color color) {

    }

    private enum Color {
        BLUE,
        GREEN,
        RED;
    }

    private static Map<String, Color> colorMap = Map.of(
        "blue", Color.BLUE,
        "red", Color.RED,
        "green", Color.GREEN
    );

    private record Game(int gameIndex, List<Ball> balls) {}
}
