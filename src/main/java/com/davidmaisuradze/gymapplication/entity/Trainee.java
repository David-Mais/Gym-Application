package com.davidmaisuradze.gymapplication.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
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
}
