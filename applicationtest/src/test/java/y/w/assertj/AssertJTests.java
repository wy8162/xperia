package y.w.assertj;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class AssertJTests {
    @Test
    public void test1() {
        assertThat("wyang".length()).isEqualTo(5);
        assertThat(1).isNotEqualTo(2);

        assertThat("123ca")
            .startsWith("1")
            .endsWith("a")
            .isNotEqualTo("abc");

        assertThat(1)
            .isIn(Arrays.asList(1,2,3));

        assertThat(new User("yang", "Herndon"))
            .usingRecursiveComparison()
            .isEqualTo(new User("yang", "Herndon"));

        List<Integer> nums = Arrays.asList(1,2,3,4);
        assertThat(nums)
            .as("Nums should contain 1")
            .contains(1);
    }

    class User {
        private final String name;
        private final String location;

        public User(String name, String location) {
            this.name = name;
            this.location = location;
        }

        public String getName() {
            return name;
        }

        public String getLocation() {
            return location;
        }
    }
}
