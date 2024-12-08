package aoc2024;

import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Stream.*;

public class Day8 implements Day {
    @Override
    public long executePart1(String input) {
        CityMap cityMap = CityMap.create(input);
        Set<Position> antinodes = cityMap.calculateAntinodes(false);
        //cityMap.printMap(System.out);
        return antinodes.size();
    }

    @Override
    public long executePart2(String input) {
        CityMap cityMap = CityMap.create(input);
        Set<Position> antinodes = cityMap.calculateAntinodes(true);
        //cityMap.printMap(System.out);
        return antinodes.size();
    }

    static class CityMap {
        private final int width;
        private final int height;
        private final Map<Character, Set<Position>> antennas;
        private final Set<Position> antinodes;

        static CityMap create(String input) {
            List<String> lines = input.lines().toList();
            int width = lines.getFirst().length();
            int height = lines.size();
            CityMap cityMap = new CityMap(width, height);
            for (int row = 0; row < height; row++) {
                String line = lines.get(row);
                for (int column = 0; column < width; column++) {
                    char c = line.charAt(column);
                    if (c != '.') {
                        cityMap.addAntenna(c, new Position(column, row));
                    }
                }
            }
            return cityMap;
        }

        CityMap(int width, int height) {
            this.width = width;
            this.height = height;
            this.antennas = new LinkedHashMap<>();
            this.antinodes = new LinkedHashSet<>();
        }

        void addAntenna(char antenna, Position position) {
            antennas.computeIfAbsent(antenna, k -> new LinkedHashSet<>())
                    .add(position);
        }

        boolean isInside(Position position) {
            int x = position.x();
            int y = position.y();
            return 0 <= x && x < width && 0 <= y && y < height;
        }

        Set<Position> calculateAntinodes(boolean withHarmonics) {
            antennas.entrySet().stream()
                    .flatMap(entry -> calculatePairPositions(entry.getValue()).stream())
                    .flatMap(pair -> calculateAntinodes(pair, withHarmonics).stream())
                    .forEach(antinodes::add);
            return antinodes;
        }

        private Set<PairPosition> calculatePairPositions(Set<Position> antennasPositions) {
            return antennasPositions.stream()
                    .flatMap(pos1 -> antennasPositions.stream()
                            .filter(pos2 -> !pos1.equals(pos2))
                            .map(pos2 -> new PairPosition(pos1, pos2)))
                    .collect(Collectors.toSet());
        }

        private Set<Position> calculateAntinodes(PairPosition pair, boolean withHarmonics) {
            if (withHarmonics) return calculateAntinodesWithHarmonics(pair);
            return calculateAntinodes(pair);
        }

        private Set<Position> calculateAntinodes(PairPosition pair) {
            Distance distance = pair.distance();
            return of(
                    pair.position1().addDistance(distance),
                    pair.position2().subtractDistance(distance)
            )
                    .filter(this::isInside)
                    .collect(Collectors.toSet());
        }

        private Set<Position> calculateAntinodesWithHarmonics(PairPosition pairPosition) {
            Distance distance = pairPosition.distance();
            Stream<Position> initialPositions = of(pairPosition.position1(), pairPosition.position2());
            Stream<Position> diagonal1 = iterate(
                    pairPosition.position1().addDistance(distance),
                    this::isInside,
                    pos -> pos.addDistance(distance));
            Stream<Position> diagonal2 = iterate(
                    pairPosition.position2().subtractDistance(distance),
                    this::isInside,
                    pos -> pos.subtractDistance(distance));
            return concat(initialPositions, concat(diagonal1, diagonal2)).collect(Collectors.toSet());
        }

        @SuppressWarnings("unused")
        public void printMap(PrintStream out) {
            char[][] map = map();
            Arrays.stream(map).forEach(out::println);
        }

        private char[][] map() {
            char[][] map = initializeMap();
            fillAntennas(map);
            fillAntinodes(map);
            return map;
        }

        private char[][] initializeMap() {
            char[][] map = new char[height][width];
            for (int row = 0; row < height; row++) {
                Arrays.fill(map[row], '.');
            }
            return map;
        }

        private void fillAntennas(char[][] map) {
            antennas.forEach((antenna, positions) ->
                    positions.forEach(position ->
                            map[position.y()][position.x()] = antenna
                    )
            );
        }

        private void fillAntinodes(char[][] map) {
            antinodes.forEach(antinode ->
                    map[antinode.y()][antinode.x()] = '#'
            );
        }
    }

    record PairPosition(Position position1, Position position2) {
        public Distance distance() {
            int x = position1.x() - position2.x();
            int y = position1.y() - position2.y();
            return new Distance(x, y);
        }
    }

    record Position(int x, int y) {

        Position addDistance(Distance distance) {
            return new Position(x + distance.x(), y + distance.y());
        }

        Position subtractDistance(Distance distance) {
            return new Position(x - distance.x(), y - distance.y());
        }
    }

    record Distance(int x, int y) {
    }

}
