package personal.project.job.models.external;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Company {

    private Long id;
    private String name;
    private String description;
    private List<Review> reviews;
}
