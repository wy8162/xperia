/**
 * Using Mockito to test
 *
 * TDD - Step 1: Write the test case
 *      - TimeCache depends on ObjectLoader
 *      - The test should not depend on a concrete class of interface ObjectLoader
 *      - Uses a stub to mock the functionalitis of ObjectLoader
 */
package y.w.tdd;

import org.junit.Before;
import org.junit.Test;
import y.w.tdd.bean.Clock;
import y.w.tdd.bean.ObjectLoader;
import y.w.tdd.bean.ReloadPolicy;
import y.w.tdd.bean.TimedCache;

import java.sql.Timestamp;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MockitoTimedCacheTest {    
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
		loader = mock(ObjectLoader.class);
		clock = mock(Clock.class);
		policy = mock(ReloadPolicy.class);
        cache = new TimedCache(loader, clock, policy);
    }
    
    @Test
    public void testLoadsObjectThatIsNotCached() {  // Interaction 1
    	// Stubbing the methods
    	when(clock.getCurrentTime()).thenReturn(CURRENT_TIME);
    	when(loader.load(KEY)).thenReturn(VALUE);
    	
    	assertSame("Should load the object", VALUE, cache.lookUp(KEY));
    	
    	// By default, verify the methods were called exactly once
    	verify(clock).getCurrentTime();
    	verify(loader).load(KEY);
    }

    @Test
    public void testDoesNotLoadObjectThatIsCachedAndBeforeTimeout() { // Interaction 2
    	// Stubbing the methods
    	when(clock.getCurrentTime()).thenReturn(CURRENT_TIME);
    	when(loader.load(KEY)).thenReturn(VALUE);
    	when(policy.shouldReload(LOAD_TIME, CURRENT_TIME)).thenReturn(false);
    	
        assertSame("Should load the object", VALUE, cache.lookUp(KEY));
        assertSame("Should get the cached object", VALUE, cache.lookUp(KEY));
        
        verify(clock, times(2)).getCurrentTime();
        verify(loader, times(1)).load(KEY);
        verify(policy, times(1)).shouldReload(LOAD_TIME, CURRENT_TIME);
    }

    @Test
    public void testLoadsCachedObjectAfterTimeout() {  // Interaction 3 & 4
    	// Consecutive method calls with same method call and same argument
    	when(loader.load(KEY)).thenReturn(VALUE, NEW_VALUE);
    	
    	when(clock.getCurrentTime()).thenReturn(CURRENT_TIME);
    	when(policy.shouldReload(LOAD_TIME, CURRENT_TIME)).thenReturn(true);
    	
        assertSame("Should be loaded object", VALUE, cache.lookUp(KEY));
        assertSame("Should be reloaded object", NEW_VALUE, cache.lookUp(KEY));
        
        verify(loader, times(2)).load(KEY);
        verify(clock, atLeast(1)).getCurrentTime();
        verify(policy, atLeast(1)).shouldReload(LOAD_TIME, CURRENT_TIME);
    }
}