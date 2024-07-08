package model;

import enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "users", schema = "main_schema")
public class User implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "role_id", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Role role;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "middle_name", length = 50)
    private String middleName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "user_name", nullable = false, length = 50)
    private String userName;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "password_hash", nullable = false, length = 32)
    private String passwordHash;


    @Column(name = "registred_at", insertable = false, updatable = false)
    private LocalDateTime registredAt;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "intro", length = Integer.MAX_VALUE)
    private String intro;

    @Column(name = "profile", length = Integer.MAX_VALUE)
    private String profile;
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "user")
    private Set<Comment> comments = new LinkedHashSet<>();
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "user")
    private Set<Task> userTasks = new LinkedHashSet<>();
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "createdBy")
    private Set<Task> createdTask = new LinkedHashSet<>();

}