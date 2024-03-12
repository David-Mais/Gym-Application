package com.davidmaisuradze.gymapplication.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.sql.Date;

@Entity
@Table(name = "trainees")
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
public class Trainee extends UserEntity {
    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;

    @Column(name = "address")
    private String address;
}