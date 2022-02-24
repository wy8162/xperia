package y.w.datastructure;

import java.util.HashMap;
import java.util.Map;

public class Maps {

    public static void main(String[] args) {
        Map<String, Integer> m = new HashMap<>();

        m.put("a", 1);
        m.computeIfAbsent("b", k -> {
            System.out.println(String.format("%s", k));
            return 2;
        });

        m.putIfAbsent("d", 4);

        m.put("c", 2);
        m.computeIfPresent("c", (k, v) -> {
            System.out.println(String.format("%s -> %d", k, v));
            return v * 2;
        });

        System.out.println(m);
    }
}
