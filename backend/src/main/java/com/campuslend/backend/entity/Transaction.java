package com.campuslend.backend.entity;

import com.campuslend.backend.entity.enums.LoanDuration;
import com.campuslend.backend.entity.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "borrow_request_id" , nullable = false)
    private BorrowRequest borrowRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lender_id" , nullable = false)
    private User lender;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status = TransactionStatus.PENDING;

    @Column(length = 500)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name  = "loan_duration" , nullable = false)
    private LoanDuration loanDuration;

    @CreationTimestamp
    @Column(name = "created_at" , nullable = false , updatable = false)
    private LocalDateTime createdAt ;

    @Column(name = "accepted_at" , nullable = false)
    private LocalDateTime acceptedAt;

    @Column(name = "return_deadline")
    private LocalDateTime returnDeadline;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "rating")
    private Integer rating;

    @Version
    private Long version;
}
