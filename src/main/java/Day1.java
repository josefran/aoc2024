import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day1 {

    public int execute(List<String> lines) {
        SortedLists sortedLists = calculateSortedLists(lines);

        List<Pair> sortedPairs = new LinkedList<>();
        for (int i = 0; i < sortedLists.leftSorted().size(); i++) {
            sortedPairs.add(new Pair(sortedLists.leftSorted().get(i), sortedLists.rightSorted().get(i)));
        }

        //Calculate distances and sum all distances
        return sortedPairs.stream().mapToInt(Pair::distance).sum();
    }

    private static SortedLists calculateSortedLists(List<String> lines) {
        List<Integer> leftList = new LinkedList<>();
        List<Integer> rightList = new LinkedList<>();
        for (String line : lines) {
            //Split the line by spaces
            String[] columns = line.split("\\s+");
            leftList.add(Integer.parseInt(columns[0]));
            rightList.add(Integer.parseInt(columns[1]));
        }
        List<Integer> leftSorted = leftList.stream().sorted().toList();
        List<Integer> rightSorted = rightList.stream().sorted().toList();
        return new SortedLists(leftSorted, rightSorted);
    }

    public int executeCase2(List<String> lines) {
        SortedLists sortedLists = calculateSortedLists(lines);
        // Calculate number apparitions leftList number in rightList

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
        //Count number of apparitions of number in integerList
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
