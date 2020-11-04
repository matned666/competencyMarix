package eu.mrndesign.www.matned.repository;

import eu.mrndesign.www.matned.model.address.Street;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StreetRepository extends JpaRepository<Street, Long> {

    @Query("select s from Street s where s.streetName = :street and s.postCode = :postCode")
    Optional<Street> findByStreetNameAdnPostCode(@Param("street") String StreetName, @Param("postCode") String postCode);

    @Query("select s from Street s where s.postCode = :code")
    Page<Street> findByPostCode(@Param("code") String postCode, Pageable pageable);

    @Query("select s from Street s where s.streetName = :name")
    Page<Street> findByStreetName(@Param("name") String streetName, Pageable pageable);


}
