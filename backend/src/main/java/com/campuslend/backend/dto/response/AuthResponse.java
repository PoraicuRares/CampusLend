package com.campuslend.backend.dto.response;

public record AuthResponse(
        String token,
        UserResponse user
) { }
