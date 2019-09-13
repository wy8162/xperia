package y.w.spring.proxy;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDKProxyTest - Test JDK dynamic based proxy
 *
 * @author ywang
 * @date 9/10/2019
 */
public class JDKProxyTest
{
    @Test
    public void jdkProxyTest()
    {
        UserService userService = JDKProxy.bind(new UserServiceImpl(), UserService.class);
        System.out.println(userService.getName("Hello"));
    }

    public static class JDKProxy implements InvocationHandler
    {
        private Object target;

        private JDKProxy(Object target)
        {
            this.target = target;
        }

        public static <T> T bind(Object o, Class<T> classz)
        {
            JDKProxy handler = new JDKProxy(o);
            Object proxy = Proxy.newProxyInstance(
                o.getClass().getClassLoader(),
                o.getClass().getInterfaces(),
                handler
            );

            return classz.cast(proxy);
        }

        @Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
        {
            beforeInvoke();
            Object result = method.invoke(target, args);
            afterInvoke();

            return result;
        }

        protected void beforeInvoke()
        {
            System.out.println("Before invoke");
        }

        protected void afterInvoke()
        {
            System.out.println("After invoke");
        }
    }

}
