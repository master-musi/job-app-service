package net.example.mappers;

import net.example.entities.JobApplication;
import net.example.enums.ApplicationStage;
import net.example.enums.ApplicationStatus;
import net.example.models.JobApplicationPatchRequest;
import net.example.models.JobApplicationRequest;
import org.springframework.stereotype.Component;

@Component
public class JobApplicationMapper {

    public JobApplication mapToEntity(JobApplicationRequest request) {
        JobApplication entity = new JobApplication();
        entity.setCompany(request.company());
        entity.setPosition(request.position());
        entity.setLocation(request.location());
        entity.setSource(request.source());
        entity.setAppliedOn(request.appliedOn());
        entity.setStatus(request.status() != null ? request.status() : ApplicationStatus.APPLIED);
        entity.setStage(request.stage() != null ? request.stage() : ApplicationStage.CV_SENT);
        entity.setRecruiterName(request.recruiterName());
        entity.setRecruiterEmail(request.recruiterEmail());
        entity.setSalaryRange(request.salaryRange());
        entity.setNextActionOn(request.nextActionOn());
        entity.setNotes(request.notes());
        return entity;
    }

    public void mapToEntity(JobApplication entity, JobApplicationRequest request) {

        entity.setCompany(request.company());
        entity.setPosition(request.position());
        entity.setLocation(request.location());
        entity.setSource(request.source());
        entity.setAppliedOn(request.appliedOn());
        entity.setStatus(request.status() != null ? request.status() : ApplicationStatus.APPLIED);
        entity.setStage(request.stage() != null ? request.stage() : ApplicationStage.CV_SENT);
        entity.setRecruiterName(request.recruiterName());
        entity.setRecruiterEmail(request.recruiterEmail());
        entity.setSalaryRange(request.salaryRange());
        entity.setNextActionOn(request.nextActionOn());
        entity.setNotes(request.notes());

    }

    public void mapToEntity(JobApplication entity, JobApplicationPatchRequest request) {

        if (request.company() != null) {
            entity.setCompany(request.company());
        }
        if (request.position() != null) {
            entity.setPosition(request.position());
        }
        if (request.location() != null) {
            entity.setLocation(request.location());
        }
        if (request.source() != null) {
            entity.setSource(request.source());
        }
        if (request.appliedOn() != null) {
            entity.setAppliedOn(request.appliedOn());
        }
        if (request.status() != null) {
            entity.setStatus(request.status());
        }
        if (request.stage() != null) {
            entity.setStage(request.stage());
        }
        if (request.recruiterName() != null) {
            entity.setRecruiterName(request.recruiterName());
        }
        if (request.recruiterEmail() != null) {
            entity.setRecruiterEmail(request.recruiterEmail());
        }
        if (request.salaryRange() != null) {
            entity.setSalaryRange(request.salaryRange());
        }
        if (request.nextActionOn() != null) {
            entity.setNextActionOn(request.nextActionOn());
        }
        if (request.notes() != null) {
            entity.setNotes(request.notes());
        }

    }
}
