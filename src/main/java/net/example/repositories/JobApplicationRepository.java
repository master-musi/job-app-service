package net.example.repositories;

import net.example.entities.JobApplication;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface JobApplicationRepository extends CrudRepository<JobApplication, Long>, JpaSpecificationExecutor<JobApplication> {
}
