package com.campuslend.backend.dto.request;

import com.campuslend.backend.entity.enums.Campus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateCampusRequest(
        @NotNull(message = "Campus is required")
        Campus campus
) { }
