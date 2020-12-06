package parsers;

import computors.Computer;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class DevicesParser {
    private XmlParser parser;
    private Computer result;
    private String typeOfParser;

    public void setTypeOfParser(String typeOfParser){
        this.typeOfParser = typeOfParser;
    }

    public Computer parseXmlDocument(String pathToXmlDocument, String pathToXsdFile) throws ParserConfigurationException, SAXException, IOException, XMLStreamException {

        if(XmlFileValidator.validateXMLDocument(pathToXmlDocument,pathToXsdFile)) {
            DeviceBuilder deviceBuilder = new DeviceBuilder();
            switch (typeOfParser.toUpperCase()) {
                case "SAX": {
                    parser = new SaxParser(deviceBuilder);
                    break;
                }
                case "DOM": {
                    parser = new DomParser(deviceBuilder);
                    break;
                }
                case "STAX": {
                    parser = new StaxParser(deviceBuilder);
                    break;
                }
                default:
                    break;
            }
            parser.parseXMLDocument(pathToXmlDocument);
            result = new Computer(deviceBuilder.getDevices());

            result.sort();
        }
        return result;
    }
}
