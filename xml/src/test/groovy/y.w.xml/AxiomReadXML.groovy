@Grapes([
        @Grab(group = 'org.apache.ws.commons.axiom', module = 'axiom-api', version = '1.2.12'),
        @Grab(group = 'org.apache.ws.commons.axiom', module = 'axiom-impl', version = '1.2.12')
])
import org.apache.axiom.om.OMAttribute
import org.apache.axiom.om.OMElement
import org.apache.axiom.om.OMNamespace
import org.apache.axiom.om.OMNode
import org.apache.axiom.om.impl.builder.StAXOMBuilder
import org.apache.axiom.om.xpath.AXIOMXPath

/**
 * Walk subtree for element. This recursively walks through the document
 * nodes under an element, accumulating summary information.
 *
 * @param element element to be walked
 * @param summary document summary information
 */
void walkElement(OMElement element) {

    // include attribute values in summary
    for (Iterator iter = element.getAllAttributes(); iter.hasNext();) {
        OMAttribute attr = (OMAttribute)iter.next();
        println "Attribute=" + attr.getAttributeValue();
    }

    // loop through children
    for (Iterator iter = element.getChildren(); iter.hasNext();) {

        // handle child by type
        OMNode child = (OMNode)iter.next();
        int type = child.getType();
        if (type == OMNode.TEXT_NODE) {
            if (!child.getText().isAllWhitespace()) println "Text=" + child.getText();
        } else if (type == OMNode.ELEMENT_NODE) {
            walkElement((OMElement)child);
        }

    }
}


/* If read from a file:
XMLStreamReader xmlParser = XMLInputFactory.newInstance().createXMLStreamReader(
                            new FileInputStream("/Users/yangwang/Downloads/dsl.xml"));
StAXOMBuilder xmlBuilder = new StAXOMBuilder( xmlParser );
 */

/* Alternatively, you can create the builder the following way. But under the hood, a XMLStreamReader will
 * eventually be created.
 StAXOMBuilder xmlBuilder = new StAXOMBuilder( new FileInputStream("/Users/yangwang/Downloads/dsl.xml") );
 */

String xml = new URL("http://www.w3schools.com/xml/simple.xml").text
StAXOMBuilder xmlBuilder = new StAXOMBuilder( new ByteArrayInputStream( xml.getBytes()) );

OMElement root = xmlBuilder.getDocumentElement();

for (Iterator ite = root.getAllDeclaredNamespaces(); ite.hasNext(); ) {
    OMNamespace ns = ite.next();
    println "${ns.getPrefix()} = ${ns.getNamespaceURI()}"
}

walkElement(root)

// Using XPATH
AXIOMXPath xpath = new AXIOMXPath("/breakfast_menu/food[1]");
OMElement selectedNode = (OMElement) xpath.selectSingleNode(root);
println ">>>>>>>>Food selected"
walkElement(selectedNode)

xpath = new AXIOMXPath("/breakfast_menu/food[calories <= 600]");
xpath.selectNodes(root).each {
    println "=====>Selected:"
    walkElement(it)
}
