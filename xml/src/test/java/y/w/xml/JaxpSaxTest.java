/**
 * Yang Wang, Feb 4, 2012
 */
package y.w.xml;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import y.w.xml.proxy.ProxySelectorImpl;

import java.net.ProxySelector;

/**
 * @author yangwang
 *
 */
public class JaxpSaxTest {
	static Logger log = Logger.getLogger(JaxpSaxTest.class);
    @Before public void setUpTestEnvironment() {
        ProxySelectorImpl ps = new ProxySelectorImpl(ProxySelector.getDefault());
        ProxySelector.setDefault(ps);
    }
    
    @After public void cleanUpTestEnvironment() {
    }

	@Test
	public void doParseDocument() {
		JaxpSax jaxp = new JaxpSax();
		jaxp.initXmlParser(); // Initialize an XML parser - no validation.
		
        log.debug(">>>>>>Parsing /cat.xml...");
		jaxp.parseXmlFile(ClassLoader.class.getResource("/cat.xml").getFile());
        
        log.debug(">>>>>>Parsing /books.xml...");
		jaxp.parseXmlFile(ClassLoader.class.getResource("/books.xml").getFile());

        log.debug(">>>>>>Parsing /fib.xml...");
		jaxp.parseXmlFile(ClassLoader.class.getResource("/fib.xml").getFile());
        
        log.debug(">>>>>>Parsing /sp_searchcoreorg.xml...");
		jaxp.parseXmlFile(ClassLoader.class.getResource("/data/sp_searchcoreorg.xml").getFile());
	}
}
