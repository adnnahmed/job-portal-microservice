package personal.project.job.clients;

import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import personal.project.job.models.external.Review;

import java.util.List;

@FeignClient(name = "review-service")
public interface ReviewClient {

    @GetMapping("/reviews")
    ResponseEntity<List<Review>> getReviews(@RequestParam("companyId") Long companyId) throws FeignException;
}
