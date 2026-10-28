package com.campuslend.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateBorrowRequestRecord(
        @NotBlank(message = "Title is required")
        @Size(min = 3, max = 100 , message = "Title must be between 3 and 100 characters")
        String title,

        @Size(max = 1000 , message = "Description must be at most 1000 characters")
        String description
) { }
