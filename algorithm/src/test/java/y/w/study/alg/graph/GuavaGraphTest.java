package y.w.study.alg.graph;

import com.google.common.collect.Collections2;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import org.junit.Test;

public class GuavaGraphTest {
    @Test
    public void simpleGraphTest() {
        String LIVINGSTON = "Livingston";
        String NEWARK = "Newark";
        String JERSEYCITY = "Jersey City";
        String NYC = "NYC";

        MutableGraph<String> cities = GraphBuilder.directed().build();

        cities.putEdge(LIVINGSTON, NEWARK);
        cities.putEdge(LIVINGSTON, JERSEYCITY);
        cities.putEdge(JERSEYCITY, NEWARK);
        cities.putEdge(NEWARK, NYC);

        Set<String> allNodes = cities.nodes();
        allNodes = cities.adjacentNodes(LIVINGSTON);
    }
}
