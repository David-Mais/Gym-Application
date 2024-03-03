package com.davidmaisuradze.gymapplication.model;

public class Trainer extends User{
    private String specialization;
    private long userId;

    public Trainer(String firstName, String lastName, String userName, String password, boolean isActive, String specialization, long userId) {
        super(firstName, lastName, userName, password, isActive);
        this.specialization = specialization;
        this.userId = userId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "specialization='" + specialization + '\'' +
                ", userId=" + userId +
                '}';
    }


}
