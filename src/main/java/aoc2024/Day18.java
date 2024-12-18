package aoc2024;

import java.util.*;
import java.util.stream.Collectors;

public class Day18 implements Day {

    public long executePart1(String input, Map<String, String> context) {
        int width = Integer.parseInt(context.get("width"));
        int height = Integer.parseInt(context.get("height"));
        int fallenBytes = Integer.parseInt(context.get("fallenBytes"));
        Set<Position> corruptedBytes = createPositions(input, fallenBytes);
        Memory memory = new Memory(width, height, corruptedBytes);
        Graph graph = GraphBuilder.buildGraph(memory);
        Position start = new Position(0, 0);
        Position end = new Position(width - 1, height - 1);
        List<Position> path = graph.dijkstra(start, end);
        return path.size() - 1;
    }

    public String executePart2(String input, Map<String, String> context) {
        int width = Integer.parseInt(context.get("width"));
        int height = Integer.parseInt(context.get("height"));
        int fallenBytes = Integer.parseInt(context.get("fallenBytes"));
        Set<Position> corruptedBytes = createPositions(input, fallenBytes);
        List<Position> pendingCorruptedBytes = createPositionsFrom(input, fallenBytes);
        Memory memory = new Memory(width, height, corruptedBytes);
        System.out.println(memory.memoryMapToString());
        for (Position corruptedByte : pendingCorruptedBytes) {
            memory.addCorruptedByte(corruptedByte);
            Graph graph = GraphBuilder.buildGraph(memory);
            Position start = new Position(0, 0);
            Position end = new Position(width - 1, height - 1);
            try {
                graph.dijkstra(start, end);
            } catch (Graph.NotPathFound e) {
                return corruptedByte.coords();
            }
        }
        throw new IllegalStateException("No corrupted byte found");
    }

    @Override
    public long executePart1(String input) {
        // Implement the logic for part 2
        return 0;
    }

    @Override
    public long executePart2(String input) {
        // Implement the logic for part 2
        return 0;
    }

    private Set<Position> createPositions(String input, int linesNumber) {
        return input.lines()
                .limit(linesNumber)
                .map(line -> {
                    String[] parts = line.split(",");
                    int x = Integer.parseInt(parts[0]);
                    int y = Integer.parseInt(parts[1]);
                    return new Position(x, y);
                }).collect(Collectors.toSet());
    }

    private List<Position> createPositionsFrom(String input, int linesNumber) {
        return input.lines()
                .skip(linesNumber)
                .map(line -> {
                    String[] parts = line.split(",");
                    int x = Integer.parseInt(parts[0]);
                    int y = Integer.parseInt(parts[1]);
                    return new Position(x, y);
                }).toList();
    }


    static final class Memory {
        private final int width;
        private final int height;
        private final Set<Position> corruptedBytes;

        Memory(int width, int height, Set<Position> corruptedBytes) {
            this.height = height;
            this.width = width;
            this.corruptedBytes = new HashSet<>(corruptedBytes);
        }

        public boolean isValidPosition(Position position) {
            return position.x() >= 0 && position.x() < width &&
                    position.y() >= 0 && position.y() < height &&
                    !corruptedBytes.contains(position);
        }

        public boolean isCorruptedBytes(Position current) {
            return corruptedBytes.contains(current);
        }

        public void addCorruptedByte(Position position) {
            corruptedBytes.add(position);
        }

        char[][] memoryMap() {
            char[][] map = new char[height][width];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    map[y][x] = corruptedBytes.contains(new Position(x, y)) ? '#' : '.';
                }
            }
            return map;
        }

        @SuppressWarnings("unused")
        String memoryMapToString() {
            return Arrays.stream(memoryMap())
                    .map(String::new)
                    .collect(Collectors.joining("\n"));
        }

    }

    record Position(int x, int y) {
        public String coords() {
            return x + "," + y;
        }
    }

    static class GraphBuilder {

        public static Graph buildGraph(Memory memory) {
            Graph graph = new Graph();
            for (int y = 0; y < memory.height; y++) {
                for (int x = 0; x < memory.width; x++) {
                    Position current = new Position(x, y);
                    if (!memory.isCorruptedBytes(current)) {
                        addEdges(graph, memory, current);
                    }
                }
            }
            return graph;
        }

        private static void addEdges(Graph graph, Memory memory, Position current) {
            int x = current.x();
            int y = current.y();
            Position[] neighbors = {
                    new Position(x, y - 1), // UP
                    new Position(x, y + 1), // DOWN
                    new Position(x - 1, y), // LEFT
                    new Position(x + 1, y)  // RIGHT
            };
            for (Position neighbor : neighbors) {
                if (memory.isValidPosition(neighbor)) {
                    graph.addEdge(current, neighbor);
                }
            }
        }

    }

    static class Graph {
        private final Map<Position, List<Position>> adjList = new HashMap<>();

        public void addEdge(Position current, Position neighbor) {
            adjList.computeIfAbsent(current, k -> new ArrayList<>()).add(neighbor);
        }

        public List<Position> dijkstra(Position start, Position end) {
            Map<Position, Long> distances = new HashMap<>();
            Map<Position, Position> previous = new HashMap<>();
            PriorityQueue<Position> queue = new PriorityQueue<>(Comparator.comparingLong(distances::get));

            distances.put(start, 0L);
            queue.add(start);

            while (!queue.isEmpty()) {
                Position current = queue.poll();

                if (current.equals(end)) {
                    return buildPath(previous, end);
                }

                for (Position neighbor : adjList.getOrDefault(current, Collections.emptyList())) {
                    long newDist = distances.get(current) + 1; // Assuming uniform cost
                    if (newDist < distances.getOrDefault(neighbor, Long.MAX_VALUE)) {
                        distances.put(neighbor, newDist);
                        previous.put(neighbor, current);
                        queue.add(neighbor);
                    }
                }
            }

            throw new NotPathFound();
        }

        static class NotPathFound extends RuntimeException {
            public NotPathFound() {
                super("No path found");
            }
        }

        private List<Position> buildPath(Map<Position, Position> previous, Position end) {
            List<Position> path = new ArrayList<>();
            for (Position at = end; at != null; at = previous.get(at)) {
                path.add(at);
            }
            Collections.reverse(path);
            return path;
        }
    }
}
