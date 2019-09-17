/**
 * http://www.theserverside.com/news/1365050/Using-JMock-in-Test-Driven-Development
 * TDD - Step 2: Write the codes
 */
package y.w.tdd.bean;

import java.sql.Timestamp;

public interface Clock {
    public abstract Timestamp getCurrentTime();
}