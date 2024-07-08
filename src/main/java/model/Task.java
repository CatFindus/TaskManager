package model;

import enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks", schema = "main_schema")
public class Task implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "updated_by", nullable = false)
    private User updatedBy;

    @Column(name = "title", nullable = false, length = 512)
    private String title;

    @Column(name = "description", length = 2048)
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private TaskStatus status;

    @Column(name = "hours", nullable = false)
    private Double hours;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    @Column(name = "planned_start")
    private LocalDateTime plannedStart;

    @Column(name = "planned_end")
    private LocalDateTime plannedEnd;

    @Column(name = "actual_start")
    private LocalDateTime actualStart;

    @Column(name = "actual_end")
    private LocalDateTime actualEnd;

    @Column(name = "content", length = Integer.MAX_VALUE)
    private String content;

    @OneToMany(mappedBy = "task")
    @Builder.Default
    private Set<Comment> comments = new LinkedHashSet<>();


    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

}
