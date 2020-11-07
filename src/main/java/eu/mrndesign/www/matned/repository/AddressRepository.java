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

    @Query("select a from Address a inner join Street s where lower(s.streetName) like lower(concat('%', concat(:searchedElement, '%')))")
    Page<Address> findByStreetName(@Param("searchedElement") String searchedElement, Pageable pageable);

    @Query("select a from Address a inner join Street s where lower(s.postCode) like lower(concat('%', concat(:searchedElement, '%')))")
    Page<Address> findByPostCode(@Param("searchedElement") String searchedElement, Pageable pageable);

    @Query("select a from Address a inner join City c where lower(c.cityName) like lower(concat('%', concat(:searchedElement, '%')))")
    Page<Address> findByCity(@Param("searchedElement") String searchedElement, Pageable pageable);

    @Query("select a from Address a inner join Country c where lower(c.countryName) like lower(concat('%', concat(:searchedElement, '%')))")
    Page<Address> findByCountry(@Param("searchedElement") String searchedElement, Pageable pageable);
}
