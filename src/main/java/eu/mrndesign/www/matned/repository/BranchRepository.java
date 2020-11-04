package eu.mrndesign.www.matned.repository;

import eu.mrndesign.www.matned.model.company.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
}
