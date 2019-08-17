package y.w.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * HomeWebController
 *
 * @author ywang
 * @date 8/6/2019
 */
@RestController
@RequestMapping("/api")
public class SimpleRestController
{
    @GetMapping("/hi") // URL route and action GET
    public String welcome()
    {
        return "welcome";
    }
}
