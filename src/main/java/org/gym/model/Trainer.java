package org.gym.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Trainer extends User {
    @Getter
    @Setter
    private String specialization;
    public Trainer(Long id, String firstName, String lastName, String userName, String password, Boolean isActive, String specialization) {
        super(id, firstName, lastName, userName, password, isActive);
        this.specialization = specialization;
    }


    @Override
    public String toString() {
        return "Trainer{" +
                "id=" + getId() +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", userName='" + getUserName() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", isActive=" + getIsActive() +
                ", specialization='" + specialization + '\'' +
                '}';
    }

}