package personal.project.job.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import personal.project.job.clients.CompanyClient;
import personal.project.job.clients.ReviewClient;
import personal.project.job.exceptions.ResourceUnavailableException;
import personal.project.job.mapper.CompanyToJobMapper;
import personal.project.job.models.Job;
import personal.project.job.models.dtos.JobDTO;
import personal.project.job.models.external.Company;
import personal.project.job.models.external.Review;
import personal.project.job.repositories.JobRepository;
import personal.project.job.services.JobService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImplementation implements JobService {

    private final JobRepository jobRepository;
    private final CompanyClient companyClient;
    private final ReviewClient reviewClient;

    @Autowired
    public JobServiceImplementation(JobRepository jobRepository, CompanyClient companyClient, ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
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
        JobDTO jobDTO = getJobDTO(jobRepository.save(job));
        return new ResponseEntity<>(jobDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<JobDTO> replaceJobRequest(Long jobId, Job job) throws ResourceUnavailableException {
        if (jobRepository.existsById(jobId)) {
            job.setId(jobId);
            JobDTO jobDTO = getJobDTO(jobRepository.save(job));
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
        try {
            return companyClient.getCompany(companyId);
        } catch (RestClientException e) {
            throw new ResourceUnavailableException("There seems to some problem with the Company service.");
        }
    }

    private List<Review> getReviews(Long companyId) throws ResourceUnavailableException {
        try {
            return reviewClient.getReviews(companyId);
        } catch (RestClientException e) {
            throw new ResourceUnavailableException("There seems to some problem with the Review service.");
        }
    }

    private JobDTO getJobDTO(Job job) throws ResourceUnavailableException {
        return CompanyToJobMapper.mapToJobDTO(job, getCompany(job.getCompanyId()), getReviews(job.getCompanyId()));
    }
}
