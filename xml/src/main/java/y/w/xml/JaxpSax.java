/**
 * SAX with JAXP
 * This is to use SAX interface provided by JAXP.
 * http://java.sun.com/webservices/reference/tutorials/jaxp/html/sax.html
 */
package y.w.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.lang.System;

// JAXP SAX API
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;

// SAX API by org.xml
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.SAXException;
import org.xml.sax.InputSource;

// Handlers
import org.apache.log4j.Logger;
 
public class JaxpSax {
    static Logger log = Logger.getLogger(JaxpSax.class);
    
    // Property constants for validation
    static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    static final String W3C_XML_SCHEMA		 = "http://www.w3.org/2001/XMLSchema";
    static final String JAXP_SCHEMA_SOURCE   = "http://java.sun.com/xml/jaxp/properties/schemaSource";
    
    boolean xincludeAware = false;
    SAXParserFactory saxParserFactory = null;
    SAXParser saxParser 			  = null;
    SaxErrorHandler errorHandler	  = null;
    SaxHandler saxHandler			  = null;
    SaxEntResolver entResolver		  = null;
    
    public JaxpSax() {
        // The actual SAXParserFactory created is determined by system property:
        //      javax.xml.parsers.SAXParserFactory
        // The following sets the System property to use Xerces-J implementation
        System.getProperties().setProperty("javax.xml.parsers.SAXParserFactory", "org.apache.xerces.jaxp.SAXParserFactoryImpl");  
    }
    
    public JaxpSax(boolean xincludeAware) {
        // The actual SAXParserFactory created is determined by system property:
        //      javax.xml.parsers.SAXParserFactory
        // The following sets the System property to use Xerces-J implementation
        System.getProperties().setProperty("javax.xml.parsers.SAXParserFactory", "org.apache.xerces.jaxp.SAXParserFactoryImpl");
        this.xincludeAware = xincludeAware;
    }
    
    /**
     * Create a parser for XML parser without validation.
     * @return: true = successful, false = failed
     */
    public boolean initXmlParser() {
    	if (saxParser != null) return false; // This JaxpSax has already been initialized.
  
        boolean result = initParser(false);
        if (result) {
            log.debug("SAXParserFactory=" + saxParserFactory.getClass().getName());
            log.debug("SAXParser=" + saxParser.getClass().getName());
        }
        return result;
    }

    /**
     * Create a parser for XML parser with DTD validation.
     * @return: true = successful, false = failed
     */
    public boolean initXmlParserDTD() {
    	if (saxParser != null) return false; // This JaxpSax has already been initialized.
    	boolean result = initParser(true);
    	
        if (result) {
            log.debug("SAXParserFactory=" + saxParserFactory.getClass().getName());
            log.debug("SAXParser=" + saxParser.getClass().getName());
        }
        return result;
    }
    
    
    /**
     * Create a parser for XML parser schema validation.
     * @return: true = successful, false = failed
     */
    public boolean initXmlParserXSD(String schemaSource) {
    	if (saxParser != null) return false; // This JaxpSax has already been initialized.
    	if ( initParser(true) ) {
            log.debug("SAXParserFactory=" + saxParserFactory.getClass().getName());
            log.debug("SAXParser=" + saxParser.getClass().getName());  
            
            try {
                saxParser.setProperty(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
                if (schemaSource != null) saxParser.setProperty(JAXP_SCHEMA_SOURCE, new File(schemaSource));
                return true;
            } catch (SAXNotRecognizedException x) {
                log.fatal("Error: JAXP SAXParser property not recognized: " + JAXP_SCHEMA_LANGUAGE + ". Check to see if parser conforms to JAXP 1.2 spec.");
                System.exit(1);
            } catch (SAXNotSupportedException e) {
				log.fatal("Error: JAXP SAXParser does not support:" + JAXP_SCHEMA_LANGUAGE );
			}
    	}
    	return false;
    }
    
    public void setValidating() {
        if (saxParserFactory != null) {
        	saxParserFactory.setValidating(true);
        	saxParserFactory.setNamespaceAware(true);
        }
    }
    
    private void setXInclude() {
        if (xincludeAware) {
            log.info("Configuring XInclude feature");
            saxParserFactory.setXIncludeAware(true);
            
            try {
            	// Enable this feature so that the validation will be applied to the infoset after XInclude applied
            	// http://xerces.apache.org/xerces2-j/faq-xinclude.html
                saxParserFactory.setFeature("http://apache.org/xml/features/validation/schema", true);
            } catch (SAXNotRecognizedException ne) {
                log.warn("XInclude feater is not recognized." + ne);
            } catch (SAXNotSupportedException sne) {
                log.warn("XInclude feater is not supported." + sne);
            } catch (ParserConfigurationException pe) {
                log.warn("XInclude feater is not supported." + pe);
            }
        }
    }
    
    private boolean initParser(boolean validating) {
        try {
            saxParserFactory = SAXParserFactory.newInstance();
            if (validating) setValidating();
            setXInclude();
            saxParser = saxParserFactory.newSAXParser();
            if (saxParserFactory.isXIncludeAware()) log.debug("XML SAX PARSER IS XINCLUDE Aware");
        } catch (ParserConfigurationException e) {
            log.fatal("Failed to create SAXParserFactory." + e);
            e.printStackTrace();
            return false;
        } catch (SAXException se) {
            log.fatal("Failed to create SAXParser." + se);
            se.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean isValid() {
		return errorHandler.isValid();
    }
    
    public XMLReader parseXmlFile(String filename) {
        XMLReader xmlReader;
        try {
            xmlReader = saxParser.getXMLReader();
        } catch (SAXException e) {
            log.fatal("Failed to create XMLReader." + e);
            e.printStackTrace();
            return null;
        }
        
        log.debug("XMLReader=" + xmlReader.getClass().getName());
        errorHandler = new SaxErrorHandler();
        saxHandler	 = new SaxHandler();
        entResolver	 = new SaxEntResolver();
        xmlReader.setContentHandler(saxHandler);
        xmlReader.setErrorHandler(errorHandler);
        xmlReader.setEntityResolver(entResolver);

        try {
            InputStream ins = new FileInputStream(new File(filename));
            InputSource is = new InputSource(ins);
            is.setSystemId(filename);
            //xmlReader.parse(convertToFileURL(filename));
            xmlReader.parse(is);
        } catch (IOException ie) {
            log.fatal("Failed to read file '" + filename + "'");
        } catch (SAXException se) {
            log.fatal("Failed to parse file '" + filename + "'" + se);
            se.printStackTrace();
            return null;
        }

        return xmlReader;
    }

    public XMLReader getSAXParser() {
        XMLReader xmlReader;
        try {
            xmlReader = saxParser.getXMLReader();
        } catch (SAXException e) {
            log.fatal("Failed to create XMLReader." + e);
            e.printStackTrace();
            return null;
        }
        return xmlReader;
    }
    
    private static String convertToFileURL(String filename) {
        String path = new File(filename).getAbsolutePath();
        if (File.separatorChar != '/') {
            path = path.replace(File.separatorChar, '/');
        }
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return "file:" + path;
    }
}