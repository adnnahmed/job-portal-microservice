package personal.project.review.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import personal.project.review.models.external.Company;

@FeignClient(name = "company-service")
public interface CompanyClient {

    @GetMapping("/companies/{companyId}")
    ResponseEntity<Company> getCompany(@PathVariable("companyId") Long companyId);
}
