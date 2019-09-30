package org.mjaworski.backend.persistance.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Builder
@Data
@ToString(exclude = {"roles", "projects"})
@EqualsAndHashCode(exclude = {"roles", "projects"})
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "users")
public class User {
    public static final int MIN_USERNAME_LENGTH = 3;
    public static final int MAX_USERNAME_LENGTH = 20;

    public static final int MIN_PASSWORD_LENGTH = 3;
    public static final int MAX_PASSWORD_LENGTH = 20;
    public static final int MAX_PASSWORD_HASH_LENGTH = 60;

    public static final int MAX_EMAIL_LENGTH = 254;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = MIN_USERNAME_LENGTH, max = MAX_USERNAME_LENGTH)
    @Column(unique = true)
    private String username;

    @NotNull
    @Size(max = MAX_PASSWORD_HASH_LENGTH)
    private String password;

    @NotNull
    @Size(max = MAX_EMAIL_LENGTH)
    @Column(unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            mappedBy = "owner",
            orphanRemoval = true)
    @OrderBy("id")
    private Set<Project> projects;
}