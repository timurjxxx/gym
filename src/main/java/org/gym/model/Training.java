package org.gym.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "training_id")
    @Fetch(FetchMode.SELECT)
    private List<TrainingType> trainingTypes;

    @ManyToOne
    @JoinColumn(name = "trainee_id")
    private Trainee trainee;
    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @Override
    public String toString() {
        return "Training{" +
                "id=" + id +
                ", trainingName='" + trainingName + '\'' +
                ", trainingDate=" + trainingDate +
                ", trainingDuration=" + trainingDuration +
                ", trainingTypes=" + trainingTypes +
                ", trainee=" + (trainee != null ? trainee.getId() : null) +
                ", trainer=" + (trainer != null ? trainer.getId() : null) +
                '}';
    }
}
