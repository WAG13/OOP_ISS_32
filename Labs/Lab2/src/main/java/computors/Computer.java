package computors;

import parsers.DomParser;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class Computer {
    private List<Device> devices;
    private Logger log = Logger.getLogger(DomParser.class.getName());

    public Computer(List<Device> devices) {
        this.devices = devices;
    }

    public void sort() {
        Collections.sort(devices);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (Device device : devices) {
            string.append(device);
        }
        return string.toString();
    }

    public void writeInFile(String fileName){
        try(FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(toString());
            writer.append('\n');
            writer.flush();
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    public List<Device> getDevices() {
        return devices;
    }

}