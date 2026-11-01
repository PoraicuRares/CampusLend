package com.campuslend.backend.dto.response;

import com.campuslend.backend.entity.enums.Campus;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String email,
        Campus campus,
        Double karmaScore,
        Integer totalLends,
        Double averageRating,
        Integer ratingCount,
        LocalDateTime createdAt
) { }
