package y.w.spring;

import y.w.spring.beans.Animal;
import y.w.spring.beans.EmployeeManager;
import y.w.spring.beans.Person;
import y.w.spring.beans.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAspectJAutoProxy
public class AspectTest
{
    @Autowired Student student;

    @Autowired EmployeeManager manager;

    @Autowired Animal dog;

    @Test public void loadContextTest()
    {
        student.getAge();
        student.getName();

        manager.getEmployeeById(1);
        manager.createEmployee(new Person());
    }

    /**
     * Tests aspects with arguments, etc.
     */
    @Test
    public void testDog()
    {
        dog.hiThere("Good morning");
        dog.eat("Meat");
        dog.eat("Vegitable");
        dog.eat("Beef");
        dog.hiThere("Got food?");
    }
}
