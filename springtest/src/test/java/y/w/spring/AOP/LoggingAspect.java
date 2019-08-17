package y.w.spring.AOP;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect
{
    /**
     * This is the method which I would like to execute
     * before a selected method execution.
     */
    @Before("execution(* y.w.spring.beans.Student.getName(..))")
    public void beforeAdvice(JoinPoint joinPoint)
    {
        System.out.println("Going to setup student profile." + joinPoint.getSignature().getName());
    }

    /**
     * This is the method which I would like to execute
     * after a selected method execution.
     */
    @After("execution(* y.w.spring.beans.Student.getAge(..))")
    public void afterAdvice(JoinPoint joinPoint)
    {
        System.out.println("Student profile has been setup." + joinPoint.getSignature().getName());
    }

    /**
     * This is the method which I would like to execute
     * when any method returns.
     */
    @AfterReturning(pointcut = "execution(* y.w.spring.beans.Student.get*(..))",
            returning="retVal")
    public void afterReturningAdvice(Object retVal)
    {
        System.out.println("Returning:" + retVal);
    }

    /**
     * This is the method which I would like to execute
     * if there is an exception raised.
     */
    public void AfterThrowingAdvice(IllegalArgumentException ex)
    {
        System.out.println("There has been an exception: " + ex.toString());
    }
}