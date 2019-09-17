@Grapes([
    @Grab(group='org.apache.ws.commons.axiom', module='axiom-api', version='1.2.12'),
    @Grab(group='org.apache.ws.commons.axiom', module='axiom-impl', version='1.2.12')
])

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNode;
import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMText;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axiom.om.xpath.AXIOMXPath;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axiom.soap.SOAPEnvelope;

OMFactory factory = OMAbstractFactory.getOMFactory();
OMNamespace ns = factory.createOMNamespace("http://www.wy8162.com/e2e", "ns");
OMElement root = factory.createOMElement("family", ns);
OMAttribute attr = factory.createOMAttribute("religious", null, "Christian");
root.addAttribute( attr );
OMElement member1 = factory.createOMElement("member", ns);
OMAttribute role1 = factory.createOMAttribute("type", null, "father");
OMElement   name1 = factory.createOMElement("name", ns);
            name1.setText("Yang Wang");
OMElement    sex1 = factory.createOMElement("sex", ns);
             sex1.setText("male");
          member1.addChild(name1);
          member1.addChild(sex1);
             root.addAttribute(role1);
             root.addChild(member1);
OMElement member2 = factory.createOMElement("member", ns);
OMAttribute role2 = factory.createOMAttribute("type", null, "son");
OMElement   name2 = factory.createOMElement("name", ns);
            name2.setText("Jack Wang");
OMElement    sex2 = factory.createOMElement("sex", ns);
             sex2.setText("male");
          member2.addChild(name2);
          member2.addChild(sex2);
             root.addAttribute(role2);
             root.addChild(member2);
SOAPFactory soapFactory = OMAbstractFactory.getSOAP12Factory();
SOAPEnvelope env = soapFactory.getDefaultEnvelope();
env.getBody().addChild(root);
println env