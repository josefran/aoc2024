package aoc2024;

import java.util.*;
import java.util.stream.Collectors;

public class Day11 implements Day {
    @Override
    public long executePart1(String input) {
        StoneStraightLine straightLine = StoneBlinker.create(input);
        StoneStraightLine straightLine25 = StoneBlinker.blink(straightLine, 25);
        return straightLine25.length();
    }

    @Override
    public long executePart2(String input) {
        StoneStraightLine straightLine = StoneBlinker.create(input);
        StoneStraightLine straightLine75 = StoneBlinker.blink(straightLine, 75);
        return straightLine75.length();
    }

    static class StoneBlinker {
        private static final Map<List<Long>, StoneStraightLine> cache = new HashMap<>();

        private StoneBlinker() {}

        public static StoneStraightLine create(String input) {
            String line = input.lines().findFirst().orElseThrow();
            List<Stone> stones = Arrays.stream(line.split(" "))
                    .map(Long::parseLong)
                    .map(Stone::new)
                    .collect(Collectors.toCollection(LinkedList::new));
            return new StoneStraightLine(stones);
        }

        public static StoneStraightLine blink(StoneStraightLine straightLine, int blinks) {
            List<Long> key = straightLine.stones().stream().map(Stone::number).toList();
            if (cache.containsKey(key)) {
                return cache.get(key);
            }

            StoneStraightLine result = straightLine;
            for (int i = 0; i < blinks; i++) {
                result = blink(result);
            }

            cache.put(key, result);
            return result;
        }

        private static StoneStraightLine blink(StoneStraightLine straightLine) {
            List<Stone> result = new LinkedList<>();
            List<Long> stones = straightLine.stones().stream().map(Stone::number).toList();
            for (long number : stones) {
                String digits = String.valueOf(number);
                if (number == 0) {
                    result.add(new Stone(1));
                } else if (digits.length() % 2 == 0) {
                    int half = digits.length() / 2;
                    long left = Long.parseLong(digits.substring(0, half));
                    long right = Long.parseLong(digits.substring(half));
                    result.add(new Stone(left));
                    result.add(new Stone(right));
                } else {
                    result.add(new Stone(number * 2024));
                }
            }
            return new StoneStraightLine(result);
        }
    }

    record StoneStraightLine(List<Stone> stones) {

        public long length() {
            return stones().size();
        }
    }

    record Stone(long number) {

    }
}
