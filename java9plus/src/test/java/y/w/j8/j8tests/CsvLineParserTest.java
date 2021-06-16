package y.w.j8.j8tests;

import org.junit.Test;
import y.w.cvs.CsvLineParser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class CsvLineParserTest {
	private static final String line1  = null;
	private static final String line2  = "";
	private static final String line3  = ",";
	private static final String line4  = ",,";
	private static final String line5  = ",1,";
	private static final String line6  = ",,1,2,,";
	private static final String line7  = "\"\"";
	private static final String line8  = "\"\",,1,2,,\"\"";
	private static final String line9  = "1,2,\"3,4\"";
	private static final String line10 = "1,2,3,4";

	// In CSV data, if the data contains '"', it will be saved as '""'. So, "" will be converted
    // to " as data instead of quote.
	private static final String line11 = "1,\"the dimensions of the paper is, 1'-2\"\" long\",4";

	@Test
	public void contextLoads() {
	}

	@Test
	public void testLine1_and_2() {
		assertNull(CsvLineParser.parse(line1));
		assertNull(CsvLineParser.parse(line2));
	}

	@Test
	public void testLine3_and_4() {
		String[] strings = CsvLineParser.parse(line3);
		assertEquals(strings.length, 2);
		assertEquals(strings[0].length(), 0);
		assertEquals(strings[1].length(), 0);

        strings = CsvLineParser.parse(line4);
        assertEquals(strings.length, 3);
        assertEquals(strings[0].length(), 0);
        assertEquals(strings[1].length(), 0);
        assertEquals(strings[2].length(), 0);
	}

    @Test
    public void testLine5() {
        String[] strings = CsvLineParser.parse(line5);
        assertEquals(strings.length, 3);
        assertEquals(strings[0].length(), 0);
        assertTrue(strings[1].equals("1"));
        assertEquals(strings[2].length(), 0);
    }

    @Test
    public void testLine6() {
        String[] strings = CsvLineParser.parse(line6);
        assertEquals(strings.length, 6);
        assertTrue(strings[2].equals("1"));
        assertTrue(strings[3].equals("2"));
    }

    @Test
    public void testLine7() {
        String[] strings = CsvLineParser.parse(line7);
        assertEquals(strings.length, 1);
        assertTrue(strings[0].equals("\""));
    }

    @Test
    public void testLine8() {
        String[] strings = CsvLineParser.parse(line8);
        assertEquals(strings.length, 6);
        assertTrue(strings[2].equals("1"));
        assertTrue(strings[3].equals("2"));
        assertTrue(strings[0].equals("\""));
        assertTrue(strings[5].equals("\""));
    }

    @Test
    public void testLine9() {
        String[] strings = CsvLineParser.parse(line9);
        assertEquals(strings.length, 3);
        assertTrue(strings[0].equals("1"));
        assertTrue(strings[1].equals("2"));
        assertTrue(strings[2].equals("3,4"));
    }

    @Test
    public void testLine10() {
        String[] strings = CsvLineParser.parse(line10);
        assertEquals(strings.length, 4);
        assertTrue(strings[0].equals("1"));
        assertTrue(strings[1].equals("2"));
        assertTrue(strings[2].equals("3"));
        assertTrue(strings[3].equals("4"));
    }

    @Test
    public void testLine11() {
        String[] strings = CsvLineParser.parse(line11);
        assertEquals(strings.length, 3);
        assertTrue(strings[0].equals("1"));
        assertTrue(strings[1].equals("the dimensions of the paper is, 1'-2\" long"));
        assertTrue(strings[2].equals("4"));
    }


}
