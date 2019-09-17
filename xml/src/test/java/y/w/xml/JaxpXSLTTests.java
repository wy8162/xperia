/**
 * Yang Wang, Feb 4, 2012
 */
package y.w.xml;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.XMLReader;
import y.w.xml.proxy.ProxySelectorImpl;

import java.net.ProxySelector;

/**
 * @author yangwang
 *
 */
public class JaxpXSLTTests {
	static Logger log = Logger.getLogger(JaxpXSLTTests.class);
    @Before public void setUpTestEnvironment() {
        ProxySelectorImpl ps = new ProxySelectorImpl(ProxySelector.getDefault());
        ProxySelector.setDefault(ps);
    }
    
    @After public void cleanUpTestEnvironment() {
    }

    @Test
    public void doConvertDOMToXML() {
    	log.debug("Creating JaxpDOM instance...");
    	JaxpDOM jaxp = new JaxpDOM();
    	JaxpXSLT xslt= new JaxpXSLT();
    	
    	Document doc = jaxp.initXmlDocumentFile(ClassLoader.class.getResource("/cat.xml").getFile());
    	xslt.transformDOMToXML(doc);
    }

    @Test
    public void doConvertDOMToXML2() {
    	log.debug("Creating JaxpDOM instance...");
    	JaxpDOM jaxp = new JaxpDOM();
    	JaxpXSLT xslt= new JaxpXSLT();
    	
    	Document doc = jaxp.initXmlDocumentFile(ClassLoader.class.getResource("/xslt/article1.xml").getFile());
    	xslt.transformDOMToXML(doc);
    }
    
    @Test
    public void doConvertDOMToXMLSubTree() {
    	log.debug("Creating JaxpDOM instance...");
    	JaxpDOM jaxp = new JaxpDOM();
    	JaxpXSLT xslt= new JaxpXSLT();
    	
    	Document doc = jaxp.initXmlDocumentFile(ClassLoader.class.getResource("/books.xml").getFile());
    	
    	NodeList list = doc.getElementsByTagName("title");
    	Node node = list.item(0); 
    	  
    	xslt.transformSubTreeToXML(node);
    }
    
    @Test
    public void doConvertCustomSAXParser() {
    	log.debug("Creating JaxpDOM instance...");
    	JaxpDOM jaxp = new JaxpDOM();
    	JaxpXSLT xslt= new JaxpXSLT();
    	AddressBookParser parser = new AddressBookParser();
    	  
    	xslt.transformSAXParser(parser, ClassLoader.class.getResource("/xslt/PersonalAddressBook.ldif").getFile());
    }
    
    @Test
    public void doConvertCustomSAXParser2() {
    	log.debug("Creating JaxpDOM instance...");
    	JaxpDOM jaxp = new JaxpDOM();
    	JaxpXSLT xslt= new JaxpXSLT();
    	JaxpSax sax  = new JaxpSax();
    	sax.initXmlParser();
    	XMLReader parser = sax.getSAXParser();
    	  
    	xslt.transformSAXParser(parser, ClassLoader.class.getResource("/books.xml").getFile());
    }
}
