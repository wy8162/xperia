package y.w.spring.context;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * SpringApplicationContextTest
 *
 * @author ywang
 * @date 8/20/2019
 */
public class SpringApplicationContextTest
{
    AnnotationConfigApplicationContext pCtx;
    AnnotationConfigApplicationContext cCtx;

    @Before
    public void setUp()
    {
        pCtx = new AnnotationConfigApplicationContext(ParentContextConfig.class);
        cCtx = new AnnotationConfigApplicationContext(ChildContextConfig.class);
        cCtx.setParent(pCtx);
    }

    @Test
    public void parentChildApplicationContextTest()
    {
        // beanInParent is accessible from Parent context
        assertThat(pCtx.getBean("beanInParent", TestBeans.BeanInParent.class)).isNotNull();

        // beanInChind is not accessible from Parent context
        assertThatExceptionOfType(NoSuchBeanDefinitionException.class).isThrownBy(() ->
                assertThat(pCtx.getBean("beanInChind", TestBeans.BeanInChind.class)).isNull()
        );

        // Both beanInChind and beanInParent are available from the child context.
        assertThat(cCtx.getBean("beanInParent", TestBeans.BeanInParent.class)).isNotNull();
        assertThat(cCtx.getBean("beanInChind", TestBeans.BeanInChind.class)).isNotNull();
    }

    @Test
    public void scopedBeanTest()
    {
        TestBeans.ScopedBean sb = cCtx.getBean("scopedBean", TestBeans.ScopedBean.class);

        sb.doSomething();
    }
}
