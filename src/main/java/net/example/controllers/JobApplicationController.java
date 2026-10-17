package net.example.controllers;

import lombok.RequiredArgsConstructor;
import net.example.enums.ApplicationStage;
import net.example.enums.ApplicationStatus;
import net.example.models.JobApplicationPatchRequest;
import net.example.models.JobApplicationRequest;
import net.example.models.JobApplicationResponse;
import net.example.services.JobApplicationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/applications")
public class JobApplicationController {

    private final JobApplicationService service;

    @GetMapping
    public Page<JobApplicationResponse> findAll(
            @RequestParam(required = false) String company,
            @RequestParam(required = false) ApplicationStatus status,
            @RequestParam(required = false) ApplicationStage stage,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate appliedFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate appliedTo,
            @RequestParam(required = false) String q,
            @PageableDefault(size = 20, sort = "appliedOn") Pageable pageable) {

        return service.findAll(company, status, stage, appliedFrom, appliedTo, q, pageable)
                .map(JobApplicationResponse::from);
    }

    @GetMapping("/{id}")
    public JobApplicationResponse findById(@PathVariable Long id) {
        return JobApplicationResponse.from(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<JobApplicationResponse> create(@RequestBody JobApplicationRequest request) {
        JobApplicationResponse body = JobApplicationResponse.from(service.create(request));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(body.id())
                .toUri();
        return ResponseEntity.created(location).body(body);
    }

    @PutMapping("/{id}")
    public JobApplicationResponse replace(@PathVariable Long id,
                                          @RequestBody JobApplicationRequest request) {

        return JobApplicationResponse.from(service.replace(id, request));
    }

    @PatchMapping("/{id}")
    public JobApplicationResponse patch(@PathVariable Long id,
                                        @RequestBody JobApplicationPatchRequest request) {

        return JobApplicationResponse.from(service.patch(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
