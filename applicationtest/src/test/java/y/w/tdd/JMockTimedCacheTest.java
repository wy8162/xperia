/**
 * http://www.theserverside.com/news/1365050/Using-JMock-in-Test-Driven-Development
 * TDD - Step 1: Write the test case
 *      - TimeCache depends on ObjectLoader
 *      - The test should not depend on a concrete class of interface ObjectLoader
 *      - Uses a stub to mock the functionalitis of ObjectLoader
 */
package y.w.tdd;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import y.w.tdd.bean.Clock;
import y.w.tdd.bean.ObjectLoader;
import y.w.tdd.bean.ReloadPolicy;
import y.w.tdd.bean.TimedCache;

import java.sql.Timestamp;

import static org.junit.Assert.assertSame;

@RunWith(JMock.class)
public class JMockTimedCacheTest {
    private Mockery context = new JUnit4Mockery();
         
    //you have to define it as a final variable
    //since it is going to be used in a inner class
    // We need a Clock class to record time
    // We need a class to define load policy
    private final ObjectLoader loader       = context.mock(ObjectLoader.class);
    private final Clock        clock        = context.mock(Clock.class);
    private final ReloadPolicy reloadPolicy = context.mock(ReloadPolicy.class);

    private final Object     KEY = new Object();
    private final Object     VALUE = new Object();
    private final Object     NEW_VALUE = new Object();
    private       TimedCache cache;
    
    //Change 3:
    private static final Timestamp CURRENT_TIME = new Timestamp(System.currentTimeMillis());
    private static final Timestamp LOAD_TIME = CURRENT_TIME;

    @Before
    public void setUp() {
        /**
         * The cache component takes the ObjectLoader dependency in its constructor on instantiation. This
         * design is known Construction Dependency Injection (CDI). The Construction Dependency Injection
         * makes possible for the cache to use an ObjectLoader component (a real implementation or a Mock
         * object) without depending on its concrete implementation.
         */
        // Change 3: refactor TimedCache to take Clock and ReloadPolicy objects
        // These can be injected as constructor arguments.
        cache = new TimedCache(loader, clock, reloadPolicy);
    }
    
    @Test
    public void testLoadsObjectThatIsNotCached() {  // Interaction 1
        //set the expection for the mock object
        context.checking(new Expectations() {{
            //the mock object will call the load method once
            //and we assume that is returns VALUE
            oneOf (loader).load(with(equal(KEY)));
            will(returnValue(VALUE));
            oneOf(clock).getCurrentTime(); will(returnValue(CURRENT_TIME));
            never(reloadPolicy).shouldReload(LOAD_TIME, CURRENT_TIME);
        }});

        // Now call it and check the value
        // ObjectLoader.load() should be called one
        assertSame("Should load the object", VALUE, cache.lookUp(KEY));
    }

    @Test
    public void testDoesNotLoadObjectThatIsCached() { // Interaction 2
        //set the expection for the mock object
        context.checking(new Expectations() {{
            //the mock object will call the load method once
            //and we assume that is returns VALUE
            oneOf (loader).load(with(equal(KEY)));
            will(returnValue(VALUE));
            exactly(2).of(clock).getCurrentTime(); will(returnValue(CURRENT_TIME));
            atLeast(1).of(reloadPolicy).shouldReload(LOAD_TIME, CURRENT_TIME);
        }});

        // Now call it and check the value
        // ObjectLoader.load() should be called one
        assertSame("Should load the object", VALUE, cache.lookUp(KEY));
        // This time the ObjectLoader.load() should not be called
        assertSame("Should get the cached object", VALUE, cache.lookUp(KEY));
    }

    @Test
    public void testCachedObjectsAreNotReloadedBeforeTimeout() {  // Interaction 3
    	// Introduced new interfaces: Clock and ReloadPolicy
        //set the expection for the mock object
        context.checking(new Expectations() {{
            //the mock object will call the load method once
            //and we assume that is returns VALUE
            oneOf (loader).load(with(equal(KEY)));
            will(returnValue(VALUE));
        }});

        //set the expection for the mock object clock
        context.checking(new Expectations() {{
            //Clock.getCurrentTime() must be called at least once without argument
            // and return CURRENT_TIME
            atLeast(1).of(clock).getCurrentTime(); will(returnValue(CURRENT_TIME));
        }});

        //set the expection for the mock object reloadPolicy
        context.checking(new Expectations() {{
            // ReloadPolicy.shouldReload() must be called at least with arguments LOAD_TIME and CURRENT_TIME
            // and return false - pretends it is before timeout
            atLeast(1).of(reloadPolicy).shouldReload(LOAD_TIME, CURRENT_TIME);
            will(returnValue(false));
        }});
        
        assertSame("Loaded object", VALUE, cache.lookUp(KEY));
        assertSame("Cached object", VALUE, cache.lookUp(KEY));
    }
    
    @Test
    public void testLoadsCachedObjectAfterTimeout() {  // Interaction 4
    	// Introduced new interfaces: Clock and ReloadPolicy
        //set the expection for the mock object
        context.checking(new Expectations() {{
            //the mock object will call the load method once
            //and we assume that is returns VALUE
            exactly(2).of(loader).load(with(equal(KEY)));
            will( onConsecutiveCalls ( returnValue(VALUE), returnValue(NEW_VALUE)  ) );
        }});

        //set the expection for the mock object clock
        context.checking(new Expectations() {{
            //Clock.getCurrentTime() must be called at least once without argument
            // and return CURRENT_TIME
            atLeast(1).of(clock).getCurrentTime(); will(returnValue(CURRENT_TIME));
        }});

        //set the expection for the mock object reloadPolicy
        context.checking(new Expectations() {{
            // ReloadPolicy.shouldReload() must be called at least with arguments LOAD_TIME and CURRENT_TIME
            // and return false - pretends it is before timeout
            atLeast(1).of(reloadPolicy).shouldReload(LOAD_TIME, CURRENT_TIME);
            will(returnValue(true));
        }});
        
        assertSame("Should be loaded object", VALUE, cache.lookUp(KEY));
        assertSame("Should be reloaded object", NEW_VALUE, cache.lookUp(KEY));
    }
}