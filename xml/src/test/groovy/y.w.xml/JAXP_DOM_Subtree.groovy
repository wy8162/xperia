import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.xpath.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource; 
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

def xfile = 'C:/Users/yang_wang/awy/workspace-soaui/Request_Response_XML/get_Response.xml'

Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xfile);

def expr = XPathFactory.newInstance().newXPath().compile("//*[local-name() = 'ApplicationArea']")
def nodes = expr.evaluate(doc, XPathConstants.NODESET)

Document appArea = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument()
//Element root = appArea.createElement("request");
//appArea.appendChild(root);
for (int i = 0; i < nodes.getLength(); i++) {
    Node node = nodes.item(i);
    Node copyNode = appArea.importNode(node, true);
    appArea.appendChild(copyNode);
}

Transformer transformer = TransformerFactory.newInstance().newTransformer();
DOMSource source = new DOMSource(appArea);
StreamResult result = new StreamResult(System.out);
transformer.setOutputProperty(OutputKeys.INDENT, "yes");
transformer.transform(source, result);
