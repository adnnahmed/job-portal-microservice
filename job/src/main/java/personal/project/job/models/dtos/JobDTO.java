package personal.project.job.models.dtos;

import lombok.Getter;
import lombok.Setter;
import personal.project.job.models.Job;
import personal.project.job.models.external.Company;

@Getter
@Setter
public class JobDTO {

    private Job job;
    private Company company;
}
