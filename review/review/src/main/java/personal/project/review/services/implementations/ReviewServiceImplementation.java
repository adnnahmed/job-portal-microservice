package personal.project.review.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import personal.project.review.exceptions.ResourceUnavailableException;
import personal.project.review.models.Review;
import personal.project.review.repositories.ReviewRepository;
import personal.project.review.services.ReviewService;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImplementation implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImplementation(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public ResponseEntity<List<Review>> getAllReviews(Long companyId) throws ResourceUnavailableException {
        if (companyService.existsByCompanyId(companyId)) {
            List<Review> reviewsList = reviewRepository.findByCompanyId(companyId);
            if (reviewsList.isEmpty()) {
                throw new ResourceUnavailableException("No data is available.");
            }
            return ResponseEntity.ok(reviewsList);
        }
        throw new ResourceUnavailableException("Company with ID " + companyId + " is unavailable.");
    }

    @Override
    public ResponseEntity<Review> getSingleReview(Long reviewId) throws ResourceUnavailableException {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if (reviewOptional.isPresent())
            return ResponseEntity.ok(reviewOptional.get());
        throw new ResourceUnavailableException("Review with ID " + reviewId + " is unavailable.");
    }


    @Override
    public ResponseEntity<Review> createReview(Review review) throws ResourceUnavailableException {
        Long companyId = review.getCompanyId();
        if (companyService.existsByCompanyId(companyId))
            return new ResponseEntity<>(reviewRepository.save(review), HttpStatus.CREATED);
        throw new ResourceUnavailableException("Company with ID " + companyId + " is unavailable.");
    }

    @Override
    public ResponseEntity<Review> replaceReview(Long reviewId, Review review) throws ResourceUnavailableException {
        Long companyId = review.getCompanyId();
        if (companyService.existsByCompanyId(companyId)) {
            if (reviewRepository.existsById(reviewId))
                return ResponseEntity.ok(reviewRepository.save(review));
            throw new ResourceUnavailableException("Review with ID " + reviewId + " is unavailable.");
        }
        throw new ResourceUnavailableException("Company with ID " + companyId + " is unavailable.");
    }

    @Override
    public ResponseEntity<String> deleteReview(Long reviewId) throws ResourceUnavailableException {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if (reviewOptional.isPresent()) {
            reviewRepository.deleteById(reviewId);
            return ResponseEntity.ok("Review with ID " + reviewId + " has been deleted.");
        }
        throw new ResourceUnavailableException("Review with ID " + reviewId + " is unavailable.");
    }

    @Override
    public ResponseEntity<List<Review>> getAllReviewsOfAllCompanies() throws ResourceUnavailableException {
        List<Review> reviewsList = reviewRepository.findAll();
        if (reviewsList.isEmpty()) {
            throw new ResourceUnavailableException("No data is available.");
        }
        return ResponseEntity.ok(reviewsList);
    }
}
