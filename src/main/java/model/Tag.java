package model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tags", schema = "main_schema")
public class Tag implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", length = 75)
    private String title;

    @Column(name = "slug", length = 100)
    private String slug;

    @Builder.Default
    @OneToMany(mappedBy = "tag")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Task> tasks = new HashSet<>();

    void addTask(Task task) {
        tasks.add(task);
    }
}