package com.campuslend.backend.dto.response;

import java.util.UUID;

public record UserSummary(
        UUID id,
        String name,
        Double karmaScore,
        Integer totalLends,
        Double averageRating,
        Integer ratingCount
) {
}
