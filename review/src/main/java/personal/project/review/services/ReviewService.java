package personal.project.review.services;

import org.springframework.http.ResponseEntity;
import personal.project.review.exceptions.ResourceUnavailableException;
import personal.project.review.models.Review;
import personal.project.review.models.dtos.ReviewDTO;

import java.util.List;

public interface ReviewService {

    ResponseEntity<List<ReviewDTO>> getAllReviews(Long companyId) throws ResourceUnavailableException;

    ResponseEntity<ReviewDTO> getSingleReview(Long reviewId) throws ResourceUnavailableException;

    ResponseEntity<ReviewDTO> createReview(Review review) throws ResourceUnavailableException;

    ResponseEntity<ReviewDTO> replaceReview(Long reviewId, Review review) throws ResourceUnavailableException;

    ResponseEntity<String> deleteReview(Long reviewId) throws ResourceUnavailableException;

    ResponseEntity<List<Review>> getAllReviewsOfAllCompanies() throws ResourceUnavailableException;
}
