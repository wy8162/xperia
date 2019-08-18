package y.w.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import y.w.webapp.model.Employee;
import y.w.webapp.validator.EmployeeValidator;

@Controller
@RequestMapping("/employees")
@SessionAttributes("employee")
public class EmployeeController
{
    private final EmployeeValidator validator;
    private final EmployeeService   employeeService;

    @Autowired
    public EmployeeController(EmployeeValidator validator, EmployeeService employeeService)
    {
        this.validator = validator;
        this.employeeService = employeeService;
    }

    @GetMapping
    public String getAllEmployees(Model model)
    {
        model.addAttribute("employees", employeeService.getAllEmployees());

        return "listEmployees";
    }

    @GetMapping("/addNew")
    public String setupForm(Model model)
    {
         Employee employeeVO = new Employee();
         model.addAttribute("employee", employeeVO);
         return "addEmployee";
    }

    @RequestMapping(value = "/addNew", method = RequestMethod.POST)
    public String submitForm(@ModelAttribute("employee") Employee employeeVO, BindingResult result, SessionStatus status) {

        validator.validate(employeeVO, result);

        if (result.hasErrors()) {
            return "addEmployee";
        }

        // Mark Session Complete
        status.setComplete();
        return "redirect:success";
    }
     
    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String success(Model model)
    {
         return "addSuccess";
    }
}