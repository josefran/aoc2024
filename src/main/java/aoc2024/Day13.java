package aoc2024;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13 implements Day {

    @Override
    public long executePart1(String input) {
        List<Machine> machines = createMachines(input);
        return machines.stream()
                .map(Machine::minimizeTokens)
                .filter(Optional::isPresent)
                .mapToLong(Optional::get)
                .sum();
    }

    @Override
    public long executePart2(String input) {
        List<Machine> machines = createMachines(input, 10000000000000L);
        return machines.stream()
                .map(Machine::minimizeTokens)
                .filter(Optional::isPresent)
                .mapToLong(Optional::get)
                .sum();
    }


    private List<Machine> createMachines(String input) {
        return createMachines(input, 0);
    }

    private List<Machine> createMachines(String input, long corrector) {
        List<Machine> machines = new LinkedList<>();
        List<String> lines = input.lines().toList();
        for (Iterator<String> iterator = lines.iterator(); iterator.hasNext(); ) {
            Button a = extractButton(iterator.next(), 3);
            Button b = extractButton(iterator.next(), 1);
            Prize prize = extractPrize(iterator.next());
            if (iterator.hasNext()) iterator.next(); // Skip empty line
            Prize newPrize = prize.fix(corrector);
            Machine machine = new Machine(a, b, newPrize);
            machines.add(machine);
        }
        return machines;
    }

    private static Button extractButton(String line, int tokens) {
        Pattern buttonPattern = Pattern.compile("Button (A|B): X\\+(\\d+), Y\\+(\\d+)");
        Matcher matcher = buttonPattern.matcher(line);
        if (matcher.find()) {
            int xValue = Integer.parseInt(matcher.group(2));
            int yValue = Integer.parseInt(matcher.group(3));
            return new Button(xValue, yValue, tokens);
        } else throw new IllegalArgumentException("Invalid input");
    }

    private static Prize extractPrize(String line) {
        Pattern prizePattern = Pattern.compile("Prize: X=(\\d+), Y=(\\d+)");
        Matcher matcher = prizePattern.matcher(line);
        if (matcher.find()) {
            int xValue = Integer.parseInt(matcher.group(1));
            int yValue = Integer.parseInt(matcher.group(2));
            return new Prize(xValue, yValue);
        } else throw new IllegalArgumentException("Invalid input");
    }

    record Machine(Button a, Button b, Prize prize) {

        public Optional<Long> minimizeTokens() {
            Optional<Long[]> longs = SolverLinearEquations.solveLinearEquations(
                    a.x(), b.x(), prize.x(),
                    a.y(), b.y(), prize.y());
            if (longs.isEmpty()) {
                return Optional.empty();
            } else {
                Long[] result = longs.get();
                return Optional.of(result[0] * a.tokens() + result[1] * b.tokens());
            }
        }
    }

    record Button(int x, int y, int tokens) {
    }

    record Prize(long x, long y) {
        public Prize fix(long corrector) {
            return new Prize(x + corrector, y + corrector);
        }
    }

    static class SolverLinearEquations {

        public static Optional<Long[]> solveLinearEquations(long a1, long b1, long c1, long a2, long b2, long c2) {
            long determinant = a1 * b2 - a2 * b1;
            if (determinant == 0) {
                throw new IllegalArgumentException("No unique solution exists");
            }

            long xNumerator = c1 * b2 - c2 * b1;
            long yNumerator = a1 * c2 - a2 * c1;

            if (xNumerator % determinant != 0 || yNumerator % determinant != 0) {
                return Optional.empty(); //No integer solution exists
            }

            long x = xNumerator / determinant;
            long y = yNumerator / determinant;
            return Optional.of(new Long[]{x, y});
        }

    }

}
