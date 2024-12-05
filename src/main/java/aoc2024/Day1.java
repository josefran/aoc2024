package aoc2024;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day1 implements Day {

    @Override
    public int executePart1(String input) {
        List<String> lines = List.of(input.trim().split("\n"));
        SortedLists sortedLists = calculateSortedLists(lines);

        List<Pair> sortedPairs = new LinkedList<>();
        for (int i = 0; i < sortedLists.leftSorted().size(); i++) {
            sortedPairs.add(new Pair(sortedLists.leftSorted().get(i), sortedLists.rightSorted().get(i)));
        }

        return sortedPairs.stream().mapToInt(Pair::distance).sum();
    }

    @Override
    public int executePart2(String input) {
        List<String> lines = List.of(input.trim().split("\n"));
        return executePart2(lines);
    }

    private static SortedLists calculateSortedLists(List<String> lines) {
        List<Integer> leftList = new LinkedList<>();
        List<Integer> rightList = new LinkedList<>();
        for (String line : lines) {
            String[] columns = line.split("\\s+");
            leftList.add(Integer.parseInt(columns[0]));
            rightList.add(Integer.parseInt(columns[1]));
        }
        List<Integer> leftSorted = leftList.stream().sorted().toList();
        List<Integer> rightSorted = rightList.stream().sorted().toList();
        return new SortedLists(leftSorted, rightSorted);
    }

    public int executePart2(List<String> lines) {
        SortedLists sortedLists = calculateSortedLists(lines);

        Map<Integer, Integer> leftListApparitions = sortedLists.leftSorted().stream().distinct()
                .collect(Collectors.toMap(i -> i, i -> calculateNumberApparitions(sortedLists.rightSorted(), i)));
        int result = 0;
        for (int leftNumber : sortedLists.leftSorted()) {
            Integer value = leftListApparitions.getOrDefault(leftNumber, 0);
            result += leftNumber * value;
        }
        return result;
    }

    private Integer calculateNumberApparitions(List<Integer> integerList, Integer number) {
        return (int) integerList.stream().filter(i -> i.equals(number)).count();
    }


    private record SortedLists(List<Integer> leftSorted, List<Integer> rightSorted) {
    }

    record Pair(int left, int right) {
        public int distance() {
            return Math.abs(right - left);
        }
    }
}
