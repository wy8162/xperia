package y.w.webapp.service;

import org.springframework.stereotype.Service;
import y.w.webapp.model.Employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * EmployeeService
 *
 * @author ywang
 * @date 8/16/2019
 */
@Service
public class EmployeeService
{
    private List<Employee> employees = new ArrayList<>();

    public EmployeeService()
    {
        this.employees.addAll(
                Arrays.asList(
                new Employee(1, "Jack", "Wang", "jackw@gmail.com"),
                new Employee(1, "Jane", "Wan",  "janew@gmail.com"),
                new Employee(1, "Yang", "Wang", "yangw@gmail.com")
                ));

    }

    public List<Employee> getAllEmployees()
    {
        return this.employees;
    }

    public void addEmployee(Employee employee)
    {
        employees.add(employee);
    }
}
