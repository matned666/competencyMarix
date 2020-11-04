package eu.mrndesign.www.matned.repository;

import eu.mrndesign.www.matned.model.company.CompanyDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyDetailsRepository extends JpaRepository<CompanyDetails, Long> {
}
