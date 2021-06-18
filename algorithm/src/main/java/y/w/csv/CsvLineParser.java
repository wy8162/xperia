package y.w.csv;

import java.util.ArrayList;
import java.util.List;

/**
 * This utility class will parse CSV line which may have the format like below
 * "" - empty
 * "," - two empty columns
 * ",," - three empty columns
 * ",1,"
 * ",,1,2,,"
 * "\"\"" - two contiguous '"' will be treated as part of the data instead of quote
 * "\"\",,1,2,,\"\"";
 * "1,2,\"3,4\"";
 * "1,2,3,4";
 */
public class CsvLineParser {

    /*
      Split string separated by comma, which might have quotes of \".
     */
    public final static String[] parse(String line) {
        return parse(line, ',', '"');
    }

    public final static String[] parse(String line, char separator, char quote) {
        if (line == null || line.length() == 0) return null;

        List<String> stringList = new ArrayList<>();

        boolean isInQuotes = false;
        StringBuilder sb = new StringBuilder();

        int ptr = 0;
        char[] chars = line.toCharArray();
        while (ptr < chars.length) {
            char c = chars[ptr];
            if (c == separator) {
                if (isInQuotes) {
                    sb.append(c);
                } else {
                    stringList.add(sb.toString());
                    sb = new StringBuilder();
                }
            } else if (c == quote) {
                if ( ptr < chars.length - 1 && chars[ptr + 1] == quote) { // "" equals "
                    sb.append(c);
                    ptr += 1;
                } else { // Otherwise, single " means a quote
                    isInQuotes = !isInQuotes;
                }
            } else {
                sb.append(c);
            }
            ptr += 1;
        }
        stringList.add(sb.toString());
        return stringList.toArray(new String[stringList.size()]);
    }
}
