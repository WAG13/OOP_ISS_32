package parsers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.logging.Logger;

public class DomParser implements XmlParser {
    private final DeviceBuilder deviceBuilder;
    private final Logger log = Logger.getLogger(DomParser.class.getName());

    DomParser(DeviceBuilder deviceBuilder){
        this.deviceBuilder = deviceBuilder;
    }

    @Override
    public void parseXMLDocument(String xmlPath) {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document doc = null;
        try {
            builder = builderFactory.newDocumentBuilder();
            doc = builder.parse(xmlPath);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            log.info(e.getMessage());
        }

        assert doc != null;
        Element root = doc.getDocumentElement();

        NodeList devicesNodes = root.getElementsByTagName(deviceBuilder.getRootName());

        for (int i = 0; i < devicesNodes.getLength(); i++) {

            Element deviceElement = (Element) devicesNodes.item(i);
            deviceBuilder.setTag("id",deviceElement.getAttributes().item(0).getNodeValue());
            NodeList childNodes = deviceElement.getChildNodes();

            for (int j = 0; j < childNodes.getLength(); j++) {
                if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {

                    Element child = (org.w3c.dom.Element) childNodes.item(j);

                    deviceBuilder.setTag(child.getNodeName(), getChildValue(deviceElement, child.getNodeName()));
                    NodeList childChildNodes = child.getChildNodes();

                    for (int k = 0; k < childChildNodes.getLength(); k++) {
                        if (childChildNodes.item(k).getNodeType() == Node.ELEMENT_NODE) {
                            Element childChild = (org.w3c.dom.Element) childChildNodes.item(k);
                            deviceBuilder.setTag(childChild.getNodeName(), getChildValue(child, childChild.getNodeName()));
                        }
                    }
                }
            }
            deviceBuilder.saveElement(deviceElement.getNodeName());
        }
    }


    private String getChildValue(Element element, String name) {
        Element child = (Element) element.getElementsByTagName(name).item(0);
        if (child == null)  return "";
        Node node = child.getFirstChild();
        return node.getNodeValue();
    }


}