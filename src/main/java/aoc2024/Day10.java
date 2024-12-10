package aoc2024;

import java.util.*;

public class Day10 implements Day {
    @Override
    public long executePart1(String input) {
        TopographicMap topographicMap = TopographicMap.create(input);
        List<Trailhead> trailheads = topographicMap.calculateTrailheads();
        return trailheads.stream().mapToInt(Trailhead::score).sum();
    }

    @Override
    public long executePart2(String input) {
        TopographicMap topographicMap = TopographicMap.create(input);
        List<Trailhead> trailheads = topographicMap.calculateTrailheads();
        return trailheads.stream().mapToInt(Trailhead::rating).sum();
    }

    private record TopographicMap(char[][] map) {

        public static TopographicMap create(String input) {
            String[] lines = input.split("\n");
            char[][] auxMap = new char[lines.length][];
            for (int i = 0; i < lines.length; i++) {
                auxMap[i] = lines[i].replace("\r", "").toCharArray();
            }
            return new TopographicMap(auxMap);
        }

        public List<Trailhead> calculateTrailheads() {
            List<Trailhead> trailheads = new ArrayList<>();
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    if (map[i][j] == '0') {
                        List<Trail> trails = calculateTrails(i, j);
                        if (!trails.isEmpty()) {
                            trailheads.add(new Trailhead(trails));
                        }
                    }
                }
            }
            return trailheads;
        }

        private List<Trail> calculateTrails(int i, int j) {
            return new ArrayList<>(calculateNextStep(i, j, 0, new Trail()));
        }

        private List<Trail> calculateNextStep(int i, int j, int value, Trail trail) {
            if (isOutside(i, j) || isNotNextValue(i, j, value)) {
                return List.of();
            } else if (value == 9) {
                List<Trail> trails = new ArrayList<>();
                    trail.step(i, j);
                    trails.add(trail);
                    return trails;
            } else {
                List<Trail> trails = new ArrayList<>();
                trail.step(i, j);
                trails.addAll(calculateNextStep(i, j+1, value+1, trail.copy()));
                trails.addAll(calculateNextStep(i, j-1, value+1, trail.copy()));
                trails.addAll(calculateNextStep(i+1, j, value+1, trail.copy()));
                trails.addAll(calculateNextStep(i-1,j , value+1, trail.copy()));
                return trails;
            }
        }

        private boolean isNotNextValue(int i, int j, int value) {
            return map[i][j] != '0' + value;
        }

        boolean isOutside(int i, int j) {
            return i < 0 || i >= map.length || j < 0 || j >= map[i].length;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (char[] chars : map) {
                sb.append(chars).append("\n");
            }
            return sb.toString();
        }
    }

    record Trailhead(List<Trail> trails) {

        int score() {
            // count Trails with different last position
            return (int) trails.stream().map(Trail::lastStep).distinct().count();
        }

        int rating() {
            return trails.size();
        }
    }

    static class Trail {

        private final SequencedSet<Position> positions;

        public Trail(Collection<Position> positions) {
            this.positions =  new LinkedHashSet<>(positions);
        }

        public Trail() {
            this.positions = new LinkedHashSet<>();
        }

        void step(int i, int j) {
            Position position = new Position(i, j);
            positions.add(position);
        }

        Position lastStep() {
            return positions.getLast();
        }

        // clone Trail
        public Trail copy() {
            return new Trail(this.positions);
        }

    }

    record Position(int x, int y) { }
}
