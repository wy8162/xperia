package y.w.xml;

import java.io.IOException;
import java.io.InputStream;
import org.apache.log4j.Logger;
import org.apache.xerces.dom.DOMImplementationImpl;
import org.apache.xerces.parsers.DOMParser;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class FirstDOM {
	static Logger log = Logger.getLogger(FirstDOM.class);
	public FirstDOM() {
	}
	
	void createDocument() {
		DOMImplementation impl = DOMImplementationImpl.getDOMImplementation();
		Document doc = impl.createDocument("com.wy8162/dom", "catalog", null);
		Element catalog = doc.getDocumentElement();
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
		
		OutputFormat fmt = new OutputFormat(doc);
		fmt.setIndenting(true);
		XMLSerializer serializer = new XMLSerializer(System.out, fmt);
		try {
			serializer.serialize(doc);
		} catch (IOException e) {
			log.error("Failed to serialize the Document");
		}
	}
	
	public void parse(InputStream in) {
		//Now let's analyze an XML file
		DOMParser parser = new DOMParser();
		InputSource source = new InputSource(in);
		
		try {
			parser.parse(source);
	
			Document doc = parser.getDocument();
			NodeList titles = doc.getElementsByTagName("title");
			for (int i=0; i < titles.getLength(); i++) {
				Node datum = titles.item(i);
				Text result = (Text) datum.getFirstChild();
				System.out.println(result.getNodeValue() + "\n");
			}
		} catch (IOException ie) {
			log.error("Failed to read XML Document");
		} catch (SAXException e) {
			log.error("Failed to parse the XML Document");
		}
	}
}