package org.mjaworski.backend.persistance.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Builder
@Data
@ToString(exclude = {"tasks"})
@EqualsAndHashCode(exclude = {"tasks"})
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "states")
public class State {
    public static final int MIN_NAME_LENGTH = 1;
    public static final int MAX_NAME_LENGTH = 50;

    public static final String STATE_IN_PROGRESS = "inprogress";
    public static final String STATE_TO_DO = "todo";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = MIN_NAME_LENGTH, max = MAX_NAME_LENGTH)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "state")
    private Set<Task> tasks;

}
