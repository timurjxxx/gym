package org.gym.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Training name cannot be blank")
    @Column(nullable = false)
    private String trainingName;

    @NotNull(message = "Training date cannot be null")
    @Future(message = "Training date must be in the future")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date trainingDate;

    @NotNull(message = "Training duration cannot be null")
    @Positive(message = "Training duration must be a positive number")
    @Column(nullable = false)
    private Number trainingDuration;

    @OneToMany(mappedBy = "training", cascade = CascadeType.REMOVE)
    private List<TrainingType> trainingTypes;

    @ManyToOne
    @JoinColumn(name = "trainee_id")
    private Trainee trainee;
    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;


}
