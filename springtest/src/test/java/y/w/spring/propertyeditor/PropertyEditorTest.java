package y.w.spring.propertyeditor;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * PropertyEditorTest
 *
 * @author ywang
 * @date 8/21/2019
 */
public class PropertyEditorTest
{
    @Test
    public void propertyEditorTest()
    {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring/propertyEditor-beans.xml");

        DependsOnExoticType bean = ctx.getBean("sample", DependsOnExoticType.class);
    }
}
