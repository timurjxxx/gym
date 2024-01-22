package org.gym.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trainee extends User {


    @JsonProperty("dateOfBirth")
    private Date dateOfBirth;
    @JsonProperty("address")
    private String address;



    @Override
    public String toString() {
        return "Trainee{" + super.toString() +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                '}';
    }
}

