package y.w.effectivejava.skeletal;

/**
 * AbstractClearingChannelType - Skeletal implementation which implements
 * the common methods.
 *
 * @author ywang
 * @date 9/19/2019
 */
public abstract class AbstractClearingChannelType implements ClearingChannel, ReturnRule
{
    @Override public void shutDown()
    {
    }

    @Override public void start()
    {
    }

    @Override public boolean canReturn()
    {
        return true;
    }
}
