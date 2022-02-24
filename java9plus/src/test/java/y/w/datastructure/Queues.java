package y.w.datastructure;

import java.util.ArrayDeque;
import java.util.Deque;

public class Queues {

    public static void main(String[] args) {
        Deque<Integer> q = new ArrayDeque<>();

        q.addFirst(1); // Throws exception if capacity is full, etc.
        q.addLast(3);
        q.getFirst(); // Retrieves but not remove
        q.removeFirst(); // Retrieve and remove. Throws exception if empty
        q.offerFirst(0); // True of False if successful or unsuccessful

        q.pollFirst(); // Returns null if empty.
    }

}
