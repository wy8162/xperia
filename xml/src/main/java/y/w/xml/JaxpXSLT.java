/**
 * XSLT With JAXP
 * Yang Wang, Feb 25, 2012
 * 
 * http://java.sun.com/webservices/reference/tutorials/jaxp/html/xslt.html
 */
package y.w.xml;

import java.io.*;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.dom.DOMSource; 
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/**
 * @author yangwang
 *
 */
public class JaxpXSLT {
	static Logger log = Logger.getLogger(JaxpXSLT.class);

	TransformerFactory transformerFactory = null;
	Transformer transformer = null;

	public JaxpXSLT() {
		transformerFactory = TransformerFactory.newInstance();
		try {
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			log.fatal("Failed to create Transformer." + e);
		}
        if (transformerFactory != null) log.debug("TransformerFactory=" + transformerFactory.getClass().getName());
        if (transformer != null) log.debug("Transformer=" + transformer.getClass().getName());
	}
	
	public void transformDOMToXML(Document doc) {
		if (transformer == null) {
			log.error("JaxpXSLT was not correctly initialized. Transformer is null");
			return;
		}
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(System.out);
		try {
			// Set transform output properties
			if (doc.getDoctype() != null) {
				String systemValue = (new File(doc.getDoctype().getSystemId())).getName();
				transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, systemValue);
			}
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);
		} catch (TransformerException e) {
			log.error("Failed to transform document " + e);
		}
	}
	
	public void transformSubTreeToXML(Node node) {
		if (transformer == null) {
			log.error("JaxpXSLT was not correctly initialized. Transformer is null");
			return;
		}
		DOMSource source = new DOMSource(node);
		StreamResult result = new StreamResult(System.out);
		try {
			// Set transform output properties
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);
		} catch (TransformerException e) {
			log.error("Failed to transform document " + e);
		}
	}
	
	public void transformSAXParser(XMLReader parser, String xmlFile) {
		if (transformer == null) {
			log.error("JaxpXSLT was not correctly initialized. Transformer is null");
			return;
		}
		FileReader fr;
		try {
			fr = new FileReader(xmlFile);
		} catch (FileNotFoundException e1) {
			log.error("Failed to find file " + xmlFile);
			return;
		}
		
		BufferedReader br = new BufferedReader(fr);
		InputSource inputSource = new InputSource(br);
		SAXSource source = new SAXSource(parser, inputSource);
	
		StreamResult result = new StreamResult(System.out);
		try {
			// Set transform output properties
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);
		} catch (TransformerException e) {
			log.error("Failed to transform document " + e);
		}
	}
}
