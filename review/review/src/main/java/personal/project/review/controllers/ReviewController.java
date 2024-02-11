package personal.project.review.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import personal.project.review.exceptions.ResourceUnavailableException;
import personal.project.review.models.Review;
import personal.project.review.services.ReviewService;

import java.util.List;

/*
    Todo - Add User Entity
    Todo - **GET /reviews/user/{userId}:** Retrieve all reviews submitted by a specific user.
 */

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/all-reviews")
    public ResponseEntity<List<Review>> getAllReviewsOfAllCompanies() throws ResourceUnavailableException {
        return reviewService.getAllReviewsOfAllCompanies();
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId) throws ResourceUnavailableException {
        return reviewService.getAllReviews(companyId);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getSingleReview(@PathVariable Long reviewId) throws ResourceUnavailableException {
        return reviewService.getSingleReview(reviewId);
    }

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) throws ResourceUnavailableException {
        return reviewService.createReview(review);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> replaceReview(@PathVariable Long reviewId,
                                                @RequestBody Review review) throws ResourceUnavailableException {
        return reviewService.replaceReview(reviewId, review);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) throws ResourceUnavailableException {
        return reviewService.deleteReview(reviewId);
    }
}
