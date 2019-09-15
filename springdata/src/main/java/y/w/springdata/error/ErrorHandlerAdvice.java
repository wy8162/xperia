package y.w.springdata.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ServiceErrorAdvice
 */
@ControllerAdvice
@Slf4j
public class ErrorHandlerAdvice
{

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ItemNotFoundException.class)
    public ErrorDetails handleNotFoundException(final Exception exception) {
        log.error(exception.getMessage());
        return new ErrorDetails(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class })
    @ResponseBody
    public ErrorDetails handleBadRequestException(final Exception exception) {
        log.error(exception.getMessage());
        return new ErrorDetails(exception.getMessage());
    }
}