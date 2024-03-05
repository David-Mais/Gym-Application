package com.davidmaisuradze.gymapplication.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
public class Trainer extends User{
    private String specialization;
    private Long userId;
}
