package personal.project.company.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import personal.project.company.exceptions.ResourceUnavailableException;
import personal.project.company.models.Company;
import personal.project.company.repositories.CompanyRepository;
import personal.project.company.services.CompanyService;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImplementation implements CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImplementation(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public ResponseEntity<List<Company>> findAllCompanies() throws ResourceUnavailableException {
        List<Company> companiesList = companyRepository.findAll();
        if (companiesList.isEmpty()) {
            throw new ResourceUnavailableException("No data is available.");
        }
        return ResponseEntity.ok(companiesList);
    }

    @Override
    public ResponseEntity<Company> findSingleCompany(Long companyId) throws ResourceUnavailableException {
        Optional<Company> companyOptional = companyRepository.findById(companyId);
        if (companyOptional.isPresent())
            return ResponseEntity.ok(companyOptional.get());
        throw new ResourceUnavailableException("Company with ID " + companyId + " is unavailable.");
    }

    @Override
    public ResponseEntity<Company> createCompany(Company company) {
        return new ResponseEntity<>(companyRepository.save(company), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Company> replaceCompany(Long companyId, Company company) throws ResourceUnavailableException {
        if (companyRepository.existsById(companyId)) {
            company.setId(companyId);
            return ResponseEntity.ok(companyRepository.save(company));
        }
        throw new ResourceUnavailableException("Company with ID " + companyId + " is unavailable.");
    }

    @Override
    public ResponseEntity<String> deleteCompany(Long companyId) throws ResourceUnavailableException {
        if (companyRepository.existsById(companyId)) {
            companyRepository.deleteById(companyId);
            return ResponseEntity.ok("Company with ID " + companyId + " has been deleted.");
        }
        throw new ResourceUnavailableException("Company with ID " + companyId + " is unavailable.");
    }
}
