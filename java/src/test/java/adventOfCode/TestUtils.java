package adventOfCode;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class TestUtils {

    private static InputStream getFileAsStream(String fileName) {
        return TestUtils.class.getClassLoader().getResourceAsStream(fileName);
    }

    public static List<String> getLines(String fileName) {
        return new BufferedReader(new InputStreamReader(
            getFileAsStream(fileName),
            StandardCharsets.UTF_8
        )).lines().toList();
    }

    public static String getContent(String fileName) {
        return new BufferedReader(new InputStreamReader(
            getFileAsStream(fileName),
            StandardCharsets.UTF_8
        )).lines().collect(Collectors.joining("\n"));
    }
}
