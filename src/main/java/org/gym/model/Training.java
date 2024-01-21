package org.gym.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Training  implements Identifiable {

    @JsonProperty("id")
    private Long id;

    private Trainee traineeId;
    private Trainer trainerId;
    @JsonProperty("trainingName")
    private String trainingName;
    @JsonProperty("trainingDate")
    private Date trainingDate;
    @JsonProperty("trainingDuration")
    private Number trainingDuration;

    private List<TrainingType> trainingType;

    @Override
    public String toString() {
        return "Training{" +
                "id=" + id +
                ", traineeId=" + traineeId +
                ", trainerId=" + trainerId +
                ", trainingName='" + trainingName + '\'' +
                ", trainingDate=" + trainingDate +
                ", trainingDuration=" + trainingDuration +
                '}';
    }
}