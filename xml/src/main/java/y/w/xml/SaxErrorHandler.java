/**
 * SAX error handler.
 * 
 * Yang Wang, Feb 5, 2012
 */
package y.w.xml;

import org.apache.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * @author yangwang
 *
 */
public class SaxErrorHandler implements ErrorHandler {
	static Logger log = Logger.getLogger(SaxErrorHandler.class);
	private boolean valid = true;
	
	public boolean isValid() { return valid; }
	public void reset() { valid = true; }

	/* (non-Javadoc)
	 * @see org.xml.sax.ErrorHandler#error(org.xml.sax.SAXParseException)
	 */
	public void error(SAXParseException exception) throws SAXException {
	    log.error("Error: " + exception.getMessage());
	    log.error(" at line " + exception.getLineNumber() + ", column " + exception.getColumnNumber());
	    log.error(" in entity " + exception.getSystemId());
	    valid = false;
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ErrorHandler#fatalError(org.xml.sax.SAXParseException)
	 */
	public void fatalError(SAXParseException exception) throws SAXException {
	    log.fatal("Fatal Error: " + exception.getMessage());
	    log.fatal(" at line " + exception.getLineNumber() + ", column " + exception.getColumnNumber()); 
	    log.fatal(" in entity " + exception.getSystemId());
	    valid = false;
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ErrorHandler#warning(org.xml.sax.SAXParseException)
	 */
	public void warning(SAXParseException exception) throws SAXException {
	    log.warn("Warning: " + exception.getMessage());
	    log.warn(" at line " + exception.getLineNumber() + ", column " + exception.getColumnNumber());
	    log.warn(" in entity " + exception.getSystemId());
	    valid = false;
	}
}
