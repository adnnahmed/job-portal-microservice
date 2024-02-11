package personal.project.job.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import personal.project.job.models.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

}