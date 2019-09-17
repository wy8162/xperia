package y.w.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHandler extends DefaultHandler {
	public void startElement(String namespaceURI, String localName,
							 String qualifiedName, Attributes atts) throws SAXException {

		System.out.print("\t----->Namespace=" + namespaceURI + " LocalName=" + localName +
						 " QName=" + qualifiedName + "\n");
		if (atts.getLength() > 0) {
			System.out.print("\t\tAttributes: ");
			for (int i=0; i<atts.getLength(); i++)
				System.out.print(atts.getQName(i) + "=" + atts.getValue(i) + " ");
			System.out.print("\n");
		}
	
	}

	public void endElement(String namespaceURI, String localName,
						   String qualifiedName) throws SAXException {
	
		System.out.print("\t----->End of Element=" + "Namespace=" + namespaceURI + " LocalName=" + localName +
						 " QName=" + qualifiedName + "\n");
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException {
        if (length <= 0) return;
		System.out.print("\t\tValue=");
		for (int i = start; i < start+length; i++) {
			System.out.print(ch[i]); 
		}
		System.out.print("\n");
	}

    /**
     * When a DTD is present, the parser will no longer call the characters() method on white space that it knows 
     * to be irrelevant. From the standpoint of an application that is interested in processing only the XML data, 
     * that is a good thing because the application is never bothered with white space that exists purely to make 
     * the XML file readable. On the other hand, if you are writing an application that filters an XML data file 
     * and if you want to output an equally readable version of the file, then that white space would no longer be 
     * irrelevant: it would be essential. To get those characters, you would add the ignorableWhitespace method to 
     * your application. To process any (generally) ignorable white space that the parser sees, you would need to 
     * add something like the following code to implement the ignorableWhitespace event handler.
     */
    public void ignorableWhitespace (char buf[], int start, int length) throws SAXException
    {
        System.out.print("\t......>IGNORABLE\n");
    }
	
	public void skippedEntity(String name) {
		System.out.print("\t----->Skipped entity=" + name);
	}
	
	public InputSource resolveEntity(String publicId, String systemId)  {
		System.out.print("\t----->Resolve Entity: publicId" + publicId + " systemId=" + systemId);
		return null;
	}
}