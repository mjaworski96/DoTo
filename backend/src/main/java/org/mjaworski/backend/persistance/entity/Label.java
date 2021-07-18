package org.mjaworski.backend.persistance.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Builder
@Data
@ToString(exclude = {"project", "tasks"})
@EqualsAndHashCode(exclude = {"project", "tasks"})
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "labels")
public class Label {
    public static final int MAX_NAME_LENGTH = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = MAX_NAME_LENGTH)
    @NotNull
    private String name;

    @ManyToOne
    private Project project;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "labels")
    private Set<Task> tasks;
}
