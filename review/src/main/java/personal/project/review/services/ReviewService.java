package personal.project.review.services;

import org.springframework.http.ResponseEntity;
import personal.project.review.exceptions.ResourceUnavailableException;
import personal.project.review.models.Review;

import java.util.List;

public interface ReviewService {

    ResponseEntity<List<Review>> getAllReviews(Long companyId) throws ResourceUnavailableException;
    ResponseEntity<Review> getSingleReview(Long reviewId) throws ResourceUnavailableException;
    ResponseEntity<Review> createReview(Review review) throws ResourceUnavailableException;
    ResponseEntity<Review> replaceReview(Long reviewId, Review review) throws ResourceUnavailableException;
    ResponseEntity<String> deleteReview(Long reviewId) throws ResourceUnavailableException;
    ResponseEntity<List<Review>> getAllReviewsOfAllCompanies() throws ResourceUnavailableException;
}
