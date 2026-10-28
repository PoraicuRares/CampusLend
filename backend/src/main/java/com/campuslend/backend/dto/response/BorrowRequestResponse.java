package com.campuslend.backend.dto.response;

import com.campuslend.backend.entity.enums.Campus;

import java.time.LocalDateTime;
import java.util.UUID;

public record BorrowRequestResponse(
        UUID id,
        String title,
        String description,
        Campus campus,
        boolean active,
        LocalDateTime createdAt,
        UserSummary requester
) { }
