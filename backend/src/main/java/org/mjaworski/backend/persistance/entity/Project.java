package org.mjaworski.backend.persistance.entity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Builder
@Data
@ToString(exclude = {"owner", "tasks"})
@EqualsAndHashCode(exclude = {"owner", "tasks"})
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "projects")
public class Project {
    public static final int MIN_NAME_LENGTH = 1;
    public static final int MAX_NAME_LENGTH = 50;
    public static final int MAX_DESCRIPTION_LENGTH = 300;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = MIN_NAME_LENGTH, max = MAX_NAME_LENGTH)
    private String name;

    @Size(max = MAX_DESCRIPTION_LENGTH)
    private String description;

    @ManyToOne
    private User owner;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            mappedBy = "project",
            orphanRemoval = true)
    @OrderBy("id DESC")
    private Set<Task> tasks;
}
