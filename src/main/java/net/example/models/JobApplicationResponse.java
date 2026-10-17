package net.example.models;

import net.example.entities.JobApplication;
import net.example.enums.ApplicationStage;
import net.example.enums.ApplicationStatus;

import java.time.Instant;
import java.time.LocalDate;

public record JobApplicationResponse(
        Long id,
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
        String notes,
        Instant createdAt,
        Instant updatedAt
) {
    public static JobApplicationResponse from(JobApplication entity) {

        return new JobApplicationResponse(
                entity.getId(),
                entity.getCompany(),
                entity.getPosition(),
                entity.getLocation(),
                entity.getSource(),
                entity.getAppliedOn(),
                entity.getStatus(),
                entity.getStage(),
                entity.getRecruiterName(),
                entity.getRecruiterEmail(),
                entity.getSalaryRange(),
                entity.getNextActionOn(),
                entity.getNotes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
