package aoc2024;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day15 implements Day {
    @Override
    public long executePart1(String input) {
        Warehouse warehouse = createWarehouse(input, 1);
        warehouse.moveRobot();
        return warehouse.sumGpsBoxes();
    }

    @Override
    public long executePart2(String input) {
        Warehouse warehouse = createWarehouse(input, 2);
        System.out.println(warehouse.map());
        warehouse.moveRobot();
        return warehouse.sumGpsBoxes();
    }

    private Warehouse createWarehouse(String input, int wide) {
        Set<Wall> wall = new HashSet<>();
        Set<Box> boxes = new HashSet<>();
        Robot robot = null;
        boolean emptyLine = false;
        List<String> lines = input.lines().toList();
        int width = lines.getFirst().length();
        int y = 0;
        Iterator<String> iterator = lines.iterator();
        AtomicInteger boxId = new AtomicInteger(1);
        while (iterator.hasNext() && !emptyLine) {
            String line = iterator.next();
            if (line.isBlank()) {
                emptyLine = true;
            } else {
                robot = parseMapLine(boxId, y, line, wall, boxes, robot, wide).orElse(robot);
                y++;
            }
        }
        int height = y;

        List<Direction> movements = new LinkedList<>();
        while (iterator.hasNext()) {
            String line = iterator.next();
            if (line.isBlank()) break;
            movements.addAll(parseMovements(line));
        }
        return new Warehouse(wide, height, width * wide, wall, boxes, robot, movements);
    }

    private static Optional<Robot> parseMapLine(AtomicInteger boxId, int y, String line, Set<Wall> wall, Set<Box> boxes, Robot robot, int wide) {
        for (int x = 0; x < line.length(); x++) {
            char c = line.charAt(x);
            if (c == '#') {
                for (int w = 0; w < wide; w++) {
                    wall.add(new Wall(x * wide + w, y));
                }
            } else if (c == 'O') {
                boxes.add(new Box(boxId.getAndIncrement(), x * wide, y, wide));
            } else if (c == '@') {
                robot = new Robot(x * wide, y);
            }
        }
        return Optional.ofNullable(robot);
    }

    private Collection<Direction> parseMovements(String line) {
        List<Direction> directions = new LinkedList<>();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == 'v') {
                directions.add(Direction.DOWN);
            } else if (c == '^') {
                directions.add(Direction.UP);
            } else if (c == '>') {
                directions.add(Direction.RIGHT);
            } else if (c == '<') {
                directions.add(Direction.LEFT);
            }
        }
        return directions;
    }

    static final class Warehouse {
        private final int wide;
        private final int height;
        private final int width;
        private final Set<Wall> wall;
        private final Set<Box> boxes;
        private Robot robot;
        private final List<Direction> movements;

        Warehouse(int wide, int height, int width, Set<Wall> wall, Set<Box> boxes, Robot robot, List<Direction> movements) {
            this.wide = wide;
            this.height = height;
            this.width = width;
            this.wall = wall;
            this.boxes = boxes;
            this.robot = robot;
            this.movements = movements;
        }

        String map() {
            char[][] map = new char[height][width];
            for (char[] row : map) {
                Arrays.fill(row, '.');
            }
            for (Wall w : wall) {
                map[w.y()][w.x()] = '#';
            }
            for (Box b : boxes) {
                if (wide == 1) map[b.y()][b.x()] = 'O';
                else if (wide == 2) {
                    map[b.y()][b.x()] = '[';
                    map[b.y()][b.x() + 1] = ']';
                } else {
                    throw new IllegalArgumentException("Wide not supported :" + wide);
                }
            }
            map[robot.y()][robot.x()] = '@';
            return Arrays.stream(map).map(String::new).collect(Collectors.joining("\n"));
        }

        public void moveRobot() {
            for (Direction direction : movements) {
                moveRobot(direction);
            }
        }

        private void moveRobot(Direction direction) {
            Robot newRobot = robot.move(direction);
            int x = newRobot.x();
            int y = newRobot.y();
            if (!isAWall(x, y)) {
                if (isABox(x, y)) {
                    Box box = obtainBox(x, y);
                    if (canMoveBox(box, direction)) {
                        robot = newRobot;
                        Map<Integer, Box> newBoxesMap = moveBox(box, direction);
                        boxes.removeIf(b -> newBoxesMap.containsKey(b.id()));
                        boxes.addAll(newBoxesMap.values());
                    }
                } else {
                    robot = newRobot;
                }
            }
        }

        private Box obtainBox(int x, int y) {
            return boxes.stream().filter(b -> b.contains(x, y)).findFirst().orElseThrow();
        }

        private boolean isABox(int x, int y) {
            return boxes.stream().anyMatch(b -> b.contains(x, y));
        }

        private Map<Integer, Box> moveBox(Box box, Direction direction) {
            Map<Integer, Box> movedBoxes = new HashMap<>();
            Box newBox = box.move(direction);
            if (intersectWithOtherBox(newBox)) {
                Set<Box> interestedBoxes = intersectedBoxes(newBox);
                for (Box b : interestedBoxes) {
                    movedBoxes.putAll(moveBox(b, direction));
                }
            }
            movedBoxes.put(box.id(), newBox);
            return movedBoxes;
        }

        boolean canMoveBox(Box box, Direction direction) {
            Box newBox = box.move(direction);
            if (intersectWithWall(newBox)) {
                return false;
            } else if (intersectWithOtherBox(newBox)) {
                Set<Box> interestedBoxes = intersectedBoxes(newBox);
                for (Box b : interestedBoxes) {
                    if (!canMoveBox(b, direction)) {
                        return false;
                    }
                }
            }
            return true;
        }

        private Set<Box> intersectedBoxes(Box newBox) {
            return boxes.stream()
                    .filter(b -> b.id() != newBox.id())
                    .filter(b -> b.intersect(newBox))
                    .collect(Collectors.toSet());
        }

        private boolean intersectWithOtherBox(Box newBox) {
            return boxes.stream()
                    .filter(b -> b.id() != newBox.id())
                    .anyMatch(b -> b.intersect(newBox));
        }

        private boolean intersectWithWall(Box newBox) {
            return wall.stream().anyMatch(w -> newBox.contains(w.x(), w.y()));
        }

        private boolean isAWall(int auxX, int auxY) {
            return wall.contains(new Wall(auxX, auxY));
        }

        public int sumGpsBoxes() {
            return boxes.stream().mapToInt(Box::gps).sum();
        }
    }

    record Wall(int x, int y) {
    }

    record Box(int id, int x, int y, int wide) {
        int gps() {
            return 100 * y + x;
        }

        boolean contains(int x1, int y1) {
            return x1 >= x && x1 < x + wide && y1 == y;
        }

        Box move(Direction direction) {
            int newX = this.x;
            int nexY = this.y;
            switch (direction) {
                case UP -> nexY--;
                case RIGHT -> newX++;
                case DOWN -> nexY++;
                case LEFT -> newX--;
            }
            return new Box(id, newX, nexY, wide);
        }

        public boolean intersect(Box newBox) {
            return newBox.x < x + wide && newBox.x + newBox.wide > x && newBox.y == y;
        }
    }

    record Robot(int x, int y) {

        Robot move(Direction direction) {
            int newX = this.x;
            int nexY = this.y;
            switch (direction) {
                case UP -> nexY--;
                case RIGHT -> newX++;
                case DOWN -> nexY++;
                case LEFT -> newX--;
            }
            return new Robot(newX, nexY);
        }
    }

    enum Direction {
        UP, RIGHT, DOWN, LEFT
    }
}
