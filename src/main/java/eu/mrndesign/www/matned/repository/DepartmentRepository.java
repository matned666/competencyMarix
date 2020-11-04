package eu.mrndesign.www.matned.repository;

import eu.mrndesign.www.matned.model.company.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
