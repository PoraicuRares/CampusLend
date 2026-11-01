package com.campuslend.backend.dto.request;

import com.campuslend.backend.entity.enums.Campus;
import jakarta.validation.constraints.*;

public record RegisterRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 100 , message = "Name must be at most 100 characters")
        String name,

        @NotBlank
        @Email(message = "Email format is invalid")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8 , max = 72 , message = "Password must be between 8 and 72 characters")
        String password,

        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "^\\+[1-9][0-9]{7,14}$" , message = "Phone number must be in international format")
        String phoneNumber,

        @NotNull(message = "Campus is required")
        Campus campus
) { }
