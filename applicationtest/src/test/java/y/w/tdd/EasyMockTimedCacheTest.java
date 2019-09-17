/**
 * Using EasyMock to test
 *
 * TDD - Step 1: Write the test case
 *      - TimeCache depends on ObjectLoader
 *      - The test should not depend on a concrete class of interface ObjectLoader
 *      - Uses a stub to mock the functionalitis of ObjectLoader
 */
package y.w.tdd;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;
import y.w.tdd.bean.Clock;
import y.w.tdd.bean.ObjectLoader;
import y.w.tdd.bean.ReloadPolicy;
import y.w.tdd.bean.TimedCache;

import java.sql.Timestamp;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertSame;

public class EasyMockTimedCacheTest extends EasyMockSupport {    
    private ObjectLoader loader;
    private Clock        clock;
    private ReloadPolicy policy;

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
		loader = createMock(ObjectLoader.class);
		clock = createMock(Clock.class);
		policy = createMock(ReloadPolicy.class);
        cache = new TimedCache(loader, clock, policy);
    }
    
    @Test
    public void testLoadsObjectThatIsNotCached() {  // Interaction 1
    	expect(clock.getCurrentTime()).andReturn(CURRENT_TIME).times(1);
    	expect(loader.load(eq(KEY))).andReturn(VALUE).times(1);
    	replayAll();
        assertSame("Should load the object", VALUE, cache.lookUp(KEY));
        verifyAll();
    }

    @Test
    public void testDoesNotLoadObjectThatIsCachedAndBeforeTimeout() { // Interaction 2
    	expect(clock.getCurrentTime()).andReturn(CURRENT_TIME).times(2);
    	expect(loader.load(eq(KEY))).andReturn(VALUE).times(1);
    	expect(policy.shouldReload(eq(LOAD_TIME), eq(CURRENT_TIME))).andReturn(false).times(1);

    	replayAll();
        assertSame("Should load the object", VALUE, cache.lookUp(KEY));
        assertSame("Should get the cached object", VALUE, cache.lookUp(KEY));
        verifyAll();
    }

    @Test
    public void testLoadsCachedObjectAfterTimeout() {  // Interaction 3 & 4
    	expect(loader.load(eq(KEY)))
    		.andReturn(VALUE).times(1)		// Return VALUE the first time
    		.andReturn(NEW_VALUE).times(1);	// Return NEW_VALUE the second time
    	expect(clock.getCurrentTime()).andReturn(CURRENT_TIME).atLeastOnce();
    	expect(policy.shouldReload(eq(LOAD_TIME), eq(CURRENT_TIME))).andReturn(true).atLeastOnce();

    	replayAll();
        assertSame("Should be loaded object", VALUE, cache.lookUp(KEY));
        assertSame("Should be reloaded object", NEW_VALUE, cache.lookUp(KEY));
        verifyAll();
    }
}