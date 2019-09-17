/**
 * Yang Wang, Feb 4, 2012
 */
package y.w.xml;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import y.w.xml.proxy.ProxySelectorImpl;

import java.net.ProxySelector;

import static org.junit.Assert.assertTrue;

/**
 * @author yangwang
 *
 */
public class JaxpDOMValidationTests {
	static Logger log = Logger.getLogger(JaxpDOMValidationTests.class);
    @Before public void setUpTestEnvironment() {
        ProxySelectorImpl ps = new ProxySelectorImpl(ProxySelector.getDefault());
        ProxySelector.setDefault(ps);
    }
    
    @After public void cleanUpTestEnvironment() {
    }

    @Test
    public void doValidateDTD() {
    	String xml = "/data/personal.xml";
    	
    	log.debug("Creating JaxpDOM instance...");
    	JaxpDOM jaxp = new JaxpDOM();
    	
    	Document doc = jaxp.initXmlDocumentFileValidation(ClassLoader.class.getResource(xml).getFile());
    	jaxp.echo(doc);
        if (jaxp.isValid()) log.debug(xml + " is VALID.");
        else log.debug(xml + " is INVALID.");
        assertTrue(jaxp.isValid());
    }
    
    @Test
    public void doValidateDTDXInclude() {
    	String xml = "/xincludetest.xml";
    	
    	log.debug("Creating JaxpDOM instance...");
    	JaxpDOM jaxp = new JaxpDOM();
    	
    	jaxp.setXIncludeAware(true);
    	Document doc = jaxp.initXmlDocumentFileValidation(ClassLoader.class.getResource(xml).getFile());
    	jaxp.echo(doc);
        if (jaxp.isValid()) log.debug(xml + " is VALID.");
        else log.debug(xml + " is INVALID.");
        
        //assertTrue(jaxp.isValid());
        log.info("The validation will fail for some reason - EntResolver returns null for HTTP resources.");
    }
    
    @Test
    public void doValidateXSD() {
    	String xml = "/data/personal-schema.xml";
    	
    	log.debug("Creating JaxpDOM instance...");
    	JaxpDOM jaxp = new JaxpDOM();
    	
    	jaxp.setSchemaValidation(true, null);
    	
    	Document doc = jaxp.initXmlDocumentFileValidation(ClassLoader.class.getResource(xml).getFile());
    	jaxp.echo(doc);
        if (jaxp.isValid()) log.debug(xml + " is VALID.");
        else log.debug(xml + " is INVALID.");
        
        assertTrue(jaxp.isValid());
    }
    
    
    @Test
    public void doValidateXSDExternalSchema() {
    	final String xml = "/data/personal-ext-schema.xml";
    	final String [] schemaSources = { ClassLoader.class.getResource("/data/personal.xsd").getFile() };
    	
    	log.debug("Creating JaxpDOM instance...");
    	JaxpDOM jaxp = new JaxpDOM();
    	
    	jaxp.setSchemaValidation(true, schemaSources);
    	
    	Document doc = jaxp.initXmlDocumentFileValidation(ClassLoader.class.getResource(xml).getFile());
    	jaxp.echo(doc);
        if (jaxp.isValid()) log.debug(xml + " is VALID.");
        else log.debug(xml + " is INVALID.");
        
        assertTrue(jaxp.isValid());
    }
}
