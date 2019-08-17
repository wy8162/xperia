package y.w.restful.webservice;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * SimpleRestController
 *
 *  @RestController is equal to @Controller + @ResponseBody.
 *
 * @ResponseBody asks Spring to convert the response object to the request message type, per
 * request, like application/json, etc.
 *
 * Spring has a some default message converters like MappingJackson2HttpMessageConverter.
 *
 * @author ywang
 * @date 8/6/2019
 */
@RestController
@RequestMapping("/api")
public class SimpleRestController
{
    /**
     * Request param defines a query like below:
     *
     * http://host:port/api/hi?name=jack
     *
     * If there are multiple:
     *
     * http://host:port/api/hi?name=jack&address=Rihmond
     *
     * @param name
     * @return
     */
    @GetMapping("/hi")
    public ResponseEntity<String> sayHi(@RequestParam(value="name", defaultValue="World") String name)
    {
        return new ResponseEntity("hi, " + name, HttpStatus.OK);
    }

    /**
     * http://localhost:8080/api/params?fruits=apple,pear&meats=beef,pork
     * @param fruits
     * @param meats
     * @return
     */
    @SuppressWarnings("unchecked")
    @GetMapping("/params")
    public ResponseEntity multipleParams(
            @RequestParam(value="fruits") List<String> fruits,
            @RequestParam(value="meats") List<String> meats)
    {
        return new ResponseEntity("Fruits " +
                StringUtils.join(fruits, ",") + " " +
                StringUtils.join(meats, ",")
                , HttpStatus.OK);
    }
}
