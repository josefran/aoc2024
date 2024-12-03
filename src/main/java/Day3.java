import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {

    public int executePart1(List<String> lines) {
        List<Multiply> reports = buildMultiply(lines);
        return 0;
    }

    public int executePart2(List<String> lines) {
        return 0;
    }

    private static List<Multiply> buildMultiply(List<String> lines) {
        List<Multiply> multiplies = new LinkedList<>();
        for (String line : lines) {
            multiplies.addAll(Multiply.create(line));
        }
        return multiplies;
    }

    record Multiply(int a, int b) {
        static List<Multiply> create(String line) {
            Pattern pattern = Pattern.compile("/mul\\((?'a'\\d+),(?'b'\\d+)\\)/gm");
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
    }

}
