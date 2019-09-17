/**
 * http://www.theserverside.com/news/1365050/Using-JMock-in-Test-Driven-Development
 * TDD - Step 2: Write the codes
 */
package y.w.tdd.bean;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class TimedCache {
	// Added this private class to hold object value and it's creation timestamp.
	private class TimestampedValue {
		public Object value;
		public Timestamp loadTime;
	}

	// Change 2: added Map to make sure ObjectLoader.load() will not be called if the
	// object is cached
	// Change 3:
	// The cache component must load an object when lookup() is invoked for the first time.
	// The cache component must not reload objects that have been previously loaded and have been on cache for too long.
	// The cache component must reload objects that have been previously loaded and have not been on cache for too long.
	//
	// Dependencies Clock and ReloadPolicy are injected by constructor

    private ObjectLoader loader;
    private Clock clock;
    private ReloadPolicy policy;
    private Map<Object, TimestampedValue> cachedValues = new HashMap<Object, TimestampedValue>();
    
    public TimedCache(ObjectLoader loader, Clock clock, ReloadPolicy reloadPolicy) {
        this.loader = loader;
        this.clock  = clock;
        this.policy = reloadPolicy;
    }
    
    public Object lookUp(Object key) {
        TimestampedValue timestampedValue = (TimestampedValue) cachedValues.get(key);
        Timestamp currentTime =clock.getCurrentTime();
        if((timestampedValue == null)||((policy.shouldReload(timestampedValue.loadTime,currentTime)))){
            Object value = loader.load(key);
            timestampedValue = new TimestampedValue();
            timestampedValue.loadTime = currentTime;
            timestampedValue.value = value;
            cachedValues.put(key, timestampedValue);
        }
        return timestampedValue.value;
    }
}