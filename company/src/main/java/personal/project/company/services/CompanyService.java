package personal.project.company.services;

import org.springframework.http.ResponseEntity;
import personal.project.company.exceptions.ResourceUnavailableException;
import personal.project.company.models.Company;

import java.util.List;

public interface CompanyService {

    ResponseEntity<List<Company>> findAllCompanies() throws ResourceUnavailableException;

    ResponseEntity<Company> findSingleCompany(Long companyId) throws ResourceUnavailableException;

    ResponseEntity<Company> createCompany(Company company);

    ResponseEntity<Company> replaceCompany(Long companyId, Company company) throws ResourceUnavailableException;

    ResponseEntity<String> deleteCompany(Long companyId) throws ResourceUnavailableException;
}
