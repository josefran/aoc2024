# Advent of Code 2024

Link: [Advent of Code 2024](https://adventofcode.com/2024)

## Project Structure

```
aoc2004/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── aoc2024/
│   │           ├── Day1.java
│   │           ├── ...
│   │           └── DayN.java
│   └── test/
│       ├── java/
│       │   └── aoc2024/
│       │       ├── Day1Test.java
│       │       ├── ...
│       │       └── DayNTest.java
│       └── resources/
│           ├── day1/
│           │    └── input
│           ├── ...
│           └── dayN/
│               └── input
├── scripts/
│   ├── create_day.sh
│   ├── download_input.sh
│   ├── create_day.ps1
│   ├── download_input.ps1
├── .mvn/
│   └── wrapper/
│       └── maven-wrapper.properties
├── mvnw
├── mvnw.cmd
└── pom.xml
```

## Requirements and Dependencies

- Java 21 or higher
- Maven 3.9.9 or higher (Maven Wrapper is included)
- Bash (for running bash scripts)
- PowerShell (for running PowerShell scripts)

## Scripts

### Bash Scripts

To run the bash scripts, use the following commands:

```bash
cd scripts
./create_day.sh <day_number>
./download_input.sh <day_number> <session_cookie>
```

### PowerShell Scripts

To run the PowerShell scripts, use the following commands:

```powershell
cd scripts
./create_day.ps1 <day_number>
./download_input.ps1 <day_number> <session_cookie>
```

## Usage

Ensure you have the necessary permissions to execute the scripts. You may need to modify the permissions using:

```bash
chmod +x create_day.sh download_input.sh
```

For PowerShell scripts, you may need to set the execution policy:

```powershell
Set-ExecutionPolicy RemoteSigned -Scope CurrentUser
```

## Maven Wrapper

To use the Maven Wrapper, you can run the following commands:

```bash
./mvnw clean install
```

## Running Tests

To run the tests, use the following command:

```bash
./mvnw test
```
