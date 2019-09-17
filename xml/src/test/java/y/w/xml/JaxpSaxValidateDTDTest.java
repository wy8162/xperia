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
public class JaxpSaxValidateDTDTest {
	static Logger log = Logger.getLogger(JaxpSaxValidateDTDTest.class);
    @Before public void setUpTestEnvironment() {
        ProxySelectorImpl ps = new ProxySelectorImpl(ProxySelector.getDefault());
        ProxySelector.setDefault(ps);
    }
    
    @After public void cleanUpTestEnvironment() {
    }

	@Test
	public void doParseDocument() {
		JaxpSax jaxp = new JaxpSax();
		jaxp.initXmlParserDTD(); // Initialize an XML parser, validating DTD
		
        log.debug(">>>>>>Parsing /data/personal.xml against /data/personal.dtd...");
		jaxp.parseXmlFile(ClassLoader.class.getResource("/data/personal.xml").getFile());
        if (jaxp.isValid()) log.debug("/data/personal.xml is VALID.");
        else log.debug("/data/personal.xml is INVALID.");

        log.debug(">>>>>>Parsing /data/rich_iii.xml against /data/play.dtd...");
		jaxp.parseXmlFile(ClassLoader.class.getResource("/data/rich_iii.xml").getFile());
        if (jaxp.isValid()) log.debug("/data/rich_iii.xml is VALID.");
        else log.debug("/data/rich_iii.xml is INVALID.");
	}
}
