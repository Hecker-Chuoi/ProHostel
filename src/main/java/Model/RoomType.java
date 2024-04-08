package Model;

import javafx.beans.property.Property;

public class RoomType {
    private String name;
    private String id;
    private String description;
    private double pricePerHour;
    private double pricePerDay;
    private int numberOfBed;
    private int maxPeople;

    public RoomType(String name, String id, String description, double pricePerHour, double pricePerDay, int numberOfBed, int maxPeople) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.pricePerHour = pricePerHour;
        this.pricePerDay = pricePerDay;
        this.numberOfBed = numberOfBed;
        this.maxPeople = maxPeople;
    }
    public RoomType(){}

    @Override
    public String toString() {
        return name + "\n" + id + " " + numberOfBed + " " + maxPeople + " " + pricePerHour + " " + pricePerDay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public int getNumberOfBed() {
        return numberOfBed;
    }

    public void setNumberOfBed(int numberOfBed) {
        this.numberOfBed = numberOfBed;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }

}
