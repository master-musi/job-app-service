package net.example.services;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import net.example.entities.JobApplication;
import net.example.enums.ApplicationStage;
import net.example.enums.ApplicationStatus;
import net.example.mappers.JobApplicationMapper;
import net.example.models.JobApplicationPatchRequest;
import net.example.models.JobApplicationRequest;
import net.example.repositories.JobApplicationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobApplicationService {

    private final JobApplicationRepository repository;
    private final JobApplicationMapper mapper;

    public Page<JobApplication> findAll(String company,
                                        ApplicationStatus status,
                                        ApplicationStage stage,
                                        LocalDate appliedFrom,
                                        LocalDate appliedTo,
                                        String q,
                                        Pageable pageable) {

        Specification<JobApplication> spec = buildSpecification(company, status, stage, appliedFrom, appliedTo, q);
        return repository.findAll(spec, pageable);
    }

    public JobApplication findById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found: " + id));
    }

    @Transactional
    public JobApplication create(JobApplicationRequest request) {
        JobApplication entity = mapper.mapToEntity(request);
        return repository.save(entity);
    }

    @Transactional
    public JobApplication replace(Long id, JobApplicationRequest request) {
        JobApplication entity = findById(id);
        mapper.mapToEntity(entity, request);
        return repository.save(entity);
    }

    @Transactional
    public JobApplication patch(Long id, JobApplicationPatchRequest request) {
        JobApplication entity = findById(id);
        mapper.mapToEntity(entity, request);
        return repository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found: " + id);
        }
        repository.deleteById(id);
    }

    private Specification<JobApplication> buildSpecification(String company,
                                                             ApplicationStatus status,
                                                             ApplicationStage stage,
                                                             LocalDate appliedFrom,
                                                             LocalDate appliedTo,
                                                             String q) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(company)) {
                predicates.add(cb.like(cb.lower(root.get("company")), "%" + company.toLowerCase() + "%"));
            }
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            if (stage != null) {
                predicates.add(cb.equal(root.get("stage"), stage));
            }
            if (appliedFrom != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("appliedOn"), appliedFrom));
            }
            if (appliedTo != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("appliedOn"), appliedTo));
            }
            if (StringUtils.hasText(q)) {
                String pattern = "%" + q.toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("company")), pattern),
                        cb.like(cb.lower(root.get("position")), pattern),
                        cb.like(cb.lower(root.get("location")), pattern)
                ));
            }

            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}