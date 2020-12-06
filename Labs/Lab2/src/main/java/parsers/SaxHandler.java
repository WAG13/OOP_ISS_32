package parsers;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHandler extends DefaultHandler {
    private final DeviceBuilder deviceBuilder;
    private StringBuilder data;

    public SaxHandler(DeviceBuilder deviceBuilder){
        this.deviceBuilder = deviceBuilder;
        data = null;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        data = new StringBuilder();
        int attributeLength = attributes.getLength();
        for (int i = 0; i < attributeLength; i++) {
                deviceBuilder.setTag(attributes.getQName(i), attributes.getValue(i));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if(qName.equalsIgnoreCase(deviceBuilder.getRootName()))
            deviceBuilder.saveElement(qName);
        deviceBuilder.setTag(qName, data.toString());
        data = new StringBuilder();
    }


    @Override
    public void characters(char[] ch, int start, int length) {
        data.append(new String(ch, start, length));
    }


}