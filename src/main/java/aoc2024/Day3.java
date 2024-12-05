package aoc2024;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 implements Day {

    @Override
    public int executePart1(String input) {
        List<Multiply> multiplies = Multiply.create(input);
        if (multiplies.isEmpty()) {
            return 0;
        }
        return multiplies.stream()
                .map(Multiply::multiply)
                .reduce(Integer::sum)
                .orElseThrow();
    }

    @Override
    public int executePart2(String input) {
        List<Multiply> multiplies = Multiply.createWithEnabled(input);
        if (multiplies.isEmpty()) {
            return 0;
        }
        return multiplies.stream()
                .map(Multiply::multiply)
                .reduce(Integer::sum)
                .orElseThrow();
    }

    record Multiply(int a, int b) {
        static List<Multiply> create(String line) {
            Pattern pattern = Pattern.compile("mul\\((?<a>\\d+),(?<b>\\d+)\\)");
            Matcher matcher = pattern.matcher(line);
            List<Multiply> multiplies = new LinkedList<>();

            while (matcher.find()) {
                int a = Integer.parseInt(matcher.group("a"));
                int b = Integer.parseInt(matcher.group("b"));
                Multiply multiply = new Multiply(a, b);
                multiplies.add(multiply);
            }

            return multiplies;
        }

        static List<Multiply> createWithEnabled(String input) {
            Pattern pattern = Pattern.compile("(?<do>do)\\(\\)|(?<dont>don't)\\(\\)|(?<mul>mul)\\((?<a>\\d{1,3}),(?<b>\\d{1,3})\\)");
            Matcher matcher = pattern.matcher(input);
            List<Multiply> multiplies = new LinkedList<>();

            boolean enabled = true;
            while (matcher.find()) {
                if (matcher.group("do") != null) {
                    enabled = true;
                } else if (matcher.group("dont") != null) {
                    enabled = false;
                } else if (matcher.group("mul") != null && enabled) {
                    int a = Integer.parseInt(matcher.group("a"));
                    int b = Integer.parseInt(matcher.group("b"));
                    Multiply multiply = new Multiply(a, b);
                    multiplies.add(multiply);
                }
            }
            return multiplies;
        }

        int multiply() {
            return a * b;
        }
    }

}
