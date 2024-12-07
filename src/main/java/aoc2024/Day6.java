package aoc2024;

import java.util.*;
import java.util.stream.Collectors;

public class Day6 implements Day {

    @Override
    public long executePart1(String input) {
        LaboratoryMap map = LaboratoryMap.create(input);
        Route route = map.calculateRoute();
        return route.numberOfPositions();
    }

    @Override
    public long executePart2(String input) {
        LaboratoryMap map = LaboratoryMap.create(input);
        Route route = map.calculateRoute();
        Set<Position> obstructions = map.calculateObstructions(route);
        return obstructions.size();
    }

    static class LaboratoryMap {
        private final int width;
        private final int height;
        private final GuardianPosition initialGuardianPosition;
        private final List<Position> obstacles;

        LaboratoryMap(int width, int height, GuardianPosition initialGuardianPosition, List<Position> obstacles) {
            this.width = width;
            this.height = height;
            this.initialGuardianPosition = initialGuardianPosition;
            this.obstacles = obstacles;
        }

        static LaboratoryMap create(String input) {
            int width = input.lines().findFirst().orElseThrow().length();
            int height = (int) input.lines().count();
            List<GuardianPosition> guardianPositions = new LinkedList<>();
            List<Position> obstacles = new LinkedList<>();
            for (int row = 0; row < height; row++) {
                String line = input.lines().skip(row).findFirst().orElseThrow();
                for (int column = 0; column < width; column++) {
                    char c = line.charAt(column);
                    if (c == '^') {
                        Position initialPosition = new Position(column, row);
                        GuardianPosition guardianPosition = new GuardianPosition(initialPosition, Direction.UP);
                        guardianPositions.add(guardianPosition);
                    }
                    if (c == '#') {
                        Position obstacle = new Position(column, row);
                        obstacles.add(obstacle);
                    }
                }
            }
            return new LaboratoryMap(width, height, guardianPositions.getFirst(), obstacles);
        }

        private boolean isObstacle(Position position) {
            return obstacles.stream()
                    .anyMatch(obstacle -> obstacle.equals(position));
        }

        private boolean isInside(Position position) {
            int x = position.x();
            int y = position.y();
            return 0 <= x && x < width && 0 <= y && y < height;
        }

        private boolean isInitialPosition(Position position) {
            return initialGuardianPosition.position().equals(position);
        }

        private Route calculateRoute() {
            return calculateRouteWithHistoric(initialGuardianPosition, new LinkedHashSet<>());
        }

        private Route calculateRouteWithHistoric(GuardianPosition initialGuardianPosition, SequencedSet<GuardianPosition> historic) {
            SequencedSet<GuardianPosition> guardianPositions = new LinkedHashSet<>(historic);
            GuardianPosition guardianPosition = initialGuardianPosition;
            while (isInside(guardianPosition.position)) {
                if (guardianPositions.contains(guardianPosition)) {
                    throw new LoopException();
                }
                guardianPositions.add(guardianPosition);
                GuardianPosition nextPosition = guardianPosition.moveForward();
                if (isObstacle(nextPosition.position)) {
                    guardianPosition = guardianPosition.turnRight();
                } else {
                    guardianPosition = nextPosition;
                }
            }
            return new Route(guardianPositions);
        }

        private Set<Position> calculateObstructions(Route route) {
            Set<Position> newObstructions = new HashSet<>();
            SequencedSet<GuardianPosition> guardianPositions = route.guardianPositions();
            GuardianPosition actualGuardianPosition = guardianPositions.getFirst();
            for (GuardianPosition possibleObstruction : guardianPositions) {

                GuardianPosition finalActualGuardianPosition = actualGuardianPosition;
                SequencedSet<GuardianPosition> historic = guardianPositions.stream()
                        .takeWhile(gp -> !gp.equals(finalActualGuardianPosition))
                        .collect(Collectors.toCollection(LinkedHashSet::new));

                boolean isInPath = historic.stream()
                        .anyMatch(gp -> gp.position().equals(possibleObstruction.position()));

                Position possibleObstructionPosition = possibleObstruction.position();
                if (!isInPath
                        && !isInitialPosition(possibleObstructionPosition)
                        && !actualGuardianPosition.position().equals(possibleObstructionPosition)
                        && !newObstructions.contains(possibleObstructionPosition)
                ) {
                    List<Position> possibleObstacles = new LinkedList<>(obstacles);
                    possibleObstacles.add(possibleObstructionPosition);
                    try {
                        LaboratoryMap auxMap = new LaboratoryMap(width, height,
                                actualGuardianPosition, possibleObstacles);
                        auxMap.calculateRouteWithHistoric(actualGuardianPosition, historic);
                    } catch (LoopException e) {
                        newObstructions.add(possibleObstructionPosition);
                    }
                }
                actualGuardianPosition = possibleObstruction;
            }
            return newObstructions;
        }
    }

    static class LoopException extends RuntimeException {
    }

    record Position(int x, int y) {
    }

    record Route(SequencedSet<GuardianPosition> guardianPositions) {

        public int numberOfPositions() {
            Set<Position> positions = guardianPositions.stream()
                    .map(gp -> gp.position)
                    .collect(Collectors.toSet());
            return positions.size();
        }

    }

    record GuardianPosition(Position position, Direction direction) {

        GuardianPosition moveForward() {
            Position newPosition = switch (direction) {
                case UP -> new Position(position.x(), position.y() - 1);
                case RIGHT -> new Position(position.x() + 1, position.y());
                case DOWN -> new Position(position.x(), position.y() + 1);
                case LEFT -> new Position(position.x() - 1, position.y());
            };
            return new GuardianPosition(newPosition, direction);
        }

        GuardianPosition turnRight() {
            Direction newDirection = switch (direction) {
                case UP -> Direction.RIGHT;
                case RIGHT -> Direction.DOWN;
                case DOWN -> Direction.LEFT;
                case LEFT -> Direction.UP;
            };
            return new GuardianPosition(position, newDirection);
        }

    }

    enum Direction {
        UP, RIGHT, DOWN, LEFT
    }
}
