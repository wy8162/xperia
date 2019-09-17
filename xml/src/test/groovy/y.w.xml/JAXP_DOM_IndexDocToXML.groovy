import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
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

class LogIndexDoc {
    String id;                // Document ID - will be Call ID for CORE services;  a surrogate ID for CMP services
    String logFileName;
    String application;        // [env/]app: i.e., PROD/CMP, PROD/RATINGS
    String lastModified;        // Index Creation Time
    String serviceStartTime;    // Timestamp when the service started
    String serviceName;        // Service name
    String logLineNumber;    // Line number of request message in the log file
    String time;                // Execution Time in millisecond
    String serviceRequest;    // XML request message
    String serviceResponse;  // XML response message (trimmed if longer than 10K bytes)
    String info;                // Catch whatever information like exception, DVL Requests & Responses

    public String toString() {
        return "ID=" + id + " APP=" + application + " SERVICE=" + serviceName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogFileName() {
        return logFileName;
    }

    public void setLogFileName(String logFileName) {
        this.logFileName = logFileName;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getServiceStartTime() {
        return serviceStartTime;
    }

    public void setServiceStartTime(String serviceStartTime) {
        this.serviceStartTime = serviceStartTime;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getLogLineNumber() {
        return logLineNumber;
    }

    public void setLogLineNumber(String logLineNumber) {
        this.logLineNumber = logLineNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getServiceRequest() {
        return serviceRequest;
    }

    public void setServiceRequest(String serviceRequest) {
        this.serviceRequest = serviceRequest;
    }

    public String getServiceResponse() {
        return serviceResponse;
    }

    public void setServiceResponse(String serviceResponse) {
        this.serviceResponse = serviceResponse;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

class LogInfoXmlUtils {
    private static String applicationArea = "//*[local-name() = 'ApplicationArea']";
    private static String responseArea = "//*[local-name() = 'ResponseMessageArea']";
    
    public static NodeList retrieveSubTree(String xpathExpr, String xmldoc) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        Document doc = documentBuilderFactory.newDocumentBuilder().parse(new ByteArrayInputStream(xmldoc.getBytes("UTF-8")));
        
        XPathExpression expr = XPathFactory.newInstance().newXPath().compile(xpathExpr);
        NodeList nodes = expr.evaluate(doc, XPathConstants.NODESET);
        return nodes;
    }
    
    public static Document createDocument(String rootElement, LogIndexDoc idx) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        Document doc = documentBuilderFactory.newDocumentBuilder().newDocument();
        Element root = doc.createElement(rootElement);
        doc.appendChild(root);
        
        Element ele = doc.createElement("id");
        Text text = doc.createTextNode("XML Developer's Guide");
        ele.appendChild(text);
        root.appendChild(ele);
        
        ele = doc.createElement("ID");
        text = doc.createTextNode(idx.getId());
        ele.appendChild(text);
        root.appendChild(ele);
        ele = doc.createElement("LogFilename");
        text = doc.createTextNode(idx.getLogFileName());
        ele.appendChild(text);
        root.appendChild(ele);
        ele = doc.createElement("Application");
        text = doc.createTextNode(idx.getApplication());
        ele.appendChild(text);
        root.appendChild(ele);
        ele = doc.createElement("LastModified");
        text = doc.createTextNode(idx.getLastModified());
        ele.appendChild(text);
        root.appendChild(ele);
        ele = doc.createElement("ServiceStartTime");
        text = doc.createTextNode(idx.getServiceStartTime());
        ele.appendChild(text);
        root.appendChild(ele);
        ele = doc.createElement("ServiceName");
        text = doc.createTextNode(idx.getServiceName());
        ele.appendChild(text);
        root.appendChild(ele);
        ele = doc.createElement("LogLineNumber");
        text = doc.createTextNode(idx.getLogLineNumber());
        ele.appendChild(text);
        root.appendChild(ele);
        ele = doc.createElement("Time");
        text = doc.createTextNode(idx.getTime());
        ele.appendChild(text);
        root.appendChild(ele);
        ele = doc.createElement("Info");
        text = doc.createTextNode(idx.getInfo());
        ele.appendChild(text);
        root.appendChild(ele);

        if (idx.getServiceRequest() != null && !idx.getServiceRequest().equals("")) {
            NodeList nodes = LogInfoXmlUtils.retrieveSubTree(applicationArea, idx.getServiceRequest());
            Element reqEle =  doc.createElement("Request");
            root.appendChild(reqEle);
            LogInfoXmlUtils.addNodesToDocument(doc, reqEle, nodes);
        }
        
        if (idx.getServiceResponse() != null && !idx.getServiceResponse().equals("")) {
            NodeList nodes = LogInfoXmlUtils.retrieveSubTree(applicationArea, idx.getServiceResponse());
            Element resEle =  doc.createElement("Response");
            root.appendChild(resEle);
            LogInfoXmlUtils.addNodesToDocument(doc, resEle, nodes);
            
            nodes = LogInfoXmlUtils.retrieveSubTree(responseArea, idx.getServiceResponse());
            LogInfoXmlUtils.addNodesToDocument(doc, resEle, nodes);
        }
        return doc;
    }
    
    public static void addNodesToDocument(Document doc, Node pNode, NodeList nodes) {
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            Node copyNode = doc.importNode(node, true);
            pNode.appendChild(copyNode);
        }
    }
    
    public static String formatXml(Document doc) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(out);
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);
        return out.toString();
    }
}

LogIndexDoc idxDoc = new LogIndexDoc();
idxDoc.setId("WS-123456789-123");
idxDoc.setLogFileName("prod.log");
idxDoc.setApplication("PROD");
idxDoc.setLastModified("11223345656799");
idxDoc.setInfo("COREAPI blahblah");
idxDoc.setServiceName("SearchProduct");
idxDoc.setServiceRequest(new File('C:/Users/yang_wang/awy/workspace-soaui/Request_Response_XML/get_request.xml').text);
idxDoc.setServiceResponse(new File('C:/Users/yang_wang/awy/temp/sample_response.xml').text);
idxDoc.setServiceStartTime("1995-12-31T23:59:59.999Z");
idxDoc.setTime("125");
idxDoc.setLogLineNumber("980");

String xmlfile = new File('C:/Users/yang_wang/awy/temp/sample_response.xml').text;

Document appDoc = LogInfoXmlUtils.createDocument("logInfo", idxDoc);
println LogInfoXmlUtils.formatXml(appDoc);