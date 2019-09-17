/**
 * DOM with JAXP
 * This is to use DOM interface provided by JAXP.
 * http://java.sun.com/webservices/reference/tutorials/jaxp/html/dom.html
 */
package y.w.xml;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
 
public class JaxpDOM {
    static Logger log = Logger.getLogger(JaxpDOM.class);
    
    // Property constants for validation
    static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    static final String W3C_XML_SCHEMA		 = "http://www.w3.org/2001/XMLSchema";
    static final String JAXP_SCHEMA_SOURCE   = "http://java.sun.com/xml/jaxp/properties/schemaSource";
    
    // Lexical Control Parameters
    boolean ignoreWhitespace = false;
    boolean ignoreComments = false;
    boolean putCDATAIntoText = false;
    boolean createEntityRefs = false;
    
    boolean xincludeAware = false;
    boolean schemaValidation = false;
    String []  schemaSources = null;

    DocumentBuilderFactory documentBuilderFactory = null;
    DocumentBuilder 			  documentBuilder = null;
    SaxErrorHandler errorHandler	  = null;
    SaxEntResolver entResolver		  = null;
    
    public JaxpDOM() {
        // The actual DocumentBuilderFactory created is determined by system property:
        //      javax.xml.parsers.DocumentBuilderFactory
        // The following sets the System property to use Xerces-J implementation
        System.getProperties().setProperty("javax.xml.parsers.DocumentBuilderFactory", "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
    }
    

    /**
     * This method has to be called before initXmlDocumentFileValidation.
     * @param xIncludeAware
     */
    public void setXIncludeAware( boolean xIncludeAware ) {
    	this.xincludeAware = xIncludeAware;
    }
    
    
    /**
     * This method has to be called before initXmlDocumentFileValidation. By default, initXmlDocumentFileValidation assumes
     * DTD validation.
     * @param schemaValidation
     * @param schemaFiles
     */
    public void setSchemaValidation(boolean schemaValidation, String [] schemaFiles) {
    	this.schemaValidation = schemaValidation;
    	this.schemaSources = schemaFiles;
    }
    
    /**
     * Create an empty XML Document without validation.
     * @return: true = successful, false = failed
     */
    public Document initXmlDocument() {
    	if (documentBuilder != null) return null; // This JaxpDOM has already been initialized.
  
        boolean result = initDocumentBuilder(false);
        if (result) {
            log.debug("DocumentBuilderFactory=" + documentBuilderFactory.getClass().getName());
            log.debug("DocumentBuilder=" + documentBuilder.getClass().getName());
        }
        if (!result) return null;
        return documentBuilder.newDocument();
    }

    
    /**
     * Create an XML Document based on an XML file, without validation.
     * @return: true = successful, false = failed
     */
    public Document initXmlDocumentFile(String fileName) {
    	if (documentBuilder != null) return null; // This JaxpDOM has already been initialized.
  
        boolean result = initDocumentBuilder(false);
        if (result) {
            log.debug("DocumentBuilderFactory=" + documentBuilderFactory.getClass().getName());
            log.debug("DocumentBuilder=" + documentBuilder.getClass().getName());
        }
        if (!result) return null;
        try {
			return documentBuilder.parse(fileName);
		} catch (SAXException e) {
			log.fatal("Failed to parse XML file: " + fileName + "\n" + e);
		} catch (IOException e) {
			log.fatal("Failed to read XML file: " + fileName + "\n" + e);
		}
        return null;
    }

 
    /**
     * Create an XML Document based on an XML file, with validation.
     * @return: true = successful, false = failed
     */
    public Document initXmlDocumentFileValidation(String fileName) {
    	if (documentBuilder != null) return null; // This JaxpDOM has already been initialized.
  
        boolean result = initDocumentBuilder(true);
        if (result) {
            log.debug("DocumentBuilderFactory=" + documentBuilderFactory.getClass().getName());
            log.debug("DocumentBuilder=" + documentBuilder.getClass().getName());
        }
        if (!result) return null;
        try {
			return documentBuilder.parse(fileName);
		} catch (SAXException e) {
			log.fatal("Failed to parse XML file: " + fileName + "\n" + e);
		} catch (IOException e) {
			log.fatal("Failed to read XML file: " + fileName + "\n" + e);
		}
        return null;
    }
    
    private void setXInclude() {
        if (xincludeAware) {
            log.info("Configuring XInclude feature");
            documentBuilderFactory.setXIncludeAware(true);
            
        	// Enable this feature so that the validation will be applied to the infoset after XInclude applied
        	// http://xerces.apache.org/xerces2-j/faq-xinclude.html
        	try {
				documentBuilderFactory.setFeature("http://apache.org/xml/features/validation/schema", true);
			} catch (ParserConfigurationException e) {
				log.fatal("Feature 'http://apache.org/xml/features/validation/schema' is not supported\n" + e );
			}
        }
    }
    
    private boolean initDocumentBuilder(boolean validating) {
        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setIgnoringComments(ignoreComments);
        documentBuilderFactory.setIgnoringElementContentWhitespace(ignoreWhitespace);
        documentBuilderFactory.setCoalescing(putCDATAIntoText);
        documentBuilderFactory.setExpandEntityReferences(!createEntityRefs);
        
        if (validating) {
        	documentBuilderFactory.setValidating(true);
        	if (schemaValidation) {
        		documentBuilderFactory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
        		if (schemaSources != null) 
        			documentBuilderFactory.setAttribute(JAXP_SCHEMA_SOURCE, schemaSources);
        			//documentBuilderFactory.setAttribute(JAXP_SCHEMA_SOURCE, new File(schemaSource));
        	}
        }
        
        documentBuilderFactory.setNamespaceAware(true);
        setXInclude();
        
        try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
	        errorHandler = new SaxErrorHandler();
	        entResolver	 = new SaxEntResolver();
	        documentBuilder.setErrorHandler(errorHandler);
	        documentBuilder.setEntityResolver(entResolver);
		} catch (ParserConfigurationException e) {
			log.fatal("Failed to configure and create DocumentBuilder." + e);
			e.printStackTrace();
			return false;
		}
        if (documentBuilder.isXIncludeAware()) log.debug("DocumentBuilder IS XINCLUDE Aware");
        return true;
    }
    
