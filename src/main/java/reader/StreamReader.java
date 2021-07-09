package reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StreamReader {

    public static String readFile(String input) {

        String text = "";
        List<String> list = new ArrayList<>();
        try {
            list = Files.lines(Paths.get(input)).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String s : list) {
            text += s;
            text += "\n";
        }

        return text;
    }
}
