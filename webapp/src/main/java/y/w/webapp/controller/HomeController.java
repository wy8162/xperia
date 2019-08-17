package y.w.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * HomeController
 *
 * @author ywang
 * @date 8/16/2019
 */
@Controller
@RequestMapping("/")
public class HomeController
{

    @RequestMapping("/")
    public String welcome(Model model) {
        model.addAttribute("greeting", "Welcome to Test Store!");
        model.addAttribute("tagline", "The one and only amazing web store");

        return "index";
    }
}