/**
 * Yang Wang, Feb 4, 2012
 */
package y.w.xml;

import java.io.IOException;
import java.io.InputStream;
import java.lang.System;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * @author yangwang
 *
 */
public class FirstJaxp {
	static Logger log = Logger.getLogger(FirstJaxp.class);

	/**
	 * Constructor - do nothing at the moment.
	 */
	public FirstJaxp() {}

	public void buildXmlDocument() {
		DocumentBuilder parser = newParser();
		if (parser == null) return;

		Document doc = parser.newDocument();
		Element catalog = doc.createElementNS("com.wy8162/jaxptest", "book");
		doc.appendChild(catalog);
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
		
		transformDocument(doc);
	}

	public void parseXmlDocumentFile(InputStream xmlFile) {
		DocumentBuilder parser = newParser();
		if (parser == null) return;

		Document doc = null;

		try {
			doc = parser.parse(xmlFile);
		} catch (SAXException e) {
			log.error("Failed parse XML document");
			e.printStackTrace();
			return;
		} catch (IOException e) {
			log.error("Failed to read XML document");
			e.printStackTrace();
			return;
		}

		transformDocument(doc);
	}

	/*
	 * Parse XML document located by a URL
	 */
	public void parseXmlDocumentURL(String xmlURL) {
		DocumentBuilder parser = newParser();
		if (parser == null) return;

		Document doc = null;

		try {
			doc = parser.parse( xmlURL );
		} catch (SAXException e) {
			log.error("Failed parse XML document");
			e.printStackTrace();
			return;
		} catch (IOException e) {
			log.error("Failed to read XML document");
			e.printStackTrace();
			return;
		}

		transformDocument(doc);
	}
	
	public void parseXmlDocumentXInclude(InputSource inputSource ) {
		DocumentBuilder parser = newParser();
		if (parser == null) return;

		Document doc = null;

		try {
			doc = parser.parse(inputSource);
		} catch (SAXParseException e) {
		      log.error("XML document is not well-formed at Line " + e.getLineNumber() 
		       + ", column " +  e.getColumnNumber() + " in the entity " + e.getSystemId());
		} catch (SAXException e) {
		      log.error("Could not check document because " + e.getMessage());
		} catch (IOException e) { 
		      log.error("Due to an IOException, the parser could not check the XML document"); 
		}

		transformDocument(doc);
	}
	
	private void transformDocument(Document doc) {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		try {
			Transformer transformer = transformerFactory.newTransformer();
			Source source = new DOMSource(doc);
			Result result = new StreamResult(System.out);
			transformer.transform(source, result);
			log.info("\nSystem ID=" + result.getSystemId());
		} catch (TransformerConfigurationException e) {
			log.error("Failed to create a transformer");
			e.printStackTrace();
		} catch (TransformerException e) {
			log.error("Failed to transform the XML document");
			e.printStackTrace();
		}
	}
	
	private DocumentBuilder newParser() {
		// User Xerces DocumentBuilderFactoryImpl
		System.getProperties().setProperty("javax.xml.parsers.DocumentBuilderFactory", "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setNamespaceAware(true);
		
		// Make the parser XInclude aware
		builderFactory.setXIncludeAware( true );

		try {
			DocumentBuilder parser = builderFactory.newDocumentBuilder();
			if (parser.isXIncludeAware()) log.info("It's XInclude aware");
			else log.info("It's not XInclude aware");
			parser.setEntityResolver(new SaxEntResolver());
			parser.setErrorHandler(new SaxErrorHandler());
			return parser;
		} catch (ParserConfigurationException e) {
			log.error("Failed to create document parser / document builder.");
			e.printStackTrace();
		}
		return null;
	}
}
