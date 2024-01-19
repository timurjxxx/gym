package org.gym.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trainee extends User {

    private Date dateOfBirth;
    private String address;

    public Trainee(Long id, String firstName, String lastName, String userName, String password, Boolean isActive, Date dateOfBirth, String address) {
        super(id, firstName, lastName, userName, password, isActive);
        this.dateOfBirth = dateOfBirth;
        this.address=address;
    }

    @Override
    public String toString() {

        return super.toString() +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'';
    }
}

