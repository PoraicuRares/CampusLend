package com.campuslend.backend.dto.request;

import com.campuslend.backend.entity.enums.LoanDuration;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateOfferRequest(
        @Size(max = 500  , message = "Message must be at most 500 characters")
        String message,

        @NotNull(message = "Loan duration is required")
        LoanDuration loanDuration
) { }
