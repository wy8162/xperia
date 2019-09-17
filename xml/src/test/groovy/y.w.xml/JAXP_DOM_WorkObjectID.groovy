import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

def xmldoc = new File("C:/Users/yang_wang/awy/temp/SomeFile_req.xml").text

String applicationArea = "//*[local-name() = 'ApplicationArea']";
String responseArea = "//*[local-name() = 'ResponseMessageArea']";
String authID = "//*[local-name() = 'AuthorizationID']";
String BODID  = "//*[local-name() = 'ID']";

DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
documentBuilderFactory.setNamespaceAware(true);
Document doc, reqdoc, resdoc;
doc = documentBuilderFactory.newDocumentBuilder().parse(new ByteArrayInputStream(xmldoc.getBytes("UTF-8")));
reqdoc = documentBuilderFactory.newDocumentBuilder().newDocument()
resdoc = documentBuilderFactory.newDocumentBuilder().newDocument()
def reqroot= reqdoc.createElement("request")
def resroot= resdoc.createElement("request")
reqdoc.appendChild(reqroot)
resdoc.appendChild(resroot)

XPathExpression expr;
NodeList nodes;
expr = XPathFactory.newInstance().newXPath().compile(applicationArea);
nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
nodes.each {
    Node copyNode = reqdoc.importNode(it, true);
    reqroot.appendChild(copyNode)
}

expr = XPathFactory.newInstance().newXPath().compile(responseArea);
nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
nodes.each {
    Node copyNode = resdoc.importNode(it, true);
    resroot.appendChild(copyNode)
}

expr = XPathFactory.newInstance().newXPath().compile(authID);
nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
nodes.each { println it.getTextContent() }

expr = XPathFactory.newInstance().newXPath().compile(BODID);
nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
nodes.each { println it.getTextContent() }

