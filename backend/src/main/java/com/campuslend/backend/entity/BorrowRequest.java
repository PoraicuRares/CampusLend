package com.campuslend.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "borrow_requests",
        indexes = {
                @Index(name = "idx_request_campus_active" , columnList = "campus , active"),
                @Index(name = "idx_request_requester" , columnList = "requester_id")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Campus campus;

    @Builder.Default
    @Column( nullable = false)
    private boolean active = true;

    @CreationTimestamp
    @Column(name = "created_at" , nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id" , nullable = false)
    private User requester;

    @Version
    private Long version;

}
