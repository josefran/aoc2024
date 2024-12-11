package aoc2024;

import java.util.*;
import java.util.stream.Collectors;

public class Day11 implements Day {
    @Override
    public long executePart1(String input) {
        StoneBlinker stoneBlinker = StoneBlinker.create(input);
        return stoneBlinker.lengthOfBlink(25);
    }

    @Override
    public long executePart2(String input) {
        StoneBlinker stoneBlinker = StoneBlinker.create(input);
        return stoneBlinker.lengthOfBlink(75);
    }

    private static class StoneBlinker {
        private final StoneGraph graph;

        private static StoneBlinker create(String input) {
            String line = input.lines().findFirst().orElseThrow();
            List<Long> stones = Arrays.stream(line.split(" "))
                    .map(Long::parseLong)
                    .collect(Collectors.toCollection(LinkedList::new));
            return new StoneBlinker(stones);
        }

        public StoneBlinker(List<Long> stones) {
            graph = new StoneGraph(stones);
        }

        public long lengthOfBlink(int blinks) {
            for (int blink = 1; blink <= blinks; blink++) {
                blink(blink);
            }
            return graph.getLengthByBlink(blinks);
        }

        private void blink(int blink) {
            List<StoneGraph.StoneNode> nodes = graph.getNodesByBlink(blink - 1);
            for (StoneGraph.StoneNode parentNode : nodes) {
                long parentStone = parentNode.getStone();
                if (graph.containsChildren(parentStone)) {
                    long apparitions = parentNode.getApparitionsByBlink(blink - 1);
                    for (StoneGraph.StoneNode node : parentNode.getChildren()) {
                        node.incrementApparition(blink, apparitions);
                    }
                } else {
                    String digits = String.valueOf(parentStone);
                    if (parentStone == 0) {
                        graph.addEdge(blink, parentStone, 1);
                    } else if (digits.length() % 2 == 0) {
                        int half = digits.length() / 2;
                        long leftStone = Long.parseLong(digits.substring(0, half));
                        long rightStone = Long.parseLong(digits.substring(half));
                        graph.addEdge(blink, parentStone, rightStone);
                        graph.addEdge(blink, parentStone, leftStone);
                    } else {
                        graph.addEdge(blink, parentStone, parentStone * 2024L);
                    }
                }
            }
        }
    }

    private static class StoneGraph {
        private final Map<Long, StoneNode> nodes = new HashMap<>();

        public StoneGraph(List<Long> stones) {
            addAllNodes(0, stones);
        }

        public void addNode(int blink, long stone, long apparitions) {
            nodes.computeIfAbsent(stone, k -> new StoneNode(stone))
                    .incrementApparition(blink, apparitions);
        }

        public void addAllNodes(int blink, List<Long> stones) {
            stones.forEach(stone -> addNode(blink, stone, 1));
        }

        public void addEdge(int blink, long parent, long child) {
            StoneNode parentNode = nodes.get(parent);
            long apparitionsParent = parentNode.getApparitionsByBlink(blink - 1);
            addNode(blink, child, apparitionsParent);
            StoneNode childNode = nodes.get(child);
            parentNode.addChild(childNode);
        }

        public boolean containsChildren(long parentStone) {
            return !nodes.get(parentStone).getChildren().isEmpty();
        }

        public long getLengthByBlink(int blink) {
            return getNodesByBlink(blink).stream()
                    .mapToLong(n -> n.getApparitionsByBlink(blink))
                    .sum();
        }

        public List<StoneNode> getNodesByBlink(int blink) {
            return nodes.values().stream()
                    .filter(node -> node.apparitionsByBlink.containsKey(blink))
                    .toList();
        }

        private static class StoneNode {
            private final Map<Integer, Long> apparitionsByBlink = new HashMap<>();
            private final long stone;
            private final List<StoneNode> children = new ArrayList<>();

            public StoneNode(long stone) {
                this.stone = stone;
            }

            public long getStone() {
                return stone;
            }

            public List<StoneNode> getChildren() {
                return children;
            }

            public void addChild(StoneNode child) {
                children.add(child);
            }

            public void incrementApparition(int blink, long parentApparitions) {
                apparitionsByBlink.merge(blink, parentApparitions, Long::sum);
            }

            public long getApparitionsByBlink(int blink) {
                return apparitionsByBlink.getOrDefault(blink, 0L);
            }
        }
    }
}