package com.davidmaisuradze.gymapplication.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class User {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private boolean isActive;
}
