package net.example.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.example.enums.ApplicationStage;
import net.example.enums.ApplicationStatus;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "job_applications")
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private String position;

    private String location;

    private String source;

    @Column(nullable = false)
    private LocalDate appliedOn;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private ApplicationStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private ApplicationStage stage;

    private String recruiterName;

    private String recruiterEmail;

    private String salaryRange;

    private LocalDate nextActionOn;

    @Column(length = 2000)
    private String notes;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
        if (status == null) {
            status = ApplicationStatus.APPLIED;
        }
        if (stage == null) {
            stage = ApplicationStage.CV_SENT;
        }
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }

}
