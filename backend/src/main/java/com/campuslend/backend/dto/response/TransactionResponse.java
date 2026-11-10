package com.campuslend.backend.dto.response;

import com.campuslend.backend.entity.BorrowRequest;
import com.campuslend.backend.entity.enums.LoanDuration;
import com.campuslend.backend.entity.enums.TransactionStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponse(
        UUID id,
        TransactionStatus status,
        String message,
        LoanDuration loanDuration,
        LocalDateTime createdAt,
        LocalDateTime acceptedAt,
        LocalDateTime returnDeadline,
        LocalDateTime completedAt,
        Integer rating,
        UserSummary lender,
        BorrowRequestSummary borrowRequest,
        String contactPhone
) { }
