package y.w.effectivejava.skeletal;

public interface ReturnRule
{
    boolean canReturn();
    void returnPayment(int paymentId);
}
