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
public class JaxpSaxValidateXSDSchemaTest {
	static Logger log = Logger.getLogger(JaxpSaxValidateXSDSchemaTest.class);
    @Before public void setUpTestEnvironment() {
        ProxySelectorImpl ps = new ProxySelectorImpl(ProxySelector.getDefault());
        ProxySelector.setDefault(ps);
    }
    
    @After public void cleanUpTestEnvironment() {
    }

	@Test
	public void doValidationAgainstSchemaTest1() {
		String schemaSource = ClassLoader.class.getResource("/data/personal.xsd").getFile();

		JaxpSax jaxp = new JaxpSax();
		jaxp.initXmlParserXSD(schemaSource); // Initialize an XML parser - validating XSD
		
        log.debug(">>>>>>Parsing /data/personal-schema.xml against /data/personal.xsd...");
		jaxp.parseXmlFile(ClassLoader.class.getResource("/data/personal-schema.xml").getFile());
		if (jaxp.isValid()) log.debug("/data/personal-schema.xml is VALID.");
        else log.debug("/data/personal-schema.xml is INVALID.");
	}

	@Test
	public void doValidationAgainstSchemaTest2() {
		if (ClassLoader.class.getResource("/data/ServiceSchema/v1/Common/BODs/ProcessOrganizationCoreSearch.xsd") == null) {
			log.error("File doesn't exist: /data/ServiceSchema/v1/Common/BODs/ProcessOrganizationCoreSearch.xsd");
			return;
		}
		
		String schemaSource = ClassLoader.class.getResource("/data/ServiceSchema/v1/Common/BODs/ProcessOrganizationCoreSearch.xsd").getFile();

		JaxpSax jaxp = new JaxpSax();
		jaxp.initXmlParserXSD(schemaSource); // Initialize an XML parser - validating XSD

        log.debug(">>>>>>Parsing /data/sp_searchcoreorg.xml against XSD defined in the XML file...");
		jaxp.parseXmlFile(ClassLoader.class.getResource("/data/sp_searchcoreorg.xml").getFile());
		if (jaxp.isValid()) log.debug("/data/sp_searchcoreorg.xml is VALID.");
        else log.debug("/data/sp_searchcoreorg.xml is INVALID.");
	}
}
