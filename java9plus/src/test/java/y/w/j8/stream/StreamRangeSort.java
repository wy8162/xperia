package y.w.j8.stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;
import org.junit.Test;

public class StreamRangeSort {
    @Test
    public void testSort() {
        IntStream.range(0, 10)
            .map(i -> i * 2)
            .sorted()
            .toArray();

        int[] starts = new int[]{8,10,9,12,11,12};
        int[] ends   = new int[]{13,11,10,16,15,14};

        Event[] events = IntStream.range(0, starts.length)
            .mapToObj(i -> new Event(starts[i], ends[i]))
            .sorted(Comparator.comparing(Event::getEnd))
            .toArray(Event[]::new);

        Arrays.stream(events)
            .forEach(System.out::println);
    }

    static class Event {
        private final int start, end;

        public Event(int s, int e) {
            this.start = s;
            this.end = e;
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }

        @Override
        public String toString() {
            return "Event{" +
                "start=" + start +
                ", end=" + end +
                '}';
        }
    }

}
