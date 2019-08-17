package y.w.spring.beans;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeManager
{
    public Person getEmployeeById(Integer employeeId)
    {
        System.out.println("Method getEmployeeById() called");
        return new Person();
    }

    public List<Person> getAllEmployee()
    {
        System.out.println("Method getAllEmployee() called");
        return new ArrayList<Person>();
    }

    public void createEmployee(Person employee)
    {
        System.out.println("Method createEmployee() called");
    }

    public void deleteEmployee(Integer employeeId)
    {
        System.out.println("Method deleteEmployee() called");
    }

    public void updateEmployee(Person employee)
    {
        System.out.println("Method updateEmployee() called");
    }
}