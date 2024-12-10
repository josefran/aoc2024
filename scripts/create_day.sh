#!/bin/bash

# Use the current day if no day number is provided
if [ -z "$1" ]; then
  DAY_NUMBER=$(date +%d)
else
  DAY_NUMBER=$1
fi

DAY_NUMBER=$1
DAY_CLASS="Day${DAY_NUMBER}"
TEST_CLASS="Day${DAY_NUMBER}Test"
PACKAGE="aoc2024"
SRC_DIR="../src/main/java/${PACKAGE}"
TEST_DIR="../src/test/java/${PACKAGE}"

# Create the DayN class
cat <<EOL > ${SRC_DIR}/${DAY_CLASS}.java
package ${PACKAGE};

public class ${DAY_CLASS} implements Day {
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
EOL

# Create the DayNTest class
cat <<EOL > ${TEST_DIR}/${TEST_CLASS}.java
package ${PACKAGE};

public class ${TEST_CLASS} extends DayTest {
    @Override
    protected Day dayInstance() {
        return new ${DAY_CLASS}();
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
EOL

echo "Scaffolding for ${DAY_CLASS} and ${TEST_CLASS} created successfully."