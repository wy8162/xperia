package y.w.spring.SpEL;

import org.junit.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * SpELTest
 *
 * @author ywang
 * @date 8/24/2019
 */
public class SpELTest
{
    @Test
    public void test1()
    {
        ExpressionParser parser = new SpelExpressionParser();

        Expression exp = parser.parseExpression("'Hello world'.concat('!')");

        System.out.println(exp.getValue());
    }
}
