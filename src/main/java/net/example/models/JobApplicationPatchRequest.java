package net.example.models;

import net.example.enums.ApplicationStage;
import net.example.enums.ApplicationStatus;

import java.time.LocalDate;

public record JobApplicationPatchRequest(
        String company,
        String position,
        String location,
        String source,
        LocalDate appliedOn,
        ApplicationStatus status,
        ApplicationStage stage,
        String recruiterName,
        String recruiterEmail,
        String salaryRange,
        LocalDate nextActionOn,
        String notes
) {}
