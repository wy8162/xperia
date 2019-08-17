package y.w.spring;

import y.w.spring.beans.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LifeCycleTest
{
    @Autowired
    @Qualifier("anotherPerson") Person person;

    @Test public void loadContextTest()
    {
        person.hiThere();
    }
}
