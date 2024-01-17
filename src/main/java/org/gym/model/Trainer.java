package org.gym.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "trainer_entity")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Trainer extends User {

    private String specialization;


    @ManyToMany(mappedBy = "trainers")
    private Set<Trainee> trainees = new HashSet<>();

    @OneToMany(mappedBy = "trainer")
    private List<Training> traineeTrainings;

    @Override
    public String toString() {
        return super.toString() + "Trainer{" +
                "specialization='" + specialization + '\'' +
                '}';
    }
}

