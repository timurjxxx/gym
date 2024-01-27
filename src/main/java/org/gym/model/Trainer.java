package org.gym.model;


import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@NamedEntityGraph(
        name = "Trainer.fullGraph",
        attributeNodes = {
                @NamedAttributeNode("trainees"),
                @NamedAttributeNode("traineeTrainings"),
                @NamedAttributeNode(value = "user", subgraph = "user-subgraph")
        },
        subgraphs = @NamedSubgraph(name = "user-subgraph", attributeNodes = {})
)

public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //    @NotBlank(message = "Specialization cannot be blank")
//    @Column(nullable = false)
    private String specialization;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "trainers")
    @Fetch(FetchMode.SUBSELECT)
    private Set<Trainee> trainees = new HashSet<>();

    @OneToMany(mappedBy = "trainer")
    private List<Training> traineeTrainings;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trainer trainer = (Trainer) o;
        return Objects.equals(id, trainer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "id=" + id +
                ", specialization='" + specialization + '\'' +
                ", user=" + user +
                ", traineesCount=" + (trainees != null ? trainees.size() : 0) +
                ", traineeTrainingsCount=" + (traineeTrainings != null ? traineeTrainings.size() : 0) +
                '}';
    }
}

