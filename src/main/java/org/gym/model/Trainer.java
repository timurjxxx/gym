package org.gym.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.stereotype.Component;


@Component
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Trainer extends User {

    @JsonProperty("specialization")
    private String specialization;


    @Override
    public String toString() {
        return "Trainer{" + super.toString() +
                ", specialization='" + specialization + '\'' +
                '}';
    }
}