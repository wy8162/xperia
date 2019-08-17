package y.w.j8.j8tests;

import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.function.Predicate;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class TryResourcesAndFunctionalInterfaceTest {
    private static final String csvFile = "/testFile.csv";

	@Test
	public void contextLoads() {
	}

	@Test
	public void testPredicate() {
        Predicate<String> predicate = (String line) -> line.contains("OKALOOSA COUNTY");
        try (
                InputStream in = Files.newInputStream(Paths.get(getClass().getResource(csvFile).toURI()));
                BufferedReader br = new BufferedReader(new InputStreamReader(in))
            )
        {
            String line = br.readLine();
            while (line != null) {
                if (predicate.test(line)) {
                    System.out.println(line);
                }
                line = br.readLine();
            }
		} catch (IOException e) {
		    System.out.println(e.toString());
        } catch (URISyntaxException e) {
		    System.out.println(e.toString());
        }
	}

    @Test
    public void testConsumer() {
        Consumer<String> consumer = (String line) -> System.out.println(line);
        try (
                InputStream in = Files.newInputStream(Paths.get(getClass().getResource(csvFile).toURI()));
                BufferedReader br = new BufferedReader(new InputStreamReader(in))
        )
        {
            String line = br.readLine();
            while (line != null) {
                consumer.accept(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        } catch (URISyntaxException e) {
            System.out.println(e.toString());
        }
    }
}
