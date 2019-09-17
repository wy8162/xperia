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
public class JaxpSaxValidateXSDTest {
	static Logger log = Logger.getLogger(JaxpSaxValidateXSDTest.class);
    @Before public void setUpTestEnvironment() {
        ProxySelectorImpl ps = new ProxySelectorImpl(ProxySelector.getDefault());
        ProxySelector.setDefault(ps);
    }
    
    @After public void cleanUpTestEnvironment() {
    }

	@Test
	public void doValidationXSFTest() {
		JaxpSax jaxp = new JaxpSax();
		jaxp.initXmlParserXSD(null); // Initialize an XML parser - validating XSD
		
        log.debug(">>>>>>Parsing /data/personal-schema.xml against XSD defined in the XML file...");
		jaxp.parseXmlFile(ClassLoader.class.getResource("/data/personal-schema.xml").getFile());
		if (jaxp.isValid()) log.debug("/data/personal-schema.xml is VALID.");
        else log.debug("/data/personal-schema.xml is INVALID.");
	}
}
