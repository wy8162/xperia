package y.w.spring.proxy;

import org.junit.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * CGLIBProxyTest - test CGLIB proxy
 *
 * @author ywang
 * @date 9/10/2019
 */
public class CGLIBProxyTest
{
    @Test
    public void cglibProxyTest()
    {
        UserService userService = CGLIBProxy.bind(new UserServiceImpl(), UserService.class);
        System.out.println(userService.getName("Hello"));
    }

    public static class CGLIBProxy
    {
        public static <T> T bind(Object target, Class<T> classz)
        {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(target.getClass());
            enhancer.setCallback((MethodInterceptor)(obj, method, args, methodProxy) -> {
                Object returnValue;

                beforeInvoke();
                try
                {
                    returnValue = methodProxy.invokeSuper(obj, args);
                }
                catch (Throwable t)
                {
                    afterThrowable();
                    throw t;
                }
                finally
                {
                    afterInvoke();
                }
                return returnValue;
            });

            return (T) enhancer.create();
        }


        protected static void beforeInvoke()
        {
            System.out.println("Before invoke");
        }

        protected static void afterInvoke()
        {
            System.out.println("After invoke");
        }

        protected static void afterThrowable()
        {
            System.out.println("After throwable");
        }
    }
}
