@Grapes([
    @Grab("xerces#xercesImpl;2.7.1"),
    @GrabConfig(systemClassLoader=true, initContextClassLoader=true) // This line is important to add the jars to CLASSPATH
])

def xml = '''
<?xml version="1.0" encoding="UTF-8"?>

<S2SCTOqf:SCTOqfBlkCredTrf xsi:schemaLocation="urn:S2SCTOqf:xsd:$SCTOqfBlkCredTrf SCTOqfBlkCredTrf.xsd"
                           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                           xmlns:S2SCTOqf="urn:S2SCTOqf:xsd:$SCTOqfBlkCredTrf">
    <S2SCTOqf:SndgInst>EBAPFRP0</S2SCTOqf:SndgInst>
    <S2SCTOqf:RcvgInst>CCFRFRP0</S2SCTOqf:RcvgInst>
    <S2SCTOqf:SrvcId>SCT</S2SCTOqf:SrvcId>
    <S2SCTOqf:TstCode>T</S2SCTOqf:TstCode>
    <S2SCTOqf:FType>OQF</S2SCTOqf:FType>
    <S2SCTOqf:FileRef>NSCT%R10%</S2SCTOqf:FileRef>
    <S2SCTOqf:RoutingInd>DIR</S2SCTOqf:RoutingInd>
    <S2SCTOqf:FileBusDt>2019-09-10</S2SCTOqf:FileBusDt>
    <S2SCTOqf:FileCycleNo>10</S2SCTOqf:FileCycleNo>
    <S2SCTOqf:ReqToModfyPmt xmlns="urn:iso:std:iso:20022:tech:xsd:camt.087.001.05">
        <Assgnmt>
            <Id>%R10%</Id>
            <Assgnr>
                <Agt>
                    <FinInstnId>
                        <BICFI>WIBADE5W</BICFI>
                    </FinInstnId>
                </Agt>
            </Assgnr>
            <Assgne>
                <Agt>
                    <FinInstnId>
                        <BICFI>CCFRFRP0</BICFI>
                    </FinInstnId>
                </Agt>
            </Assgne>
            <CreDtTm>2019-09-10T09:30:47.0Z</CreDtTm>
        </Assgnmt>
        <Case>
            <Id>CAMT087-CASEID-111009</Id>
            <Cretr>
                <Agt>
                    <FinInstnId>
                        <BICFI>WIBADE5W</BICFI>
                    </FinInstnId>
                </Agt>
            </Cretr>
        </Case>
        <Undrlyg>
            <IntrBk>
                <OrgnlGrpInf>
                    <OrgnlMsgId>PACS008-CR1272-111009</OrgnlMsgId>
                    <OrgnlMsgNmId>pacs.008.001.02</OrgnlMsgNmId>
                </OrgnlGrpInf>
                <OrgnlEndToEndId>E2E-PACS008-111009</OrgnlEndToEndId>
                <OrgnlTxId>TXID-PACS008-111009</OrgnlTxId>
                <OrgnlIntrBkSttlmAmt Ccy="EUR">400</OrgnlIntrBkSttlmAmt>
                <OrgnlIntrBkSttlmDt>2019-09-10</OrgnlIntrBkSttlmDt>
                <OrgnlTxRef>
                    <RmtInf>
                        <Ustrd>FILE SAMPLE STEP2</Ustrd>
                    </RmtInf>
                    <DbtrAgt>
                        <FinInstnId>
                            <BICFI>BOFIIE2D</BICFI>
                        </FinInstnId>
                    </DbtrAgt>
                    <CdtrAgt>
                        <FinInstnId>
                            <BICFI>ABNAFRPPXXX</BICFI>
                        </FinInstnId>
                    </CdtrAgt>
                </OrgnlTxRef>
            </IntrBk>
        </Undrlyg>
        <Mod>
            <IntrBkSttlmDt>
                %D-1%
            </IntrBkSttlmDt>
        </Mod>
    </S2SCTOqf:ReqToModfyPmt>
</S2SCTOqf:SCTOqfBlkCredTrf>
'''

import org.apache.xml.serialize.OutputFormat
import org.apache.xml.serialize.XMLSerializer
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.*
//import org.custommonkey.xmlunit.*

class StringOutputStream extends OutputStream {
    StringBuilder sb
    
    StringOutputStream() { sb = new StringBuilder(512) }
    
    void write(int b) {
        sb.append((char)b)
    }
    
    String toString() { return sb.toString() }
}


/*
 * Reformat an XML string into pretty, well formated XML string.
 * @param xml - XML string
 @ @return Formated XML string or exception message if error.
 */
String prettyXml(String xml) {
    def xis = new ByteArrayInputStream(xml.toString().getBytes("UTF-8"))
    
    def builder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
    def xos = new StringOutputStream()
    
    try {
        def doc = builder.parse(xis)
        OutputFormat fmt  = new OutputFormat(doc)
        fmt.setIndenting(true)
        XMLSerializer serializer = new XMLSerializer(xos, fmt)
        serializer.serialize(doc)
        return xos.toString()
    } catch (Exception e) {
        return e.toString()
    }
}

println prettyXml(xml)
