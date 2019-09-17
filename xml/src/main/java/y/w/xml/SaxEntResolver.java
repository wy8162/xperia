/**
 * Yang Wang, Feb 5, 2012
 */
package y.w.xml;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author yangwang
 *
 */
public class SaxEntResolver implements EntityResolver {
	static Logger log = Logger.getLogger(SaxEntResolver.class);
	private Hashtable entities = new Hashtable();
	
	/*
	 * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
	 */
	public InputSource resolveEntity(String publicID, String systemID)
			throws SAXException, IOException {
		// Provide a method to resolve a public ID to system ID
		// Create an InputSource for the system ID found and return it.
		
		log.info("\t++++++>resolving: public ID'" + publicID + "' and system ID '" + systemID + "'");
		if (systemID.startsWith("file:///")) return new InputSource(systemID);
		return null;
	}
}
