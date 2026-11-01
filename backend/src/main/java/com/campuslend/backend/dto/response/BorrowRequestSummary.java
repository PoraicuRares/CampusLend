package com.campuslend.backend.dto.response;

import java.util.UUID;

public record BorrowRequestSummary(
        UUID id,
        String title,
        UserSummary requester
) { }
