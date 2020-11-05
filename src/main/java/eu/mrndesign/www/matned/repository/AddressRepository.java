package eu.mrndesign.www.matned.repository;

import eu.mrndesign.www.matned.model.address.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.net.ContentHandler;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("select a from Address a inner join Street s where lower(s.streetName) = lower(:street)")
    Page<Address> findByStreetName(@Param("street") String streetName, Pageable pageable);

    @Query("select a from Address a inner join Street s where lower(s.postCode) = lower(:postCode)")
    Page<Address> findByPostCode(@Param("postCode") String streetName, Pageable pageable);

    @Query("select a from Address a inner join City c where lower(c.cityName) = lower(:city)")
    Page<Address> findByCity(@Param("city") String streetName, Pageable pageable);

    @Query("select a from Address a inner join Country c where lower(c.countryName) = lower(:country)")
    Page<Address> findByCountry(@Param("country") String streetName, Pageable pageable);
}
