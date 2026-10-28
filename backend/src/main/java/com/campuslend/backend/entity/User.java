package com.campuslend.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@DynamicUpdate
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false , unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name ="phone_number" , nullable = false , unique = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private String campus;

    @Builder.Default
    @Column(name= "karma_score" , nullable = false)
    private Double karmaScore = 0.0;

    @Builder.Default
    @Column(name ="total_lends" , nullable = false)
    private Integer totalLends = 0;

    @Builder.Default
    @Column(name = "average_rating" , nullable = false)
    private Double averageRating = 0.0;

    @Builder.Default
    @Column(name = "rating_count" ,nullable = false)
    private Integer ratingCount = 0;

    @CreationTimestamp
    @Column(name = "created_at" , nullable = false , updatable = false)
    private LocalDateTime createdAt;

}
