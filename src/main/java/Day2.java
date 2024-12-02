import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Day2 {

    public int executePart1(List<String> lines) {
        List<Report> reports = buildReports(lines);

        long numOfSecureReports = reports.stream()
                .filter(Report::isSecure)
                .count();

        return (int) numOfSecureReports;
    }

    public int executePart2(List<String> lines) {
        List<Report> reports = buildReports(lines);

        long numOfSecureReports = reports.stream()
                .filter(Report::isSecureWithTolerance)
                .count();

        return (int) numOfSecureReports;
    }

    private static List<Report> buildReports(List<String> lines) {
        List<Report> reports = new LinkedList<>();
        for (String line : lines) {
            line = line.trim();
            String[] levels = line.split("\\s+");
            reports.add(Report.create(levels));
        }
        return reports;
    }

    record Report(List<Integer> levels) {
        static Report create(String[] levels) {
            List<Integer> levelsList = Arrays.stream(levels).map(Integer::parseInt).toList();
            return new Report(levelsList);
        }

        boolean isSecure() {
            boolean isSecure = true;
            TypeLevel typeLevel = typeOfIncrement(0, 1);
            for (int i = 0; i < levels.size(); i++) {
                int nextIndex = i + 1;
                if (nextIndex < levels.size()) {
                    if (changeIncrement(typeLevel, i, nextIndex)) return false;
                    if (isDistanceExcessive(i, nextIndex)) return false;
                }
            }
            return isSecure;
        }

        boolean isSecureWithTolerance() {
            if (isSecure()) return true;
            List<Boolean> anySecure = new LinkedList<>();
            for (int i = 1; i < levels.size(); i++) {
                List<Integer> auxLevels = new LinkedList<>(levels);
                auxLevels.remove(i);
                anySecure.add(new Report(auxLevels).isSecure());
            }
            return anySecure.stream().anyMatch(s -> s);
        }

        private boolean isDistanceExcessive(int i, int nextIndex) {
            return !checkDistanceLessThan(i, nextIndex, 4);
        }

        private boolean changeIncrement(TypeLevel typeLevel, int i, int nextIndex) {
            return typeLevel != typeOfIncrement(i, nextIndex);
        }

        boolean checkDistanceLessThan(int i1, int i2, int limit) {
            return Math.abs(levels.get(i2) - levels.get(i1)) < limit;
        }

        TypeLevel typeOfIncrement(int  i1, int i2) {
            if (levels.get(i1).equals(levels.get(i2))) return TypeLevel.SAME_LEVEL;
            else if (levels.get(i1) - levels.get(i2) > 0) return TypeLevel.INCREASE;
            else return TypeLevel.DECREASE;
        }

    }

    enum TypeLevel {
        INCREASE,
        DECREASE,
        SAME_LEVEL
    }
}
