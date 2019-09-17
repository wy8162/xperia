/**
 * Load property proxy.properties in class path
 * Yang Wang, Feb 15, 2012
 */
package y.w.xml.proxy;

import java.io.IOException;
import java.util.Properties;


/**
 * @author yangwang
 *
 */
public class ProxyConfiguration {
	public static Properties getProperties(String propFileName) throws IOException {
        Properties props = new Properties();
        props.load( ClassLoader.class.getResourceAsStream(propFileName) );
        return props;
    }
}
