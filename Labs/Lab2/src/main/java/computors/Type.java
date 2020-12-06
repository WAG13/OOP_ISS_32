package computors;

public class Type {
    private boolean peripheral;
    private double consumption;
    private boolean cooler;
    private Group group;
    private Port port;

    public Type() {
        this.peripheral = false;
        this.consumption = 0;
        this.cooler = false;
        this.group = Group.None;
        this.port = Port.None;
    }

    public Type(boolean peripheral, double consumption, boolean cooler, Group group, Port port) {
        this.peripheral = peripheral;
        this.consumption = consumption;
        this.cooler = cooler;
        this.group = group;
        this.port = port;
    }

    @Override
    public String toString() {
        String result = "{is peripheral : " + (peripheral ? "yes" : "no")
                + ", consumption: " + consumption
                + ", cooler: " + (cooler ? "yes" : "no");

        switch (group) {
            case None: break;
            case IODevices:
                result += ", type: IO device";
                break;
            case Multimedia:
                result += ", type: multimedia";
                break;
            case Other:
                result += ", type: other";
                break;
        }

        switch (port) {
            case None: break;
            case USB:
                result += ", port: USB";
                break;
            case COM:
                result += ", port: COM";
                break;
            case LPT:
                result += ", port: LPT";
                break;
            case Other:
                result += ", port: Other";
                break;
        }

        return result + '}';
    }

    @Override
    public boolean equals(Object other) {

        if (this == other) return true;

        if (!(other instanceof Type)) {
            return false;
        }

        Type type = (Type) other;
        return this.peripheral == type.isPeripheral()
                && this.consumption == type.getConsumption()
                && this.cooler == type.hasCooler()
                && this.port == type.getPort()
                && this.group == type.getGroup();
    }

    public boolean isPeripheral() {
        return peripheral;
    }

    public void setPeripheral(boolean peripheral) {
        this.peripheral = peripheral;
    }

    public double getConsumption() {
        return this.consumption;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }

    public boolean hasCooler() {
        return cooler;
    }

    public void setCooler(boolean cooler) {
        this.cooler = cooler;
    }

    public Group getGroup() {
        return this.group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Port getPort() {
        return this.port;
    }

    public void setPort(Port port) {
        this.port = port;
    }


}
