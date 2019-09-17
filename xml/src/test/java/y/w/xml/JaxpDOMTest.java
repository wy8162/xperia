/**
 * Yang Wang, Feb 4, 2012
 */
package y.w.xml;

import org.apache.log4j.Logger;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import y.w.xml.proxy.ProxySelectorImpl;

import java.io.IOException;
import java.net.ProxySelector;

/**
 * @author yangwang
 *
 */
public class JaxpDOMTest {
	static Logger log = Logger.getLogger(JaxpDOMTest.class);
    @Before public void setUpTestEnvironment() {
        ProxySelectorImpl ps = new ProxySelectorImpl(ProxySelector.getDefault());
        ProxySelector.setDefault(ps);
    }
    
    @After public void cleanUpTestEnvironment() {
    }

    @Test
    public void doParseDocument() {
    	log.debug("Creating JaxpDOM instance...");
    	JaxpDOM jaxp = new JaxpDOM();
    	
    	Document doc = jaxp.initXmlDocumentFile(ClassLoader.class.getResource("/cat.xml").getFile());
    	jaxp.echo(doc);
    }
    
	@Test
	public void doCreateADocument() {
		
		log.debug("Creating JaxpDOM object...");
		JaxpDOM jaxp = new JaxpDOM();
		
		log.debug("Creating Document ...");
		Document doc = jaxp.initXmlDocument();
		doc.setDocumentURI("com.wy8162/dom");
		Element catalog = doc.createElementNS("com.wy8162/dom", "catalog");
		Element book    = doc.createElement("book");
		book.setAttribute("id", "bk101");
		Element title   = doc.createElement("title");
		Text text = doc.createTextNode("XML Developer's Guide");
		title.appendChild(text);
		Element price   = doc.createElement("price");
		text = doc.createTextNode("44.95");
		price.appendChild(text);
		book.appendChild(title);
		book.appendChild(price);
		catalog.appendChild(book);
		
		book    = doc.createElement("book");
		book.setAttribute("id", "bk102");
		title   = doc.createElement("title");
		text = doc.createTextNode("Midnight Rain");
		title.appendChild(text);
		price   = doc.createElement("price");
		text = doc.createTextNode("5.95");
		price.appendChild(text);
		book.appendChild(title);
		book.appendChild(price);
		catalog.appendChild(book);
		
		doc.appendChild(catalog);
		
		log.debug("Serializing the XML document...");
		OutputFormat fmt = new OutputFormat(doc);
		fmt.setIndenting(true);
		XMLSerializer serializer = new XMLSerializer(System.out, fmt);
		try {
			serializer.serialize(doc);
		} catch (IOException e) {
			log.error("Failed to serialize the Document");
		}
		
		log.debug("Echo the nodes...");
		jaxp.echo(doc);
	}
}
