package computors;

public class Device implements Comparable {
    private int id;
    private String name;
    private String origin;
    private double price;
    private Type type;
    private boolean critical;

    public Device() {
        this.type = new Type();
    }

    @Override
    public String toString() {
        return "\nDevice{ "
                + "\n\tID: " + this.id
                + "\n\tName: " + this.name
                + "\n\tOrigin: " + this.origin
                + "\n\tPrice: " + this.price
                + "\n\tType: " + this.type
                + "\n\tCritical: " + ((this.critical) ? "Yes" : "No")
                + "\n}";

    }

    @Override
    public int compareTo(Object o) {
        return this.name.compareTo(((Device) o).getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Device)) return false;
        Device p = (Device) o;
        return this.name.equals(p.getName())
                && this.id==(p.getID())
                && this.type.equals(p.getType())
                && this.critical == p.isCritical()
                && this.origin.equals(p.getOrigin())
                && this.price == p.getPrice();
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin() {
        return this.origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isCritical() {
        return critical;
    }

    public void setCritical(boolean critical) {
        this.critical = critical;
    }
}
