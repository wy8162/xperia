package y.w.j8.j8tests;

import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class StreamsTest {
    private static final String csvFile = "/testFile.csv";

    @Test
    public void contextLoads() {
    }

    @Test
    public void testStream() {
        Predicate<String> predicate = (String line) -> line.contains("OKALOOSA COUNTY");
        try {
            List<SaleOrder> sales = Files.lines(Paths.get(getClass().getResource(csvFile).toURI()))
                    .parallel()
                    .filter( line -> line.split(",").length == 11)
                    .map(SaleOrder::buildInstanceFromString)
                    .collect(toList());

            System.out.println("Total number of sales orders = " + sales.size());
        } catch (IOException e) {
            System.out.println(e.toString());
        } catch (URISyntaxException e) {
            System.out.println(e.toString());
        }
    }
}
