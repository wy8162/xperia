package y.w.spring.context;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

/**
 * P1
 *
 * @author ywang
 * @date 8/20/2019
 */
@Log4j
public class TestBeans
{
    @Setter
    @Getter
    public static class BeanInParent
    {
        private String me;

        public BeanInParent()
        {
            this.me = "PBean1 from parent context";
        }
    }

    @Setter
    @Getter
    public static class BeanInChind
    {
        private String me;

        public BeanInChind()
        {
            this.me = "PBean2 from parent context.";
        }
    }

    @Setter
    @Getter
    public static class ScopedBean
    {
        private String me;
        public ScopedBean()
        {
            this.me = "ScopedBean";
        }

        public void doSomething()
        {
            log.info("From within ScopedBean");
        }
    }
}
