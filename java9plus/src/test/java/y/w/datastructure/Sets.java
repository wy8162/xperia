package y.w.datastructure;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Sets {

    public static void main(String[] args) {
        Set<String> s = new HashSet<>();
        Set<String> t = new HashSet<>();

        s.addAll(Arrays.asList("a", "b", "c", "d"));
        t.addAll(Arrays.asList(          "c", "d", "e", "f"));

        s.retainAll(t); // union of set
        t.removeAll(s); // diff of set

        System.out.println(s); // expect "c", "d"
        System.out.println(t); // expect "e", "f"
    }

}
