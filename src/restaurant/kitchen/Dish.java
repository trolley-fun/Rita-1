package restaurant.kitchen;


import java.io.Serializable;

/**
 * Created by Eduard 16.03.2016
 */
public class Dish implements Serializable {
    private String type;
    private String name;
    private String fullDesc;
    private String shortDesc;
    private String clientImagePath;
    private String serverImagePath;
    private double price;
    private boolean deleted = false;

    //Constructors

    public Dish(String type, String name,
                String shortDesc, String fullDesc,
                String serverImagePath, double price) {
        this.type = type;
        this.name = name;
        this.shortDesc = shortDesc;
        this.fullDesc = fullDesc;
        this.serverImagePath = serverImagePath;
        this.price = price;
    }

    //Setters

    public void setServerImagePath(String serverImagePath) {
        this.serverImagePath = serverImagePath;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFullDesc(String fullDescription) {
        this.fullDesc = fullDescription;
    }

    public void setShortDesc(String shortDescription) {
        this.shortDesc = shortDescription;
    }

    public void setClientImagePath(String clientImagePath) {
        this.clientImagePath = clientImagePath;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    //Getters

    public String getServerImagePath() {
        return serverImagePath;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getFullDesc() {
        return fullDesc;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public String getClientImagePath() {
        return clientImagePath;
    }

    public double getPrice() {
        return price;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dish dish = (Dish) o;

        if (type != null ? !type.equals(dish.type) : dish.type != null) return false;
        return name != null ? name.equals(dish.name) : dish.name == null;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
