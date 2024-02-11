package personal.project.company.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import personal.project.company.models.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
