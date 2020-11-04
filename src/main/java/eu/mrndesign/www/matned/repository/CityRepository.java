package eu.mrndesign.www.matned.repository;

import eu.mrndesign.www.matned.model.address.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query("select c from City c where c.cityName = ?1")
    Optional<City> findByCityName(String cityFromDTO);
}
