package y.w.effectivejava.skeletal;

/**
 * SCTClearingTransactionType
 *
 * @author ywang
 * @date 9/19/2019
 */
public class SCTClearingTransactionType extends AbstractClearingChannelType
{
    @Override public String getClearingType()
    {
        return "SCT";
    }

    @Override public void returnPayment(int paymentId)
    {
    }
}
