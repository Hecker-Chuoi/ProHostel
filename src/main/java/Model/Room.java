package Model;

import javafx.util.Pair;
import java.util.Date;

class Day{
    private Date date;
    private boolean booked;
    private Pair<Boolean, Customer> hoursPerDay;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public Pair<Boolean, Customer> getHoursPerDay() {
        return hoursPerDay;
    }

    public void setHoursPerDay(Pair<Boolean, Customer> hoursPerDay) {
        this.hoursPerDay = hoursPerDay;
    }
}

public class Room extends RoomType{
    private String area;
    private Day[] bookedInfo;

    public Day[] getBookedInfo() {
        return bookedInfo;
    }

    public void setBookedInfo(Day[] bookedInfo) {
        this.bookedInfo = bookedInfo;
    }
}
