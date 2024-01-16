package org.gym.model;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trainee extends User {

    private Date dateOfBirth;
    private String address;

    @Override
    public String toString() {
        return super.toString() +
                "dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'';
    }
}