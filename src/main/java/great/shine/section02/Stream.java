package great.shine.section02;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Stream {
    public static void main(String[] args) {
        String contents = "Java 8 is a revolutionary release of the world’s #1 development platform.";

        List<String> words = Arrays.asList(contents.split("[\\P{L}]+"));

        System.out.println("1. use stream instead of iteration");
        long count = words.stream().filter(w -> w.length() > 5).count();
        System.out.println(count);
        System.out.println();
        System.out.println();

        System.out.println("2. use parallel stream instead of iteration");
        count = words.parallelStream().filter(w -> w.length() > 5).count();
        System.out.println(count);
        System.out.println();
        System.out.println();
    }
}
