package eu.mrndesign.www.matned.repository;

import eu.mrndesign.www.matned.model.company.CompanyDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CompanyDetailsRepository extends JpaRepository<CompanyDetails, Long> {

    @Query("select cd from CompanyDetails cd join Company c on c.companyDetails.id = cd.id where c.id = ?1")
    Optional<CompanyDetails> findByCompanyId(Long id);
}
