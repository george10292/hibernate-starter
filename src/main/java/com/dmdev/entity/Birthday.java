package com.dmdev.entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record Birthday(LocalDate birth_day) {
    public long getAge() {
        return ChronoUnit.YEARS.between(birth_day, LocalDate.now());
    }
}
