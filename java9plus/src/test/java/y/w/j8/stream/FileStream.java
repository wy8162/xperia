package y.w.j8.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.junit.Test;

public class FileStream {
    @Test
    public void t() throws IOException {
        try (Stream<String> lines = Files.lines(Path.of("/Users/yangwang/Downloads/sample4.csv")).parallel()) {
            lines.forEach(System.out::println);
        }

        try(Stream<Path> paths = Files.list(Path.of("/Users/yangwang/Downloads"))) {
            paths.filter(Files::isDirectory)
                .forEach(System.out::println);
        }

        try(Stream<Path> paths = Files.walk(Path.of("/Users/yangwang/Downloads"))) {
            paths.filter(Files::isDirectory)
                .forEach(System.out::println);
        }
    }

}
