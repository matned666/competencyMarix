package eu.mrndesign.www.matned.repository;

import eu.mrndesign.www.matned.dto.BranchDTO;
import eu.mrndesign.www.matned.model.company.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

    @Query("select case when count(br)> 0 then false else true end from Branch br where br.company.id = ?1")
    boolean isFirstBranch(Long companyId);

    @Query("select br from Branch br where br.company.id = :companyId")
    Page<Branch> findAllByCompanyId(@Param("companyId") Long companyId, Pageable pageable);
}
