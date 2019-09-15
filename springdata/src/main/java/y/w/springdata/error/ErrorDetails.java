package y.w.springdata.error;

import java.util.List;

/**
 * Error
 */
public class ErrorDetails
{
    private String       message;
    private List<String> details = null;

    public ErrorDetails(String message, List<String> details)
    {
        this.message = message;
        this.details = details;
    }

    public ErrorDetails(String message)
    {
        this.message = message;
    }
}
