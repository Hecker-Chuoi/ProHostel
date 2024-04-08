package Model;

import java.awt.*;
import java.time.LocalDate;
import java.util.Date;

public class Customer {
    private String fullName;
    private String citizenId;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String address;
    private String nationality;
    private String gender;

    public Customer(){}

    public String getFullName() {
        return fullName;
    }

    public Customer(String s){
        String[] arr = s.split("\n");
        this.fullName = arr[0];
        this.citizenId = arr[1];
        this.dateOfBirth = LocalDate.parse(arr[2]);
        this.phoneNumber = arr[3];
        this.address = arr[4];
        this.nationality = arr[5];
        this.gender = arr[6];
    }

    @Override
    public String toString() {
        return fullName + "\n" + citizenId + "\n" + dateOfBirth + "\n" + phoneNumber + "\n" + address + "\n" + nationality + "\n" + gender;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(String citizenId) {
        this.citizenId = citizenId;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
