package parsers;

import java.util.ArrayList;
import java.util.List;
import computors.Device;
import computors.Group;
import computors.Port;

public class DeviceBuilder {
    private Device device;
    private List<Device> devices = new ArrayList<>();
    private final String rootName;

    public DeviceBuilder() {
        device = new Device();
        rootName = "Device";
    }

    public List<Device> getDevices(){
        return devices;
    }

    public String getRootName(){
        return rootName;
    }

    public void setTag(String tag, String value){
        switch (tag.toLowerCase()){
            case "device":
                device = new Device();
            case "name":
                device.setName(value);
                break;
            case "id":
                device.setID(Integer.parseInt(value));
                break;
            case "origin":
                device.setOrigin(value);
                break;
            case "price":
                device.setPrice(Double.parseDouble(value));
                break;
            case "critical":
                device.setCritical(Boolean.parseBoolean(value));
                break;
            case "peripheral":
                device.getType().setPeripheral(Boolean.parseBoolean(value));
                break;
            case "consumption":
                device.getType().setConsumption(Double.parseDouble(value));
                break;
            case "cooler":
                device.getType().setCooler(Boolean.parseBoolean(value));
                break;
            case "group":
                device.getType().setGroup(Group.valueOf(value));
                break;
            case "port":
                device.getType().setPort(Port.valueOf(value));
                break;
            default:
                break;
        }
    }

    public void saveElement(String element){
        if(element.equalsIgnoreCase("device")) {
            devices.add(device);
            device = new Device();
        }
    }
}