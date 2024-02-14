package personal.project.job.models.dtos;

import lombok.Getter;
import lombok.Setter;
import personal.project.job.models.external.Company;

@Getter
@Setter
public class JobDTO {

    private Long id;
    private String title;
    private String location;
    private String description;
    private String minSalary;
    private String maxSalary;
    private Company company;
}
