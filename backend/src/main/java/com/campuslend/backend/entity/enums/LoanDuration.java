package com.campuslend.backend.entity.enums;

import java.time.Duration;

public enum LoanDuration {
    FEW_HOURS(Duration.ofHours(4)),
    ONE_DAY(Duration.ofDays(1)),
    THREE_DAYS(Duration.ofDays(3)),
    ONE_WEEK(Duration.ofDays(7));

    private final Duration duration;

    LoanDuration(Duration duration) {
        this.duration = duration;
    }

    public Duration getDuration() {
        return duration;
    }
}
