package org.gym.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class TrainingSearchCriteria {

    private String trainingName;
    private Date trainingStartDate;
    private Date trainingEndDate;
    private Integer trainingDuration;

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
