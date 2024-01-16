package org.gym.model;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Trainer extends User {
    private String specialization;

    @Override
    public String toString() {
        return super.toString() +
                "specialization='" + specialization + '\'';
    }


}