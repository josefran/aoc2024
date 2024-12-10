param (
    [int]$DayNumber = (Get-Date).Day
)

$DayClass = "Day$DayNumber"
$TestClass = "Day${DayNumber}Test"
$Package = "aoc2024"
$SrcDir = "../src/main/java/$Package"
$TestDir = "../src/test/java/$Package"

# Create the DayN class
$DayClassContent = @"
package $Package;

public class $DayClass implements Day {
    @Override
    public long executePart1(String input) {
        // Implement the logic for part 1
        return 0;
    }

    @Override
    public long executePart2(String input) {
        // Implement the logic for part 2
        return 0;
    }
}
"@

$DayClassContent | Out-File -FilePath "$SrcDir/$DayClass.java" -Encoding utf8

# Create the DayNTest class
$TestClassContent = @"
package $Package;

public class $TestClass extends DayTest {
    @Override
    protected Day dayInstance() {
        return new $DayClass();
    }

    @Override
    protected String part1ExampleInput() {
        return "";
    }

    @Override
    protected long part1ExampleResult() {
        return -1;
    }

    @Override
    protected long part1Result() {
        return -1;
    }

    @Override
    protected long part2ExampleResult() {
        return -1;
    }

    @Override
    protected long part2Result() {
        return -1;
    }
}
"@

$TestClassContent | Out-File -FilePath "$TestDir/$TestClass.java" -Encoding utf8

Write-Output "Scaffolding for $DayClass and $TestClass created successfully."