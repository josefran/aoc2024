package aoc2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day7 implements Day {

    @Override
    public long executePart1(String input) {
        return CalibrationsChecker.check(input, false);
    }

    @Override
    public long executePart2(String input) {
        return CalibrationsChecker.check(input, true);
    }

    private static List<Calibration> createCalibrations(String input) {
        return input.lines()
                .map(Day7::createCalibration)
                .toList();
    }

    private static class CalibrationsChecker {
        static long check(String input, boolean concatenate) {
            List<Calibration> calibrations = createCalibrations(input);
            return calibrations.stream()
                    .filter(calibration -> calibration.isPossiblyTrue(concatenate))
                    .mapToLong(Calibration::result).sum();
        }
    }

    private static Calibration createCalibration(String line) {
        String[] parts = line.split(": ");
        long result = Long.parseLong(parts[0]);
        String[] values = parts[1].split(" ");
        long[] valuesLong = new long[values.length];
        for (int i = 0; i < values.length; i++) {
            valuesLong[i] = Long.parseLong(values[i]);
        }
        return new Calibration(result, valuesLong);
    }

    record Calibration(long result, long[] values) {

        public boolean isPossiblyTrue(boolean concatenate) {
            List<Operation> operations = Operation.createOperations(values);
            for (Operation operation : operations) {
                if (concatenate) {
                    if (operation.result() == result) {
                        return true;
                    }
                } else if (!operation.containsConcatenation()) {
                    if (operation.result() == result) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "Calibration{" +
                    "result=" + result +
                    ", values=" + Arrays.toString(values) +
                    '}';
        }
    }

    record Operation(long[] values, Operator[] operators) {
        public static List<Operation> createOperations(long[] values) {
            List<Operator[]> allCombinations = generateCombinations(values.length - 1);
            return buildOperation(values, allCombinations);
        }

        public static List<Operator[]> generateCombinations(int numberOfPositions) {
            List<Operator[]> result = new ArrayList<>();
            int totalCombinations = calculateTotalCombinations(numberOfPositions);
            for (int i = 0; i < totalCombinations; i++) {
                Operator[] combination = createCombination(i, numberOfPositions);
                result.add(combination);
            }
            return result;
        }

        private static int calculateTotalCombinations(int numberOfPositions) {
            return (int) Math.pow(Operator.values().length, numberOfPositions);
        }

        private static Operator[] createCombination(int index, int numberOfPositions) {
            Operator[] combination = new Operator[numberOfPositions];
            for (int i = 0; i < numberOfPositions; i++) {
                combination[i] = Operator.values()[index % Operator.values().length];
                index /= Operator.values().length;
            }
            return combination;
        }

        private static List<Operation> buildOperation(long[] values, List<Operator[]> allCombinations) {
            List<Operation> operations = new ArrayList<>();
            for (Operator[] combination : allCombinations) {
                operations.add(new Operation(values, combination));
            }
            return operations;
        }

        public long result() {
            return calculateWithConcatenation();
        }

        private long calculateWithConcatenation() {
            long result = values[0];
            for (int i = 0; i < operators.length; i++) {
                switch (operators[i]) {
                    case SUM -> result += values[i + 1];
                    case MULTIPLY -> result *= values[i + 1];
                    case CONCATENATION -> {
                        String newValue = Long.toString(result).concat(Long.toString(values[i + 1]));
                        result = Long.parseLong(newValue);
                    }
                }
            }
            return result;
        }

        private boolean containsConcatenation() {
            return Arrays.stream(operators)
                    .anyMatch(operator -> operator == Operator.CONCATENATION);
        }

        @Override
        public String toString() {
            return "Operation{" +
                    "values=" + Arrays.toString(values) +
                    ", operators=" + Arrays.toString(operators) +
                    '}';
        }
    }

    enum Operator {
        SUM,
        MULTIPLY,
        CONCATENATION
    }
}
