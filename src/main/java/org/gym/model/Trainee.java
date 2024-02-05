package org.gym.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@NamedEntityGraph(
        name = "Trainee.fullGraph",
        attributeNodes = {
                @NamedAttributeNode("trainers"),
                @NamedAttributeNode("traineeTrainings"),
                @NamedAttributeNode(value = "user", subgraph = "user-subgraph")
        },
        subgraphs = @NamedSubgraph(name = "user-subgraph", attributeNodes = {})

)
public class Trainee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Date of birth cannot be null")
    @Past(message = "Date of birth must be in the past")
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @NotBlank(message = "Address cannot be blank")
    @Column(nullable = false)
    private String address;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private User user;
    @JsonIgnore

    @ManyToMany()
    @JoinTable(
            name = "trainee_trainer",
            joinColumns = @JoinColumn(name = "trainee_id"),
            inverseJoinColumns = @JoinColumn(name = "trainer_id")
    )
    private Set<Trainer> trainers = new HashSet<>();
    @JsonIgnore

    @OneToMany(mappedBy = "trainee", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Training> traineeTrainings;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trainee trainee = (Trainee) o;
        return Objects.equals(id, trainee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Trainee{" + user +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", traineeTrainingsCount=" + (traineeTrainings != null ? traineeTrainings.size() : 0) +
                ", trainersCount=" + (trainers != null ? trainers : 0) +
                '}';
    }


}
