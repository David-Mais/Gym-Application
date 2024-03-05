package com.davidmaisuradze.gymapplication.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
public class Trainee extends User {
    private LocalDate dateOfBirth;
    private String address;
    private Long userId;
}
