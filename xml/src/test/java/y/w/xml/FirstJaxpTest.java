/**
 * Yang Wang, Feb 4, 2012
 */
package y.w.xml;

import static org.junit.Assert.assertNotNull;
import java.net.ProxySelector;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.InputSource;
import y.w.xml.proxy.ProxySelectorImpl;

/**
 * @author yangwang
 *
 */
public class FirstJaxpTest {
	static Logger log = Logger.getLogger(FirstJaxpTest.class);
    @Before public void setUpTestEnvironment() {
        ProxySelectorImpl ps = new ProxySelectorImpl(ProxySelector.getDefault());
        ProxySelector.setDefault(ps);
    }
    
    @After public void cleanUpTestEnvironment() {
    }

	/**
	 * Test method for {@link y.w.xml.FirstJaxp#buildXmlDocument()}.
	 */
	@Test
	public void testBuildXmlDocument() {
		log.info("Create FirstJaxp...");
		FirstJaxp jaxp = new FirstJaxp();
		jaxp.buildXmlDocument();
	}
	
	/**
	 * Test method for {@link y.w.xml.FirstJaxp#parseXmlDocument()}.
	 */
	@Test
	public void testParseXmlDocument() {
		InputStream in = ClassLoader.class.getResourceAsStream("/cat.xml");
		assertNotNull(in);
		FirstJaxp jaxp = new FirstJaxp();
		jaxp.parseXmlDocumentFile(in);
	}

	/**
	 * Test method for {@link y.w.xml.FirstJaxp#parseXmlDocument()}.
	 */
	@Test
	public void testParseXmlDocumentXincludeParser() {
		String systemId = ClassLoader.class.getResource("/xincludetest.xml").getFile();
		InputStream in;
		try {
			in = new FileInputStream(new File(systemId));
		} catch (FileNotFoundException e) {
			log.error("Failed to open file /xincludetest.xml");
			e.printStackTrace();
			return;
		}
		assertNotNull(in);
		InputSource is = new InputSource(in);
		
		// This is very important for XInclude to find the resources relative to this system ID.
		is.setSystemId(systemId);
		assertNotNull(is);
		FirstJaxp jaxp = new FirstJaxp();
		jaxp.parseXmlDocumentXInclude(is);
	}
	
	/**
	 * Test method for {@link y.w.xml.FirstJaxp#parseXmlDocument()}.
	 */
	@Test
	public void testParseXmlDocumentURLParser() {
        //if (ProxyAuthenticator.isProxyValid()) {
            FirstJaxp jaxp = new FirstJaxp();
            jaxp.parseXmlDocumentURL("http://www.w3schools.com/xml/simple.xml");
        //}
	}
}
