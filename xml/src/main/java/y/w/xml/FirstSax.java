package y.w.xml;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class FirstSax {
	static Logger log = Logger.getLogger(FirstSax.class);
	XMLReader parser;
	ContentHandler handler;
	SaxErrorHandler errorHandler;
	
	public FirstSax() {
			try {
			// Two ways to select parser:
				// Method 1:
				parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
				log.info("Parser class name=" + parser.getClass().getName());
			} catch (SAXException e1) {
				// Methond 2:
				// System.getProperties().setProperty("org.xml.sax.driver", "org.apache.xerces.parsers.SAXParser");
				// XMLReaderFactory.createXMLReader() below will use the one defined by
				// system property "org.xml.sax.driver".
				try {
					parser = XMLReaderFactory.createXMLReader();
					log.info("Parser class name=" + parser.getClass().getName());
				} catch (SAXException e2) {
					System.out.print("Failed to create XML Parser\n");
					return;
				}
			}
			handler = new SaxHandler();
			errorHandler = new SaxErrorHandler();
			parser.setContentHandler(handler);
			parser.setErrorHandler(errorHandler);
			parser.setEntityResolver(new SaxEntResolver());
	}
	
	void parse( InputStream in ) {
		try {
			InputSource source = new InputSource(in);
			parser.parse(source);
		} catch (IOException ie) {
			System.out.print("Failed to read file");
		} catch (SAXException se) {
			System.out.print("Failed to parse XML\n");
		}
	}
	
	void validXmlAgainstDTD( InputSource is ) {
		try {
			parser.setFeature("http://xml.org/sax/features/validation", true);
			parser.setFeature("http://apache.org/xml/features/xinclude", true);
		} catch (SAXNotRecognizedException e) {
			log.error("Feature http://xml.org/sax/features/validation not recognized");
			e.printStackTrace();
		} catch (SAXNotSupportedException e) {
			log.error("Feature http://xml.org/sax/features/validation not supported");
			e.printStackTrace();
		}
		try {
			parser.parse(is);
		} catch (IOException ie) {
			System.out.print("Failed to read file\n" + ie);
		} catch (SAXException se) {
			System.out.print("Failed to parse XML\n" + se);
		}
		if (errorHandler.isValid()) {
				log.info(">>>>>>>>The document is valid");
		} else  log.info(">>>>>>>>The document is INVALID");
	}
}