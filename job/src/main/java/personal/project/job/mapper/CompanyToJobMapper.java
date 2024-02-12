package personal.project.job.mapper;

import personal.project.job.models.Job;
import personal.project.job.models.dtos.JobDTO;
import personal.project.job.models.external.Company;
import personal.project.job.models.external.Review;

import java.util.List;

public class CompanyToJobMapper {

    public static JobDTO mapToJobDTO(Job job, Company company, List<Review> reviews) {
        JobDTO jobDTO = new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setCompany(company);
        jobDTO.getCompany().setReviews(reviews);

        return jobDTO;
    }
}
