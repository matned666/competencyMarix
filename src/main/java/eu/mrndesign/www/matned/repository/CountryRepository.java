package eu.mrndesign.www.matned.repository;

import eu.mrndesign.www.matned.model.address.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    @Query("select c from Country c where c.countryName = ?1")
    Optional<Country> findCountryByName(String countryFromDTO);
}
