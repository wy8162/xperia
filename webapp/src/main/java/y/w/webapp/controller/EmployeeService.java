package y.w.webapp.controller;

import com.google.common.collect.Lists;
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
    private final List<Employee> employees;

    public EmployeeService()
    {
        this.employees = Arrays.asList(
                new Employee(1, "Jack", "Wang", "jackw@gmail.com"),
                new Employee(1, "Jane", "Wan",  "janew@gmail.com"),
                new Employee(1, "Yang", "Wang", "yangw@gmail.com")
        );

    }

    public List<Employee> getAllEmployees()
    {
        return this.employees;
    }
}
