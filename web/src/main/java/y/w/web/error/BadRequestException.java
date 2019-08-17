package y.w.web.error;

/**
 * NotFoundException
 *
 * @author ywang
 * @date 8/7/2019
 */
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
