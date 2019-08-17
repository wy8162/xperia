package y.w.spring.beans;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;

@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Person
    implements BeanFactoryAware, BeanNameAware, MessageSourceAware, ApplicationContextAware
{
    String name;
    int    age;

    public Person(String name, int age)
    {
        this.name = name;
        this.age = age;
    }

    public void init()
    {
        System.out.println("Lifecycle Stage - init()");
    }

    public void destroy()
    {
        System.out.println("Lifecycle Stage - destroy()");
    }

    public void hiThere()
    {
        System.out.println(this.toString());
    }

    @Override public void setBeanFactory(BeanFactory beanFactory) throws BeansException
    {
        System.out.println("Lifecycle Stage - setBeanFactory " + beanFactory.getClass());
    }

    @Override public void setBeanName(String name)
    {
        System.out.println("Lifecycle Stage - setBeanName " + name);
    }

    @Override public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        System.out.println("Lifecycle Stage - setApplicationContext " + applicationContext.getClass());
    }

    @Override public void setMessageSource(MessageSource messageSource)
    {
        System.out.println("Lifecycle Stage - setMessageSource " + messageSource.getClass());
    }
}
