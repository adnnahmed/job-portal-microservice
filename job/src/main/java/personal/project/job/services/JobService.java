package personal.project.job.services;

import org.springframework.http.ResponseEntity;
import personal.project.job.exceptions.ResourceUnavailableException;
import personal.project.job.models.Job;
import personal.project.job.models.dtos.JobDTO;

import java.util.List;

public interface JobService {
    ResponseEntity<List<JobDTO>> findAllJobsRequests() throws ResourceUnavailableException;

    ResponseEntity<JobDTO> findSingleJobRequest(Long jobId) throws ResourceUnavailableException;

    ResponseEntity<JobDTO> createJobRequest(Job job) throws ResourceUnavailableException;

    ResponseEntity<JobDTO> replaceJobRequest(Long jobId, Job job) throws ResourceUnavailableException;

    ResponseEntity<String> deleteJobRequest(Long jobId) throws ResourceUnavailableException;
}
