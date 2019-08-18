package y.w.webapp.error;

import lombok.Getter;

/**
 * NotFoundException
 *
 * @author ywang
 * @date 8/7/2019
 */
@Getter
public class BadRequestException extends RuntimeException
{
    private String errorCode;
    private String errorMessage;

    public BadRequestException(String errorCode, String message)
    {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
    }
}
