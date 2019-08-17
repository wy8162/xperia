package y.w.spring.AOP;

import y.w.spring.beans.Animal;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect
public class TrackingDogAspect
{
    private Map<String, List<String>> dietTracking = new HashMap<>();

    @Pointcut("execution(String y.w.spring.beans.Animal.eat(String)) " +
                    "&& args(food)")
    public void trackingEatingPointCut(String food) {}

    @Pointcut("execution(* y.w.spring.beans.Animal.hiThere(String)) " +
            "&& args(message)")
    public void trackingSayHiPointCut(String message) {}

    @After("trackingSayHiPointCut(message)")
    public void sayHi(JoinPoint joinPoint, String message) throws Throwable
    {
        System.out.println(this.getClass().getName() + " @After-->" + joinPoint.getSignature().getName() + ": Before Method Execution");
        Animal animal = (Animal)joinPoint.getTarget();
        List<String> foods = dietTracking.get(animal.getName());
        if (foods == null)
            foods = Arrays.asList("Nothing");
        System.out.println(animal.toString() + " has eaten " + StringUtils.join(foods,","));
    }

    @Around("trackingEatingPointCut(food)")
    public String trackDiet(ProceedingJoinPoint joinPoint, String food) throws Throwable
    {
        System.out.println(this.getClass().getName() + " @Around beginning-->" + joinPoint.getSignature().getName() + ": Before Method Execution");
        Animal animal = (Animal)joinPoint.getTarget();

        List<String> foods = dietTracking.get(animal.getName());
        if (foods == null)
        {
            foods = new ArrayList<>();
            dietTracking.put(animal.getName(), foods);
        }
        foods.add(food);

        String result;
        try {
            result = (String) joinPoint.proceed(new Object[]{food});
        } finally {
            //Do Something useful, If you have
        }
        System.out.println(this.getClass().getName() + " @Around end-->" + joinPoint.getSignature().getName() + ": After Method Execution");

        return result;
    }
}
