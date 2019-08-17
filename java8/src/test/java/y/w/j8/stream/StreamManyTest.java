package y.w.j8.stream;

import y.w.j8.j8tests.SalesTransaction;
import y.w.cvs.CsvLineParser;
import y.w.cvs.GenericObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class StreamManyTest {
    private static final String[] fields = {
            "id", "orderID", "orderDate", "shipDate", "shipMode", "customerId", "customerName", "segment", "city", "state", "zipCode", "region", "productId", "category", "subCategory", "productName", "sales", "quantity", "discount", "profit"
    };

    private static final String csvFile = "/salesTransactions.csv";

    @Test
    public void testCount() {
        Long count = getLines()
                .skip(1) // Skip the row for header
                .parallel()
                .map(CsvLineParser::parse) // Split the comma separated string
                .map(s -> GenericObjectMapper.getInstance(SalesTransaction.class, fields, s))
                .collect(Collectors.counting());

        System.out.println("Total number = " + count);
    }

    @Test
    public void testReducingVsSum() {
        Double sum = getLines()
                .skip(1) // Skip the row for header
                .parallel()
                .map(CsvLineParser::parse) // Split the comma separated string
                .map(s -> GenericObjectMapper.getInstance(SalesTransaction.class, fields, s))
                .collect(reducing(0.0, t -> t.getSales().doubleValue(), Double::sum));

        System.out.println("Total sales = " + sum);
    }

    @Test
    public void testMaxMinSummary() {
        Comparator<SalesTransaction> comparator = Comparator.comparingDouble(SalesTransaction::getSales);
        Optional<SalesTransaction> max = getLines()
                .skip(1) // Skip the row for header
                .parallel()
                .map(CsvLineParser::parse) // Split the comma separated string
                .map(s -> GenericObjectMapper.getInstance(SalesTransaction.class, fields, s))
                .collect(maxBy(comparator));

        Optional<SalesTransaction> min = getLines()
                .skip(1) // Skip the row for header
                .parallel()
                .map(CsvLineParser::parse) // Split the comma separated string
                .map(s -> GenericObjectMapper.getInstance(SalesTransaction.class, fields, s))
                .collect(minBy(comparator));

        DoubleSummaryStatistics sum = getLines()
                .skip(1)
                .parallel()
                .map(CsvLineParser::parse)
                .map(s -> GenericObjectMapper.getInstance(SalesTransaction.class, fields, s))
                .collect(summarizingDouble(SalesTransaction::getSales));

        LongSummaryStatistics qty = getLines()
                .skip(1)
                .parallel()
                .map(CsvLineParser::parse)
                .map(s -> GenericObjectMapper.getInstance(SalesTransaction.class, fields, s))
                .collect(summarizingLong(SalesTransaction::getQuantity));

        System.out.println("Max sales = " + (max.isPresent() ? max.get().getSales() : "None") );
        System.out.println("Min sales = " + (min.isPresent() ? min.get().getSales() : "None") );
        System.out.println("Min sales = " + min.orElse(new SalesTransaction()) ); // Return a default value if none
        System.out.println("Sales summary: " + sum );
        System.out.println("Sales summary: " + qty );
    }

    private Stream<String> getLines() {
        try {
            return Files.lines(Paths.get(getClass().getResource(csvFile).toURI()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Test
    public void testJoining() {
        String cities = getLines()
                .skip(1) // Skip the row for header
                .parallel()
                .map(CsvLineParser::parse) // Split the comma separated string
                .map(s -> GenericObjectMapper.getInstance(SalesTransaction.class, fields, s))
                .map(SalesTransaction::getCity)
                .distinct()
                .collect(joining(","));

        System.out.println("Cities = " + cities);
    }

    @Test
    public void testGrouping() {
        Map<String, List<SalesTransaction>> list = getLines()
                .skip(1) // Skip the row for header
                .parallel()
                .map(CsvLineParser::parse) // Split the comma separated string
                .map(s -> GenericObjectMapper.getInstance(SalesTransaction.class, fields, s))
                .collect(groupingBy(SalesTransaction::getRegion));
                // Another way to group with a lambda
                //.collect(groupingBy(s -> {....}));

        System.out.println("Regions = " + list.keySet());
    }

    @Test
    public void testMultilevelGrouping() {
        Map<String, Map<String, List<SalesTransaction>>> list = getLines()
                .skip(1) // Skip the row for header
                .parallel()
                .map(CsvLineParser::parse) // Split the comma separated string
                .map(s -> GenericObjectMapper.getInstance(SalesTransaction.class, fields, s))
                .collect(groupingBy(SalesTransaction::getRegion, groupingBy(SalesTransaction::getCity)));
    }

    @Test
    public void testMultilevelGroupingCollecting() {
        // Find the one for each region, which has the maximum sales number
        Map<String, SalesTransaction> list = getLines()
                .skip(1) // Skip the row for header
                .parallel()
                .map(CsvLineParser::parse) // Split the comma separated string
                .map(s -> GenericObjectMapper.getInstance(SalesTransaction.class, fields, s))
                .collect(groupingBy(SalesTransaction::getRegion, collectingAndThen(maxBy(Comparator.comparingDouble(SalesTransaction::getSales)), Optional::get)));
    }

    @Test
    public void testPartitioning() {
        // Split / partition the list into a) list of all with sales > 10000 and
        // b) all the low value transactions with sales < 1000.
        Map<Boolean, List<SalesTransaction>> list = getLines()
                .skip(1) // Skip the row for header
                .parallel()
                .map(CsvLineParser::parse) // Split the comma separated string
                .map(s -> GenericObjectMapper.getInstance(SalesTransaction.class, fields, s))
                .collect(partitioningBy(s -> s.getSales() > 10000.00));
    }
}
