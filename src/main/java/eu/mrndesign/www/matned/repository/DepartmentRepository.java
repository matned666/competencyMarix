package eu.mrndesign.www.matned.repository;

import eu.mrndesign.www.matned.model.company.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.net.ContentHandler;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("select dep from Department dep where dep.branch.id = :branchId")
    Page<Department> findAllByBranch(@Param("branchId") Long branchId, Pageable pageable);

    @Query("select dep from Department dep join Branch br on dep.branch.id = br.id where br.company.id = :companyId")
    Page<Department> findAllByCompany(Long companyId, Pageable pageable);
}
