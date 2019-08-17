package y.w.j8.j8tests;

import y.w.cvs.CsvLineParser;
import y.w.cvs.GenericObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java.util.stream.Collectors.toList;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class GenericObjectMapperTest {
    private static final String[] fields = {
            "id", "orderID", "orderDate", "shipDate", "shipMode", "customerId", "customerName", "segment", "city", "state", "zipCode", "region", "productId", "category", "subCategory", "productName", "sales", "quantity", "discount", "profit"
    };

    private static final String csvFile = "/salesTransactions.csv";

    @Test
    public void testGenericObjectMapper() {
        try {
            List<SalesTransaction> salesTransactions = Files.lines(Paths.get(getClass().getResource(csvFile).toURI()))
                    .skip(1) // Skip the header row
                    .limit(10)
                    .parallel()
                    .map(CsvLineParser::parse) // Split the comma separated string
                    .map(s -> (SalesTransaction) GenericObjectMapper.getInstance(SalesTransaction.class, fields, s))
                    .collect(toList());

            for (SalesTransaction salesTransaction : salesTransactions) {
                System.out.println(salesTransaction);
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        } catch (URISyntaxException e) {
            System.out.println(e.toString());
        }
    }
}
