package aoc2024;

import java.util.*;

public class Day16 implements Day {

    @Override
    public long executePart1(String input) {
        Maze maze = createMaze(input);
        Graph graph = GraphBuilder.buildGraph(maze);
        List<Graph.Edge> edges = graph.shortestPathWithDirectionCost(maze.start, maze.end, Direction.RIGHT);
        PathResult pathResult = PathResult.calculatePathResult(edges, Direction.RIGHT);
        System.out.println("Steps: " + pathResult.steps());
        System.out.println("Direction changes: " + pathResult.directionChanges());
        System.out.print(mazeToString(maze.mazeWithPath(edges)));
        return pathResult.cost();
    }

    @Override
    public long executePart2(String input) {
        return 0;
    }

    private Maze createMaze(String input) {
        Set<Position> wall = new HashSet<>();
        Position start = null;
        Position end = null;
        List<String> lines = input.lines().toList();
        int width = lines.getFirst().length();
        int height = lines.size();
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                if (c == '#') {
                    wall.add(new Position(x, y));
                } else if (c == 'S') {
                    start = new Position(x, y);
                } else if (c == 'E') {
                    end = new Position(x, y);
                }
            }
        }
        return new Maze(height, width, wall, start, end);
    }

    private static String mazeToString(char[][] map) {
        StringBuilder sb = new StringBuilder();
        for (char[] row : map) {
            sb.append(row).append('\n');
        }
        return sb.toString();
    }

    static final class Maze {
        private final int height;
        private final int width;
        private final Set<Position> wall;
        private final Position start;
        private final Position end;

        Maze(int height, int width, Set<Position> wall, Position start, Position end) {
            this.height = height;
            this.width = width;
            this.wall = wall;
            this.start = start;
            this.end = end;
        }

        public boolean isValidPosition(Position position) {
            return position.x() >= 0 && position.x() < width &&
                    position.y() >= 0 && position.y() < height &&
                    !wall.contains(position);
        }

        char[][] maze() {
            char[][] map = new char[height][width];
            for (char[] row : map) {
                Arrays.fill(row, '.');
            }
            for (Position w : wall) {
                map[w.y()][w.x()] = '#';
            }
            map[start.y()][start.x()] = 'S';
            map[end.y()][end.x()] = 'E';
            return map;
        }

        char[][] mazeWithPath(List<Graph.Edge> path) {
            char[][] map = maze();
            path.stream().skip(1).forEach(step -> {
                Position pos = step.destination();
                char charDirection = '.';
                switch (step.direction()) {
                    case UP -> charDirection = '^';
                    case DOWN -> charDirection = 'v';
                    case LEFT -> charDirection = '<';
                    case RIGHT -> charDirection = '>';
                }
                map[pos.y()][pos.x()] = charDirection;
            });
            return map;
        }

        public boolean isWall(Position current) {
            return wall.contains(current);
        }
    }

    record Position(int x, int y) {
    }

    enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    static class GraphBuilder {

        public static Graph buildGraph(Maze maze) {
            Graph graph = new Graph();
            for (int y = 0; y < maze.height; y++) {
                for (int x = 0; x < maze.width; x++) {
                    Position current = new Position(x, y);
                    if (!maze.isWall(current)) {
                        addEdges(graph, maze, current);
                    }
                }
            }
            return graph;
        }

        private static void addEdges(Graph graph, Maze maze, Position current) {
            int x = current.x();
            int y = current.y();
            Position[] neighbors = {
                    new Position(x, y - 1), // UP
                    new Position(x, y + 1), // DOWN
                    new Position(x - 1, y), // LEFT
                    new Position(x + 1, y)  // RIGHT
            };
            Direction[] directions = {
                    Direction.UP,
                    Direction.DOWN,
                    Direction.LEFT,
                    Direction.RIGHT
            };

            for (int i = 0; i < neighbors.length; i++) {
                Position neighbor = neighbors[i];
                if (maze.isValidPosition(neighbor)) {
                    graph.addEdge(current, neighbor, directions[i]);
                }
            }
        }

    }

    record PathResult(int steps, int directionChanges) {

        public static PathResult calculatePathResult(List<Graph.Edge> path, Direction initialDirection) {
            int steps = path.size();
            int directionChanges = 0;
            Direction previousDirection = initialDirection;

            for (Graph.Edge edge : path) {
                if (edge.direction() != previousDirection) directionChanges++;
                previousDirection = edge.direction();
            }

            return new PathResult(steps, directionChanges);
        }

        public long cost() {
            return steps + directionChanges * Graph.CHANGE_DIRECTION_COST;
        }
    }

    static class Graph {
        public static final long NEXT_POSITION_COST = 50;
        public static final long CHANGE_DIRECTION_COST = 1000;
        private final Map<Position, List<Edge>> adjList = new HashMap<>();

        public List<Edge> shortestPathWithDirectionCost(Position start, Position end, Direction initialDirection) {
            PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingLong(Node::cost));
            Map<Position, Long> costs = new HashMap<>();
            Map<Position, Edge> previous = new HashMap<>();
            Set<Position> visited = new HashSet<>();

            queue.add(new Node(start, 0, initialDirection));
            costs.put(start, 0L);

            while (!queue.isEmpty()) {
                Node current = queue.poll();
                if (!visited.add(current.position())) continue;

                if (current.position().equals(end)) {
                    return buildPath(previous, end);
                }

                for (Edge edge : adjList.getOrDefault(current.position(), Collections.emptyList())) {
                    long newCost = edge.cost(current.direction());
                    if (newCost < costs.getOrDefault(edge.destination(), Long.MAX_VALUE)) {
                        costs.put(edge.destination(), newCost);
                        queue.add(new Node(edge.destination(), newCost, edge.direction()));
                        previous.put(edge.destination(), new Edge(current.position(), edge.direction()));
                    }
                }
            }

            throw new IllegalArgumentException("No path found");
        }

        private List<Edge> buildPath(Map<Position, Edge> previous, Position end) {
            List<Edge> path = new ArrayList<>();
            for (Position at = end; previous.containsKey(at); at = previous.get(at).destination()) {
                path.add(previous.get(at));
            }
            Collections.reverse(path);
            return path;
        }

        public void addEdge(Position source, Position destination, Direction direction) {
            adjList.computeIfAbsent(source, k -> new ArrayList<>()).add(new Edge(destination, direction));
        }

        record Edge(Position destination, Direction direction) {
            long cost(Direction currentDirection) {
                return currentDirection == direction ? NEXT_POSITION_COST : NEXT_POSITION_COST + CHANGE_DIRECTION_COST;

            }
        }

        record Node(Position position, long cost, Direction direction) {
        }

    }
}
