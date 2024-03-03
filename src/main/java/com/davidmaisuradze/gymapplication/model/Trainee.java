package com.davidmaisuradze.gymapplication.model;

import java.time.LocalDate;

public class Trainee extends User {
    private LocalDate dateOfBirth;
    private String address;
    private long userId;

    public Trainee(String firstName, String lastName, String userName, String password, boolean isActive, LocalDate dateOfBirth, String address, long userId) {
        super(firstName, lastName, userName, password, isActive);
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.userId = userId;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return super.toString() + "Trainee{" +
                "dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", userId=" + userId +
                '}';
    }
}
