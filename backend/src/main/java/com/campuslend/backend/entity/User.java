package com.campuslend.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Email is mandatory")
    @Column(nullable = false , unique = true)
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Phone number is mandatory")
    @Column(name ="phone_number" , nullable = false , unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String campus;

    @Column(name= "karma_score" , nullable = false)
    private Double karmaScore = 0.0;

    @Column(name ="total_lends" , nullable = false)
    private Integer totalLends = 0;
}
