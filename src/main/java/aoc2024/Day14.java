package aoc2024;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class Day14 implements Day {

    @Override
    public long executePart1(String input) {
        TilesMap tilesMap = createMap(input);
        List<Robot> robots = createRobots(input);
        List<Robot> robotsBefore = moveRobots(tilesMap, robots, 100);
        List<Position> positions = robotsBefore.stream().map(Robot::position).toList();
        return tilesMap.countPositionsInsideByQuadrant(positions);
    }

    @Override
    public long executePart2(String input) {
        TilesMap tilesMap = createMap(input);
        List<Robot> robots = createRobots(input);
        return calculateSecondWhenFormIsChristmasTree(tilesMap, robots);
    }

    @SuppressWarnings("SameParameterValue")
    private List<Robot> moveRobots(TilesMap tilesMap, List<Robot> robots, int seconds) {
        List<Robot> newRobots = robots;
        for (int i = 0; i < seconds; i++) {
            newRobots = newRobots.stream()
                    .map(tilesMap::move)
                    .toList();
        }
        return newRobots;
    }

    private int calculateSecondWhenFormIsChristmasTree(TilesMap tilesMap, List<Robot> robots) {
        int second = 0;
        while (!isChristmasTreeForm(tilesMap, robots)) {
            robots = robots.stream()
                    .map(tilesMap::move)
                    .toList();
            second++;
            if (second % 10000 == 0) {
                System.out.println("Second: " + second);
            }
        }
        System.out.println(tilesMap.mapToString(robots).replaceAll("\\d", "#"));

        return second;
    }

    private boolean isChristmasTreeForm(TilesMap tilesMap, List<Robot> robots) {
        String map = tilesMap.mapToString(robots);
        for (String line : map.lines().toList()) {
            line = line.replaceAll("\\d", "#");
            if (areAllHashesContiguous(line, 10)) return true;
        }
        return false;
    }

    @SuppressWarnings("SameParameterValue")
    private boolean areAllHashesContiguous(String line, int count) {
        return line.matches(".*#{" + count + ",}.*");
    }

    private TilesMap createMap(String input) {
        String firstLine = input.lines().findFirst().orElseThrow();
        String[] parts = firstLine.split(",");
        return new TilesMap(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }

    private List<Robot> createRobots(String input) {
        // Implement the logic to create robots
        AtomicInteger robotId = new AtomicInteger();
        return input.lines().skip(1)
                .filter(line -> !line.isBlank())
                .map(line -> {
                    Pattern pattern = Pattern.compile("p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)");
                    var matcher = pattern.matcher(line);
                    boolean b = matcher.find();
                    if (!b) throw new IllegalArgumentException("Invalid input");
                    int x = Integer.parseInt(matcher.group(1));
                    int y = Integer.parseInt(matcher.group(2));
                    int vx = Integer.parseInt(matcher.group(3));
                    int vy = Integer.parseInt(matcher.group(4));
                    return new Robot(robotId.getAndIncrement(), new Position(x, y), new Velocity(vx, vy));
                }).toList();
    }

    static final class TilesMap {
        private final int wide;
        private final int tall;
        private final List<Quadrant> quadrants;

        public TilesMap(int wide, int tall) {
            this.wide = wide;
            this.tall = tall;
            this.quadrants = calculateQuadrants();
        }

        private List<Quadrant> calculateQuadrants() {
            return List.of(
                    new Quadrant(0, 0, wide / 2, tall / 2),
                    new Quadrant(wide / 2 + wide % 2, 0, wide / 2, tall / 2),
                    new Quadrant(0, tall / 2 + tall % 2, wide / 2, tall / 2),
                    new Quadrant(wide / 2 + wide % 2, tall / 2 + tall % 2, wide / 2, tall / 2)
            );
        }

        public long countPositionsInsideByQuadrant(List<Position> positions) {
            return quadrants.stream()
                    .mapToLong(quadrant -> quadrant.countPositionsInside(positions))
                    .reduce(1, (a, b) -> a * b);
        }

        public Robot move(Robot robot) {
            int newX = calculateNewValue(robot.position().x(), robot.velocity().x(), wide);
            int newY = calculateNewValue(robot.position().y(), robot.velocity().y(), tall);
            Position newPosition = new Position(newX, newY);
            return new Robot(robot.id(), newPosition, robot.velocity());
        }

        private static int calculateNewValue(int initial, int increment, int limit) {
            int newVaule = initial + increment;
            if (newVaule < 0) newVaule = limit + newVaule;
            if (newVaule >= limit) newVaule = newVaule - limit;
            return newVaule;
        }

        private Map<Position, Set<Robot>> calculateRobotsByPosition(List<Robot> robots) {
            return robots.stream()
                    .collect(Collectors.groupingBy(Robot::position, Collectors.toSet()));
        }

        String mapToString(List<Robot> robots) {
            Map<Position, Set<Robot>> positionSetMap = calculateRobotsByPosition(robots);
            char[][] map = new char[tall][wide];
            for (int i = 0; i < tall; i++) {
                Arrays.fill(map[i], '.');
            }
            for (Map.Entry<Position, Set<Robot>> entry : positionSetMap.entrySet()) {
                Position position = entry.getKey();
                int number = entry.getValue().size();
                map[position.y()][position.x()] = (char) ('0' + number);
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < tall; i++) {
                for (int j = 0; j < wide; j++) {
                    sb.append(map[i][j]);
                }
                sb.append("\n");
            }
            return sb.toString();
        }
    }

    record Quadrant(int x, int y, int wide, int tall) {
        public boolean isInside(Position position) {
            return position.x() >= x && position.x() < x + wide && position.y() >= y && position.y() < y + tall;
        }

        long countPositionsInside(List<Position> list) {
            return list.stream().filter(this::isInside).count();
        }
    }


    record Robot(int id, Position position, Velocity velocity) {
    }

    record Position(int x, int y) {
    }

    record Velocity(int x, int y) { //Represents a velocity vector
    }
}
