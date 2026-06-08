package com.daniel.practicebackend.entity.dto;

public record AttendeeRequest(
        Long eventId,
        Long studentId
) {}