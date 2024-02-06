package org.gym.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingSearchCriteria {

    private String trainingName;
    private LocalDate trainingStartDate;
    private LocalDate trainingEndDate;
    private Integer trainingDuration;

    private TrainingType trainingTypes;

    @Override
    public String toString() {
        return "TrainingSearchCriteria{" +
                "trainingName='" + trainingName + '\'' +
                ", trainingStartDate=" + trainingStartDate +
                ", trainingEndDate=" + trainingEndDate +
                ", trainingDuration=" + trainingDuration +
                '}';
    }
}
