package Model;

import controller.Main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Random;

public class Receipt {
    private String receiptId;
    private Customer customer;
    private Room room;
    private LocalDateTime receiptDate;
    private LocalDateTime fromTime, toTime;
    private int day, hour;
    private double total;

    private String customerName;
    private Integer numOfPeople;
    private String roomId;
    private String fromTimeString;
    private String toTimeString;

    public Receipt(){}

    private String receiptIdFormat(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        Random rand = new Random();
        return "HD" + formatter.format(receiptDate) + String.format("%03d", rand.nextInt(1, 1000));
    }

    public Receipt(Room room){
        this.room = room;
        this.customer = room.getCustomer();
        this.receiptDate = LocalDateTime.now();
        this.numOfPeople = Integer.valueOf(room.getNumberOfPeople());
        this.fromTime = room.getStartTime();
        this.toTime = room.getEndTime();
        this.receiptId = receiptIdFormat();
        init();
    }

    public Receipt(String s){
        String[] arr = s.split(" ");
        this.receiptId = arr[0];
        for(Customer customer : Main.customerList){
            if(customer.getCitizenId().equals(arr[1])){
                this.customer = customer;
                break;
            }
        }
        for(Room room : Main.roomList){
            if(room.getId().equals(arr[2])){
                this.room = room;
                break;
            }
        }
        this.numOfPeople = Integer.parseInt(arr[3]);
        this.fromTime = LocalDateTime.parse(arr[4]);
        this.toTime = LocalDateTime.parse(arr[5]);
        this.receiptDate = LocalDateTime.parse(arr[6]);
        this.total = Double.parseDouble(arr[7]);
        init();
    }
    private void init(){
        if(customer != null){
            this.customerName = customer.getFullName();
        }
        if(room != null) {
            this.roomId = room.getId();
        }

        if(fromTime == null || toTime == null){
            this.day = 0;
            this.hour = 0;
        }
        else{
            this.day = (int) fromTime.until(toTime, ChronoUnit.DAYS);
            this.hour = (int) fromTime.until(toTime, ChronoUnit.HOURS) % 24;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
            this.fromTimeString = formatter.format(fromTime);
            this.toTimeString = formatter.format(toTime);
        }
        this.total = day * room.getType().getPricePerDay() + hour * room.getType().getPricePerHour();
    }

    @Override
    public String toString() {
        return receiptId + " " + customer.getCitizenId() + " " + room.getId() + " " + numOfPeople + " " + fromTime + " " + toTime + " " + receiptDate + " " + total;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getFromTimeString() {
        return fromTimeString;
    }

    public String getToTimeString() {
        return toTimeString;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDateTime getReceiptDate() {
        return receiptDate;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public double getTotal() {
        return total;
    }

    public Integer getNumOfPeople() {
        return numOfPeople;
    }
}
