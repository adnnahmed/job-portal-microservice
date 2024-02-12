package personal.project.review.mapper;

import personal.project.review.models.Review;
import personal.project.review.models.dtos.ReviewDTO;
import personal.project.review.models.external.Company;

public class CompanyToReviewMapper {

    public static ReviewDTO mapToReviewDTO(Review review, Company company) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setTitle(review.getTitle());
        reviewDTO.setDescription(review.getDescription());
        reviewDTO.setRating(review.getRating());
        reviewDTO.setCompany(company);

        return reviewDTO;
    }
}
