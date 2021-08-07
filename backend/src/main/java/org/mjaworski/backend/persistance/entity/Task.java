package org.mjaworski.backend.persistance.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

import jdk.nashorn.internal.ir.Labels;
import lombok.*;

@Builder
@Data
@ToString(exclude = {"comments", "project", "state"})
@EqualsAndHashCode(exclude = {"comments", "project", "state"})
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "tasks")
public class Task {
    public static final int MIN_SHORT_DESCRIPTION_LENGTH = 1;
    public static final int MAX_SHORT_DESCRIPTION_LENGTH = 100;
    public static final int MAX_FULL_DESCRIPTION_LENGTH = 1000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = MIN_SHORT_DESCRIPTION_LENGTH, max = MAX_SHORT_DESCRIPTION_LENGTH)
    private String shortDescription;

    @Size(max = MAX_FULL_DESCRIPTION_LENGTH)
    private String fullDescription;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            mappedBy = "task",
            orphanRemoval = true)
    @OrderBy("id")
    private Set<Comment> comments;

    @ManyToOne
    private Project project;

    @ManyToOne
    private State state;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "labels_tasks",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "label_id"))
    @OrderBy("id")
    private Set<Label> labels;

}
