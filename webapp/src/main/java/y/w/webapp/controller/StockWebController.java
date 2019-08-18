package y.w.webapp.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import y.w.webapp.error.BadRequestException;
import y.w.webapp.error.NotFoundException;
import y.w.webapp.model.Stock;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * EchoWebController
 *
 * @author ywang
 * @date 8/6/2019
 */
@Log4j
@Controller
@RequestMapping("/stocks")
public class StockWebController
{
    private Validator validator;

    private static final List<Stock> stocks = new ArrayList<>();

    static {
        stocks.add(new Stock("APPL", 100, new Date()));
        stocks.add(new Stock("FISV", 100, new Date()));
        stocks.add(new Stock("MOT", 100, new Date()));
    }

    public StockWebController()
    {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @GetMapping("/500")
    public void error()
    {
        // Can be managed:
        // server.error.include-exception=true
        // server.error.include-stacktrace=awalys
        throw new BadRequestException("500", "500 Internal Server Error - Threw BadRequestException.");
    }

    @GetMapping("notfound")
    public void notFound()
    {
        // The exception handler will take over.
        throw new NotFoundException("404", "404 Not Found - NotFoundException");
    }

    @RequestMapping
    public String allStocks(Model model)
    {
        model.addAttribute("stocks", stocks);
        return "listStocks"; // Expecting a view list.html
    }

    @RequestMapping("/addstock")
    public String setup_Submit(Model model)
    {
        return "addStock";
    }

    // We're building a simple web. So no service here.

    @ModelAttribute(name = "stock")
    public Stock stockBean()
    {
        return new Stock();
    }

    /**
     *
     * @param stock Spring MVC will get the data from HTML form and assemble it as an object of Stock and
     *              pass it in as a Model attribute.
     *
     *              Must declare a @ModelAttribute as above.
     * @param stock @Valid tells Spring MVC to validate the values. Stock has validation rules.
     * @return
     */
    @PostMapping("/addstock")
    public ModelAndView submit(@Valid @ModelAttribute("stock") Stock stock, BindingResult result, SessionStatus status)
    {
        Set<ConstraintViolation<Stock>> violations = validator.validate(stock);

        for (ConstraintViolation<Stock> violation : violations)
        {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            // Add JSR-303 errors to BindingResult
            // This allows Spring to display them in view via a FieldError
            result.addError(new FieldError("stock", propertyPath,
                    "Invalid "+ propertyPath + "(" + message + ")"));
        }

        if (result.hasErrors())
        {
            return new ModelAndView("addStock");
        }

        // Do something with services...
        stocks.add(stock);

        ModelAndView model = new ModelAndView("listStocks"); // the view name to go
        model.addObject("stocks", stocks);                // passing model data to the view
        return model;
    }

    /**
     * This ExceptionHandler only works for this controller. See ControllerExceptionHandler
     * for ExceptionHandler for all the other controller which are not annotated with @ExceptionHandler.
     * @param e
     * @return
     */
    @ExceptionHandler
    public ModelAndView handleDefault(NotFoundException e)
    {
        log.info("From @ExceptionHandler in " + this.getClass().getName());

        ModelAndView model = new ModelAndView("error/exception"); // View name
        model.addObject("exception", e);
        return model;
    }

    /**
     * To handle formating Date value from form.
     *
     * @param binder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addCustomFormatter(new DateFormatter("yyyy-MM-dd"));
    }
}
