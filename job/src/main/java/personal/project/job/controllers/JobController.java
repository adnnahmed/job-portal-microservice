package personal.project.job.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import personal.project.job.exceptions.ResourceUnavailableException;
import personal.project.job.models.Job;
import personal.project.job.models.dtos.JobDTO;
import personal.project.job.services.JobService;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<JobDTO>> findAllJobsRequests() throws ResourceUnavailableException {
        return jobService.findAllJobsRequests();
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<JobDTO> findSingleJobRequest(@PathVariable Long jobId) throws ResourceUnavailableException {
        return jobService.findSingleJobRequest(jobId);
    }

    @PostMapping
    public ResponseEntity<JobDTO> createJobRequest(@RequestBody Job job) throws ResourceUnavailableException {
        return jobService.createJobRequest(job);
    }

    @PutMapping("/{jobId}")
    public ResponseEntity<JobDTO> replaceJobRequest(@PathVariable Long jobId,
                                                    @RequestBody Job job) throws ResourceUnavailableException {
        return jobService.replaceJobRequest(jobId, job);
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<String> deleteJobRequest(@PathVariable Long jobId) throws ResourceUnavailableException {
        return jobService.deleteJobRequest(jobId);
    }
}
