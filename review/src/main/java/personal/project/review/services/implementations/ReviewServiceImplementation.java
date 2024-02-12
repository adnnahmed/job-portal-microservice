package personal.project.review.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import personal.project.review.exceptions.ResourceUnavailableException;
import personal.project.review.mapper.CompanyToReviewMapper;
import personal.project.review.models.Review;
import personal.project.review.models.dtos.ReviewDTO;
import personal.project.review.models.external.Company;
import personal.project.review.repositories.ReviewRepository;
import personal.project.review.services.ReviewService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImplementation implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public ReviewServiceImplementation(ReviewRepository reviewRepository, RestTemplate restTemplate) {
        this.reviewRepository = reviewRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<List<ReviewDTO>> getAllReviews(Long companyId) throws ResourceUnavailableException {
        List<Review> reviewsList = reviewRepository.findByCompanyId(companyId);
        /*if (reviewsList.isEmpty()) {
            throw new ResourceUnavailableException("No data is available.");
        }*/
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
        ReviewDTO reviewDTO = getReviewDTO(reviewRepository.save(review));
        return new ResponseEntity<>(reviewDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ReviewDTO> replaceReview(Long reviewId, Review review) throws ResourceUnavailableException {
        if (reviewRepository.existsById(reviewId)) {
            review.setId(reviewId);
            ReviewDTO reviewDTO = getReviewDTO(reviewRepository.save(review));
            return ResponseEntity.ok(reviewDTO);
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

    private Company getCompany(Long companyId) throws ResourceUnavailableException {
        try {
            return restTemplate.getForObject("http://COMPANY-SERVICE:8081/companies/" + companyId, Company.class);
        } catch (RestClientException e) {
            throw new ResourceUnavailableException("There seems to some problem with the Company service.");
        }
    }

    private ReviewDTO getReviewDTO(Review review) throws ResourceUnavailableException {
        return CompanyToReviewMapper.mapToReviewDTO(review, getCompany(review.getCompanyId()));
    }
}
