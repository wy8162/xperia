package y.w.effectivejava.skeletal;

/**
 * SCTClearingTransactionTypeDelegated
 *
 * @author ywang
 * @date 9/19/2019
 */
public class SCTClearingTransactionTypeDelegated
        implements ClearingChannel, ReturnRule
{
    private class SCTDelegator extends AbstractClearingChannelType
    {

        @Override public String getClearingType()
        {
            return "SCT";
        }

        @Override public void returnPayment(int paymentId)
        {
        }
    }

    private SCTDelegator delegator = new SCTDelegator();

    @Override public String getClearingType()
    {
        return delegator.getClearingType();
    }

    @Override public void shutDown()
    {
        delegator.shutDown();
    }

    @Override public void start()
    {
        delegator.start();
    }

    @Override public boolean canReturn()
    {
        return delegator.canReturn();
    }

    @Override public void returnPayment(int paymentId)
    {
        delegator.returnPayment(paymentId);
    }
}
