package y.w.j8.lambda;

import org.junit.Test;

/**
 * EffectiveFinal
 *
 * @author ywang
 * @date 9/23/2019
 */
public class EffectiveFinal
{
    private final String finalVar = "Some information";

    @Test
    public void whatIsEffectiveFinal()
    {
        String effectiveFinalName = "WYANG";
        String nonEffectiveFinalSomething = "Hmmmm";

        nonEffectiveFinalSomething = nonEffectiveFinalSomething + "Hmmmm again";
        Runnable r = () -> {
            System.out.println(finalVar);           // OK
            System.out.println(effectiveFinalName); // OK

            // the following line will get compilation error: not final or non effective final
            // System.out.println(nonEffectiveFinalSomething);
        };
    }
}
