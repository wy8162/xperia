package y.w.springdata.error;

/**
 * ItemNotFoundException
 */
public class ItemNotFoundException extends RuntimeException
{
    public ItemNotFoundException(String message)
    {
        super(message);
    }
}
