package Model;

import controller.Main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Room{
    private int floor;
    private int number;
    private RoomType type;
    private String typeString;
    private String id;
    private int numberOfBed;
    private int maxPeople;

    private Status status;
    private String statusString;;
    private Customer customer;
    private String customerString;
    private String numberOfPeople;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String startTimeString;
    private String endTimeString;

    public Room(){
        init();
    }
    public Room(String s){
        String[] arr = s.split(" ");
        this.id = arr[0];
        this.floor = Integer.parseInt(arr[1]);
        this.number = Integer.parseInt(arr[2]);
        for(RoomType roomType : Main.roomTypeList){
            if(roomType.getId().equals(arr[3])){
                this.type = roomType;
                break;
            }
        }

        init();
    }

    public void init(){
        status = Status.AVAILABLE;
        statusString = String.valueOf(status);

        numberOfPeople = "0";
        customer = null;
        customerString = "";

        startTime = null;
        endTime = null;
        startTimeString = "";
        endTimeString = "";

        if(type == null){
            typeString = "";
            numberOfBed = 0;
            maxPeople = 0;
        }
        else {
            typeString = type.getName();
            numberOfBed = type.getNumberOfBed();
            maxPeople = type.getMaxPeople();
        }
    }

    @Override
    public String toString() {
        return id + " " + floor + " " + number + " " + type.getId();
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
        this.typeString = type.getName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return String.valueOf(status);
    }

    public void setStatus(Status status) {
        this.status = status;
        this.statusString = String.valueOf(status);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        this.customerString = customer.getFullName();
    }

    public String getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(String numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public int getNumberOfBed() {
        return numberOfBed;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public String getStatusString() {
        return statusString;
    }

    public String getTypeString() {
        return typeString;
    }

    public String getCustomerString() {
        return customerString;
    }

    public String getStartTimeString() {
        return startTimeString;
    }

    public String getEndTimeString() {
        return endTimeString;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm - dd/MM/yyyy");
        this.startTimeString = formatter.format(startTime);
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm - dd/MM/yyyy");
        this.endTimeString = formatter.format(endTime);
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

}
