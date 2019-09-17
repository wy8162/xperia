@Grapes([
    @Grab(group='org.apache.ws.commons.axiom', module='axiom-api', version='1.2.12'),
    @Grab(group='org.apache.ws.commons.axiom', module='axiom-impl', version='1.2.12')
])

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNode;
import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMText;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axiom.om.xpath.AXIOMXPath;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axiom.soap.SOAPEnvelope;

String xml = new URL("http://www.w3schools.com/xml/simple.xml").text
StAXOMBuilder xmlBuilder = new StAXOMBuilder( new ByteArrayInputStream( xml.getBytes()) );

OMElement root = xmlBuilder.getDocumentElement();

SOAPFactory soapFactory = OMAbstractFactory.getSOAP11Factory();
SOAPEnvelope env = soapFactory.getDefaultEnvelope();
env.getBody().addChild(root);
println env;