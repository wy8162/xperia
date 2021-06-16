package y.w.effectivejava.skeletal;

public interface ClearingChannel
{
    String getClearingType();
    void shutDown();
    void start();
}
