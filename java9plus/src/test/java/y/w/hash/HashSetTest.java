package y.w.hash;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class HashSetTest {

    @Test
    public void testIt() {
        Person p1 = new Person("yang", 10);
        Person p2 = new Person("yang", 11);

        assertThat(p1).isEqualTo(p2);

        Set<Person> set = new HashSet<>();
        set.add(p1);
        set.add(p2);

        assertThat(set.size()).isEqualTo(1);

        assertThat(p2).isGreaterThan(p1);
    }


    static class Person implements Comparable<Person> {
        String name;
        int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Person) return this.name.equals(((Person) obj).name);
            return false;
        }

        @Override
        public int hashCode() {
            return this.name.hashCode();
        }

        @Override
        public int compareTo(Person o) {
            return this.age - o.age;
        }
    }
}
