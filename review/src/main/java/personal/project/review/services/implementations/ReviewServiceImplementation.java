package personal.project.review.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import personal.project.review.clients.CompanyClient;
import personal.project.review.exceptions.ResourceUnavailableException;
import personal.project.review.mapper.CompanyToReviewMapper;
import personal.project.review.models.Review;
import personal.project.review.models.dtos.ReviewDTO;
import personal.project.review.repositories.ReviewRepository;
import personal.project.review.services.ReviewService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImplementation implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CompanyClient companyClient;

    @Autowired
    public ReviewServiceImplementation(ReviewRepository reviewRepository, CompanyClient companyClient) {
        this.reviewRepository = reviewRepository;
        this.companyClient = companyClient;
    }

    @Override
    public ResponseEntity<List<ReviewDTO>> getAllReviews(Long companyId) throws ResourceUnavailableException {
        if (companyClient.getCompany(companyId).getStatusCode().isError())
            throw new ResourceUnavailableException("Uh oh! Something went wrong.");
        List<Review> reviewsList = reviewRepository.findByCompanyId(companyId);
        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        for (Review review : reviewsList)
            reviewDTOList.add(getReviewDTO(review));
        return ResponseEntity.ok(reviewDTOList);
    }

    @Override
    public ResponseEntity<ReviewDTO> getSingleReview(Long reviewId) throws ResourceUnavailableException {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if (reviewOptional.isPresent())
            return ResponseEntity.ok(getReviewDTO(reviewOptional.get()));
        throw new ResourceUnavailableException("Review with ID " + reviewId + " is unavailable.");
    }


    @Override
    public ResponseEntity<ReviewDTO> createReview(Review review) throws ResourceUnavailableException {
        if (companyClient.getCompany(review.getCompanyId()).getStatusCode().isError())
            throw new ResourceUnavailableException("Uh uh! Something went wrong.");
        return new ResponseEntity<>(getReviewDTO(reviewRepository.save(review)), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ReviewDTO> replaceReview(Long reviewId, Review review) throws ResourceUnavailableException {
        if (reviewRepository.existsById(reviewId)) {
            review.setId(reviewId);
            if (companyClient.getCompany(review.getCompanyId()).getStatusCode().isError())
                throw new ResourceUnavailableException("Uh uh! Something went wrong.");
            return ResponseEntity.ok(getReviewDTO(reviewRepository.save(review)));
        }
        throw new ResourceUnavailableException("Review with ID " + reviewId + " is unavailable.");
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

    private ReviewDTO getReviewDTO(Review review) {
        return CompanyToReviewMapper.mapToReviewDTO(review, companyClient.getCompany(review.getCompanyId()).getBody());
    }
}