    public boolean isValid() {
		return errorHandler.isValid();
    }
    
    // ---------------------
    // Some utility methods
    // ---------------------

    /**
     * Find the named subnode in a node's sublist.
     * <li>Ignores comments and processing instructions.
     * <li>Ignores TEXT nodes (likely to exist and contain
     *         ignorable whitespace, if not validating.
     * <li>Ignores CDATA nodes and EntityRef nodes.
     * <li>Examines element nodes to find one with
     *        the specified name.
     * </ul>
     * @param name  the tag name for the element to find
     * @param node  the element node to start searching from
     * @return the Node found
     */
    public Node findSubNode(String name, Node node) {
        if (node.getNodeType() != Node.ELEMENT_NODE) {
            System.err.println(
                    "Error: Search node not of element type");
            System.exit(22);
        }

        if (! node.hasChildNodes()) return null;

        NodeList list = node.getChildNodes();
        for (int i=0; i < list.getLength(); i++) {
            Node subnode = list.item(i);
            if (subnode.getNodeType() == Node.ELEMENT_NODE) {
                if (subnode.getNodeName().equals(name)) return subnode;
            }
        }
        return null;
    }
    
    /**
     * Return the text that a node contains. This routine:<ul>
     * <li>Ignores comments and processing instructions.
     * <li>Concatenates TEXT nodes, CDATA nodes, and the results of
     *     recursively processing EntityRef nodes.
     * <li>Ignores any element nodes in the sublist.
     *     (Other possible options are to recurse into element 
     *      sublists or throw an exception.)
     * </ul>
     * @param    node  a  DOM node
     * @return   a String representing its contents
     */
    public String getText(Node node) {
       StringBuffer result = new StringBuffer();
       if (! node.hasChildNodes()) return "";

       NodeList list = node.getChildNodes();
       for (int i=0; i < list.getLength(); i++) {
           Node subnode = list.item(i);
           if (subnode.getNodeType() == Node.TEXT_NODE) {
               result.append(subnode.getNodeValue());
           }
           else if (subnode.getNodeType() ==
                   Node.CDATA_SECTION_NODE) 
           {
               result.append(subnode.getNodeValue());
           }
           else if (subnode.getNodeType() ==
                   Node.ENTITY_REFERENCE_NODE) 
           {
               // Recurse into the subtree for text
               // (and ignore comments)
               result.append(getText(subnode));
           }
       }
       return result.toString();
    }
    
