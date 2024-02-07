package org.gym.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingSearchCriteria {

    private String trainingName;
    private LocalDate trainingStartDate;
    private LocalDate trainingEndDate;
    private Integer trainingDuration;

    private TrainingType trainingTypes;


}
