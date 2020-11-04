package eu.mrndesign.www.matned.repository;

import eu.mrndesign.www.matned.model.company.PositionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionDetailsRepository extends JpaRepository<PositionDetails, Long> {
}