    public void echo(Node n) {
        outputIndentation();

        int type = n.getNodeType();
        switch (type) {
        case Node.ATTRIBUTE_NODE:
            System.out.print("ATTR:");
            displayNode(n);
            break;
        case Node.CDATA_SECTION_NODE:
            System.out.print("CDATA:");
            displayNode(n);
            break;
        case Node.COMMENT_NODE:
            System.out.print("COMM:");
            displayNode(n);
            break;
        case Node.DOCUMENT_FRAGMENT_NODE:
            System.out.print("DOC_FRAG:");
            displayNode(n);
            break;
        case Node.DOCUMENT_NODE:
            System.out.print("DOC:");
            displayNode(n);
            break;
        case Node.DOCUMENT_TYPE_NODE:
            System.out.print("DOC_TYPE:");
            displayNode(n);

            NamedNodeMap nodeMap = ((DocumentType)n).getEntities();
            indent += 2;
            for (int i = 0; i < nodeMap.getLength(); i++) {
                Entity entity = (Entity)nodeMap.item(i);
                echo(entity);
            }
            indent -= 2;
            break;
        case Node.ELEMENT_NODE:
            System.out.print("ELEM:");
            displayNode(n);

            NamedNodeMap atts = n.getAttributes();
            indent += 2;
            for (int i = 0; i < atts.getLength(); i++) {
                Node att = atts.item(i);
                echo(att);
            }
            indent -= 2;
            break;
        case Node.ENTITY_NODE:
            System.out.print("ENT:");
            displayNode(n);
            break;
        case Node.ENTITY_REFERENCE_NODE:
            System.out.print("ENT_REF:");
            displayNode(n);
            break;
        case Node.NOTATION_NODE:
            System.out.print("NOTATION:");
            displayNode(n);
            break;
        case Node.PROCESSING_INSTRUCTION_NODE:
            System.out.print("PROC_INST:");
            displayNode(n);
            break;
        case Node.TEXT_NODE:
            System.out.print("TEXT:");
            displayNode(n);
            break;
        default:
            System.out.print("UNSUPPORTED NODE: " + type);
            displayNode(n);
            break;
        }

        indent++;
        for (Node child = n.getFirstChild(); child != null;
             child = child.getNextSibling()) {
            echo(child);
        }
        indent--;
    }
    
    
    private int indent = 0;
    private final String basicIndent = " ";

    public void outputIndentation() {
    	 for (int i = 0; i < indent; i++) {
             System.out.print(basicIndent);
         }
    }

    public void displayNode(Node n) {
        System.out.print(" nodeName=\"" + n.getNodeName() + "\"");

        String val = n.getNamespaceURI();
        if (val != null) {
        	System.out.print(" uri=\"" + val + "\"");
        }

        val = n.getPrefix();
        if (val != null) {
        	System.out.print(" pre=\"" + val + "\"");
        }

        val = n.getLocalName();
        if (val != null) {
        	System.out.print(" local=\"" + val + "\"");
        }

        val = n.getNodeValue();
        if (val != null) {
        	System.out.print(" nodeValue=");
            if (val.trim().equals("")) {
                // Whitespace
            	System.out.print("[WS]");
            } else {
            	System.out.print("\"" + n.getNodeValue() + "\"");
            }
        }
        System.out.println();
    }
}