@Grapes([
    @Grab(group='org.apache.ws.commons.axiom', module='axiom-api', version='1.2.12'),
    @Grab(group='org.apache.ws.commons.axiom', module='axiom-impl', version='1.2.12')
])

// Not all imports are needed for this test. Keep them for later references.
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMNode;
import org.apache.axiom.om.OMText;

// Obtain a factory
OMFactory factory = OMAbstractFactory.getOMFactory();
OMNamespace ns = factory.createOMNamespace("http://wy8162.com", "ns");
OMElement root = factory.createOMElement("book", ns);
OMElement name = factory.createOMElement("name", ns);
OMElement author=factory.createOMElement("author", ns);
OMElement isbn = factory.createOMElement("isbn", ns);
OMText    omtext=factory.createOMText("Yang Wang");
OMAttribute type = factory.createOMAttribute("type", null, "web-services");

name.setText("Axis 2 - Getting Started")
author.addChild(omtext);
root.addAttribute(type);
root.addChild(name);
root.addChild(author);
root.addChild(isbn);

// Let's create a writer - write it to console.
// Serialize() can be called several times. This is because a tree will be built and cache flag will be set
XMLStreamWriter writer = XMLOutputFactory.newInstance().createXMLStreamWriter(System.out);
root.serialize(writer);
writer.flush();

// But serializeAndConsume() will consume the elements. No tree will be built and cache flag will be reset
root.serializeAndConsume(System.out);
