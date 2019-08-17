package y.w.webapp.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import y.w.webapp.error.BadRequestException;
import y.w.webapp.error.NotFoundException;
import y.w.webapp.model.Stock;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * EchoWebController
 *
 * @author ywang
 * @date 8/6/2019
 */
@Log4j
@Controller
@RequestMapping("/stock")
public class SimpleWebController
{
    private static final List<Stock> stocks = new ArrayList<>();

    static {
        stocks.add(new Stock("APPL", 100, new Date()));
        stocks.add(new Stock("FISV", 100, new Date()));
        stocks.add(new Stock("MOT", 100, new Date()));
    }

    @RequestMapping
    public String home(Model model)
    {
        model.addAttribute("greeting", "Welcome to Stock Store");
        model.addAttribute("tagline", "Stock Portfolio Management");

        return "index";
    }

    @GetMapping("stocks/500")
    public void error()
    {
        // Can be managed:
        // server.error.include-exception=true
        // server.error.include-stacktrace=awalys
        throw new BadRequestException("7777", "Damn it, NPE Exception");
    }

    @GetMapping("notfound")
    public void notFound()
    {
        // The exception handler will take over.
        throw new NotFoundException("8877", "Damn it, not found");
    }

    @RequestMapping("/stocks")
    public String allStocks(Model model)
    {
        model.addAttribute("stocks", stocks);
        return "list"; // Expecting a view list.html
    }

    @RequestMapping("/addstock")
    public String addstock(Model model)
    {
        return "submit";
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
     * @param errors
     * @return
     */
    @PostMapping("/submit")
    public ModelAndView submit(@Valid @ModelAttribute(value="stock") Stock stock, Errors errors)
    {
        if (errors.hasErrors())
        {
            ModelAndView model = new ModelAndView("submit");
            model.addObject("errors", errors);
            return model;
        }

        // Do something with services...
        stocks.add(stock);

        ModelAndView model = new ModelAndView("list"); // the view name to go
        model.addObject("stocks", stocks);          // passing model data to the view
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
}
