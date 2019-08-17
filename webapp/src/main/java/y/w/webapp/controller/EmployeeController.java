package y.w.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import y.w.webapp.model.Employee;

@Controller
@RequestMapping("/employees")
@SessionAttributes("employee")
public class EmployeeController
{
    private final EmployeeValidator validator;
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeValidator validator, EmployeeService employeeService)
    {
        this.validator = validator;
        this.employeeService = employeeService;
    }

    @GetMapping(value = "/listAllEmployees")
    public String getAllEmployees(Model model)
    {
        model.addAttribute("employees", employeeService.getAllEmployees());

        return "listEmployees";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String setupForm(Model model)
    {
         Employee employeeVO = new Employee();
         model.addAttribute("employee", employeeVO);
         return "addEmployee";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submitForm(@ModelAttribute("employee") Employee employeeVO, BindingResult result, SessionStatus status) {

        validator.validate(employeeVO, result);

        if (result.hasErrors()) {
            return "addEmployee";
        }

        // Mark Session Complete
        status.setComplete();
        return "redirect:addNew/success";
    }
     
    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String success(Model model)
    {
         return "addSuccess";
    }
}