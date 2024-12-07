package aoc2024;

import java.util.*;
import java.util.stream.Collectors;

public class Day5 implements Day {

    @Override
    public long executePart1(String input) {
        String[] parts = input.split("\\r?\\n\\r?\\n");
        String pageOrderingRulesInput = parts[0];
        String updateInput = parts[1];

        Map<Integer, List<Integer>> pageOrderingRules = extractPageOrderingRules(pageOrderingRulesInput);
        String[] updates = updateInput.split("\r?\n");
        List<Update> listUpdates = Update.createList(updates);
        List<Update> correctUpdates = obtainCorrectlyOrderedUpdates(pageOrderingRules, listUpdates);
        return correctUpdates.stream()
                .mapToInt(Update::getMiddlePageNumber)
                .sum();
    }

    @Override
    public long executePart2(String input) {
        String[] parts = input.split("\\r?\\n\\r?\\n");
        String pageOrderingRulesInput = parts[0];
        String updateInput = parts[1];

        Map<Integer, List<Integer>> pageOrderingRules = extractPageOrderingRules(pageOrderingRulesInput);
        String[] updates = updateInput.split("\r?\n");
        List<Update> listUpdates = Update.createList(updates);
        List<Update> incorrectUpdates = obtainIncorrectlyOrderedUpdates(pageOrderingRules, listUpdates);
        for (Update update : incorrectUpdates) {
            update.sortPages(pageOrderingRules);
        }
        return incorrectUpdates.stream()
                .mapToInt(Update::getMiddlePageNumber)
                .sum();
    }

    private Map<Integer, List<Integer>> extractPageOrderingRules(String pageOrderingRulesInput) {
        Map<Integer, List<Integer>> map = new LinkedHashMap<>();
        pageOrderingRulesInput.lines().forEach(line -> {
            String[] split = line.split("\\|");
            int key = Integer.parseInt(split[0]);
            int value = Integer.parseInt(split[1]);
            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(value);
        });
        return map;
    }

    private List<Update> obtainCorrectlyOrderedUpdates(Map<Integer, List<Integer>> pageOrderingRules, List<Update> listUpdates) {
        return listUpdates.stream()
                .filter(update -> update.isCorrectlyOrdered(pageOrderingRules))
                .collect(Collectors.toList());
    }

    private List<Update> obtainIncorrectlyOrderedUpdates(Map<Integer, List<Integer>> pageOrderingRules, List<Update> listUpdates) {
        return listUpdates.stream()
                .filter(update -> !update.isCorrectlyOrdered(pageOrderingRules))
                .collect(Collectors.toList());
    }


    public static class Update implements Iterable<Integer> {
        private final List<Integer> pages;

        public Update(List<Integer> pages) {
            this.pages = new ArrayList<>(pages);
        }

        public static List<Update> createList(String[] updates) {
            List<Update> listUpdates = new ArrayList<>();
            for (String update : updates) {
                String[] pages = update.split(",");
                List<Integer> pagesList = Arrays.stream(pages).map(Integer::parseInt).toList();
                listUpdates.add(new Update(pagesList));
            }
            return listUpdates;
        }

        public boolean isAfter(int value, int page) {
            int pageIndex = pages.indexOf(page);
            int valueIndex = pages.indexOf(value);
            return valueIndex != -1 && pageIndex != -1 && valueIndex > pageIndex;
        }

        public int getMiddlePageNumber() {
            int index = pages.size() / 2;
            if (pages.size() % 2 == 0) {
                throw new IllegalArgumentException("The number of pages must be odd");
            }
            return pages.get(index);
        }

        @Override
        public Iterator<Integer> iterator() {
            return pages.iterator();
        }

        @Override
        public String toString() {
            return pages.toString();
        }

        public void sortPages(Map<Integer, List<Integer>> pageOrderingRules) {
            Comparator<? super Integer> comparator = comparatorToReorder(pageOrderingRules);
            pages.sort(comparator);
        }

        private static Comparator<? super Integer> comparatorToReorder(Map<Integer, List<Integer>> pageOrderingRules) {
             return (page1, page2) -> {
                 if (pageOrderingRules.getOrDefault(page1, new ArrayList<>()).contains(page2)) {
                     return -1;
                 } else if (pageOrderingRules.getOrDefault(page2, new ArrayList<>()).contains(page1)) {
                     return 1;
                 }
                 return 0;
            };
        }

        public boolean isCorrectlyOrdered(Map<Integer, List<Integer>> pageOrderingRules) {
            for (int page : pages) {
                List<Integer> nextPages = pageOrderingRules.getOrDefault(page, new ArrayList<>());
                if (nextPages.isEmpty()) continue;
                for (int nextPage : nextPages) {
                    if (pages.contains(nextPage)) {
                        if (!isAfter(nextPage, page)) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
    }

}
