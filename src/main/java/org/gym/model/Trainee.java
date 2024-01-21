package org.gym.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Trainee implements Identifiable {
    @JsonProperty("id")
    private Long id;


    @JsonProperty("dateOfBirth")
    private Date dateOfBirth;
    @JsonProperty("address")
    private String address;

    private User user;



}

