package personal.project.job.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import personal.project.job.exceptions.ResourceUnavailableException;
import personal.project.job.models.Job;
import personal.project.job.models.dtos.JobDTO;
import personal.project.job.models.external.Company;
import personal.project.job.repositories.JobRepository;
import personal.project.job.services.JobService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImplementation implements JobService {

    private final JobRepository jobRepository;

    @Autowired
    public JobServiceImplementation(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public ResponseEntity<List<JobDTO>> findAllJobsRequests() throws ResourceUnavailableException {
        List<Job> jobList = jobRepository.findAll();
        if (jobList.isEmpty()) {
            throw new ResourceUnavailableException("No data is available.");
        }
        List<JobDTO> jobDTOList = new ArrayList<>();
        for (Job job : jobList)
            jobDTOList.add(getJobDTO(job));
        return ResponseEntity.ok(jobDTOList);
    }

    @Override
    public ResponseEntity<JobDTO> findSingleJobRequest(Long jobId) throws ResourceUnavailableException {
        Optional<Job> jobOptional = jobRepository.findById(jobId);
        if (jobOptional.isPresent())
            return ResponseEntity.ok(getJobDTO(jobOptional.get()));
        throw new ResourceUnavailableException("Job request with ID " + jobId + " is unavailable.");
    }

    @Override
    public ResponseEntity<JobDTO> createJobRequest(Job job) throws ResourceUnavailableException {
        JobDTO jobDTO = getJobDTO(job);
        jobRepository.save(job);
        return new ResponseEntity<>(jobDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<JobDTO> replaceJobRequest(Long jobId, Job job) throws ResourceUnavailableException {
        if (jobRepository.existsById(jobId)) {
            JobDTO jobDTO = getJobDTO(job);
            jobRepository.save(job);
            return ResponseEntity.ok(jobDTO);
        }
        throw new ResourceUnavailableException("Job request with ID " + jobId + " is unavailable.");
    }

    @Override
    public ResponseEntity<String> deleteJobRequest(Long jobId) throws ResourceUnavailableException {
        if (jobRepository.existsById(jobId)) {
            jobRepository.deleteById(jobId);
            return ResponseEntity.ok("Job request with ID " + jobId + " has been deleted.");
        }
        throw new ResourceUnavailableException("Job request with ID " + jobId + " is unavailable.");
    }

    private Company getCompany(Long companyId) throws ResourceUnavailableException {
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.getForObject("localhost:8081/companies/" + companyId, Company.class);
        } catch (RestClientException e) {
            throw new ResourceUnavailableException("There seems to some problem with the Company service.");
        }
    }

    private JobDTO getJobDTO(Job job) throws ResourceUnavailableException {
        JobDTO jobDTO = new JobDTO();
        jobDTO.setJob(job);
        jobDTO.setCompany(getCompany(job.getCompanyId()));
        return jobDTO;
    }
}
