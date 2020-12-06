import computors.*;
import parsers.DeviceBuilder;
import parsers.DevicesParser;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {
    DevicesParser periodicalParser;
    Device gamepad;
    Device monitor;
    Device processor;

    public ParserTest(){
        periodicalParser = new DevicesParser();
        gamepad = new Device();
        monitor = new Device();
        processor = new Device();

        gamepad.setName("Gamepad");
        gamepad.setID(1);
        gamepad.setOrigin("Ukraine");
        gamepad.setPrice(250.5);
        gamepad.setCritical(false);
        gamepad.setType(new Type(false, 15, false, Group.IODevices, Port.USB));

        monitor.setName("Monitor");
        monitor.setID(2);
        monitor.setOrigin("China");
        monitor.setPrice(4000);
        monitor.setCritical(true);
        monitor.setType(new Type(true, 45, false, Group.Multimedia, Port.LPT));

        processor.setName("Processor");
        processor.setID(3);
        processor.setOrigin("USA");
        processor.setPrice(199.99);
        processor.setCritical(true);
        processor.setType(new Type(true, 20, true, Group.Other, Port.Other));
    }

    @Test
    public void DOMParserTest() throws ParserConfigurationException, XMLStreamException, SAXException, IOException {
        periodicalParser.setTypeOfParser("DOM");
        Computer computer = periodicalParser.parseXmlDocument("src/test/java/resources/Devices.xml",
                "src/main/resources/Devices.xsd");
        assertEquals(computer.getDevices().get(0), gamepad);
        assertEquals(computer.getDevices().get(1), monitor);
        assertEquals(computer.getDevices().get(2), processor);
    }

    @Test
    public void SAXParserTest() throws ParserConfigurationException, XMLStreamException, SAXException, IOException {
        periodicalParser.setTypeOfParser("SAX");
        Computer computer = periodicalParser.parseXmlDocument("src/test/java/resources/Devices.xml",
                "src/main/resources/Devices.xsd");
        assertEquals(computer.getDevices().get(0), gamepad);
        assertEquals(computer.getDevices().get(1), monitor);
        assertEquals(computer.getDevices().get(2), processor);
    }

    @Test
    public void StaxParserTest() throws ParserConfigurationException, XMLStreamException, SAXException, IOException {
        periodicalParser.setTypeOfParser("STAX");
        Computer computer = periodicalParser.parseXmlDocument("src/test/java/resources/Devices.xml",
                "src/main/resources/Devices.xsd");
        assertEquals(computer.getDevices().get(0), gamepad);
        assertEquals(computer.getDevices().get(1), monitor);
        assertEquals(computer.getDevices().get(2), processor);
    }

    @Test
    public void XMLHandlerTest(){
        DeviceBuilder builder = new DeviceBuilder();
        builder.setTag("name", "Gamepad");
        builder.setTag("id", "1");
        builder.setTag("origin", "Ukraine");
        builder.setTag("price", "250.5");
        builder.setTag("critical","false");
        builder.setTag("peripheral", "false");
        builder.setTag("consumption", "15");
        builder.setTag("cooler", "false");
        builder.setTag("group", "IODevices");
        builder.setTag("port", "USB");
        builder.saveElement("device");

        builder.setTag("name", "Monitor");
        builder.setTag("id", "2");
        builder.setTag("origin", "China");
        builder.setTag("price", "4000");
        builder.setTag("critical","true");
        builder.setTag("peripheral", "true");
        builder.setTag("consumption", "45");
        builder.setTag("cooler", "false");
        builder.setTag("group", "Multimedia");
        builder.setTag("port", "LPT");
        builder.saveElement("device");

        builder.setTag("name", "Processor");
        builder.setTag("id", "3");
        builder.setTag("origin", "USA");
        builder.setTag("price", "199.99");
        builder.setTag("critical","true");
        builder.setTag("peripheral", "true");
        builder.setTag("consumption", "20");
        builder.setTag("cooler", "true");
        builder.setTag("group", "Other");
        builder.setTag("port", "Other");
        builder.saveElement("device");

        assertEquals(gamepad, builder.getDevices().get(0));
        assertEquals(monitor, builder.getDevices().get(1));
        assertEquals(processor, builder.getDevices().get(2));
    }
}