package y.w.webapp.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import y.w.webapp.error.BadRequestException;

/**
 * ControllerExceptionHandler
 *
 * @ExceptionHandler annotated controller will only work for that controller.
 *
 * @ControllerAdvice will apply it for all the other controllers which are not
 * annotated by @ExceptionHandler.
 *
 * @author ywang
 * @date 8/7/2019
 */
@Log4j
@ControllerAdvice
public class ControllerExceptionHandler
{
    @ExceptionHandler
    public ModelAndView handleDefault(BadRequestException e)
    {
        log.info("From @ControllerAdvice");
        ModelAndView model = new ModelAndView("error/exception"); // View name
            model.addObject("exception", e);
            return model;
    }
}
