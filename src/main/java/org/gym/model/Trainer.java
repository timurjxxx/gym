package org.gym.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.stereotype.Component;


@Component
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class Trainer implements Identifiable {

    @JsonProperty("id")
    private Long id;

    public Long getId() {
        return id;
    }



    @JsonProperty("specialization")
    private String specialization;

    private User user;

    @Override
    public String toString() {
        return "Trainer{" +
                "id=" + id +
                ", specialization='" + specialization + '\'' +
                ", user=" + user +
                '}';
    }
}