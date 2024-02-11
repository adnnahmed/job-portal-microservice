package personal.project.job.services;

import org.springframework.http.ResponseEntity;
import personal.project.job.models.Job;
import personal.project.job.exceptions.ResourceUnavailableException;

import java.util.List;

public interface JobService {
    ResponseEntity<List<Job>> findAllJobsRequests() throws ResourceUnavailableException;
    ResponseEntity<Job> findSingleJobRequest(Long jobId) throws ResourceUnavailableException;
    ResponseEntity<Job> createJobRequest(Job job) throws ResourceUnavailableException;
    ResponseEntity<Job> replaceJobRequest(Long jobId, Job job) throws ResourceUnavailableException;
    ResponseEntity<String> deleteJobRequest(Long jobId) throws ResourceUnavailableException;
}
