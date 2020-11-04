package eu.mrndesign.www.matned.repository;

import eu.mrndesign.www.matned.model.company.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
}
