package aoc2024;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class InputProvider {

    static String inputToString(String resourcePathString) {
        try {
            ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
            Path path = Paths.get(Objects.requireNonNull(systemClassLoader.getResource(resourcePathString)).toURI());
            return Files.readString(path);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}