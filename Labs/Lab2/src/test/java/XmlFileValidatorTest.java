import parsers.XmlFileValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class XmlFileValidatorTest {
    @Test
    public void validXMLValidation(){
        Assertions.assertEquals(true, XmlFileValidator.validateXMLDocument("src/test/java/resources/Devices.xml",
                "src/main/resources/Devices.xsd"));
    }

    @Test
    public void invalidXMLValidation(){
        Assertions.assertEquals(false, XmlFileValidator.validateXMLDocument("src/test/java/resources/invalidXmlFile.xml",
                "src/main/resources/Devices.xsd"));
    }
}
