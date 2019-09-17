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
public class JaxpSaxXIncludeValidateDTDTest {
	static Logger log = Logger.getLogger(JaxpSaxValidateDTDTest.class);
    @Before public void setUpTestEnvironment() {
        ProxySelectorImpl ps = new ProxySelectorImpl(ProxySelector.getDefault());
        ProxySelector.setDefault(ps);
    }
    
    @After public void cleanUpTestEnvironment() {
    }

	@Test
	public void doParseDocument() {
		JaxpSax jaxp = new JaxpSax(true); // XInclude aware indicator
		jaxp.initXmlParserDTD(); // Initialize an XML parser, validating DTD
		
        log.debug(">>>>>>Parsing /xincludetest.xml...");
		jaxp.parseXmlFile(ClassLoader.class.getResource("/xincludetest.xml").getFile());
        if (jaxp.isValid()) log.debug("/xincludetest.xml is VALID.");
        else log.debug("/xincludetest.xml is INVALID.");
	}
}
