package y.w.effectivejava.skeletal;

/**
 * SCTClearingTransactionType
 *
 * @author ywang
 * @date 9/19/2019
 */
public class SDDClearingTransactionType extends AbstractClearingChannelType
{
    @Override public String getClearingType()
    {
        return "SCT";
    }

    @Override public void returnPayment(int paymentId)
    {
    }
}
