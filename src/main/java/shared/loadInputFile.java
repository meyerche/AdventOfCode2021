package shared;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class loadInputFile {
    public static List<Integer> ReadFileInt(String filename) {
        InputStream is = loadInputFile.class.getClassLoader().getResourceAsStream(filename);
        if (is == null) throw new AssertionError();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        return reader.lines().map(Integer::parseInt).collect(Collectors.toList());
    }

    public static List<String> ReadFileString(String filename) {
        InputStream is = loadInputFile.class.getClassLoader().getResourceAsStream(filename);
        if (is == null) throw new AssertionError();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        return reader.lines().collect(Collectors.toList());
    }
}
