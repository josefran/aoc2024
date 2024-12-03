package avc2024;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class InputProvider {
    static List<String> input(String resourcePathString) {
        try {
            ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
            Path path = Paths.get(Objects.requireNonNull(systemClassLoader.getResource(resourcePathString + "/input")).toURI());
            return Files.readAllLines(path);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    static String inputToString(String resourcePathString) {
        try {
            ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
            Path path = Paths.get(Objects.requireNonNull(systemClassLoader.getResource(resourcePathString + "/input")).toURI());
            return Files.readString(path);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}