package personal.project.review.models.dtos;

import lombok.Getter;
import lombok.Setter;
import personal.project.review.models.Review;
import personal.project.review.models.external.Company;

@Getter
@Setter
public class ReviewDTO {

    private Company company;
    private Review review;
}
