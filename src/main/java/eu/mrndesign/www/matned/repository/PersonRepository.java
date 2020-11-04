package eu.mrndesign.www.matned.repository;

import eu.mrndesign.www.matned.model.personal.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("select p from User u join Person p on p.id = u.person.id where u.id = ?1")
    Optional<Person> findUserPersonalDataByUser(Long id);

    @Query("delete from Person p where p.id = ?1")
    void deletePersonById(Long id);

    @Query("select p from Person  p where lower(p.firstName) = lower(:firstName) ")
    Page<Person> findByFirstName(@Param("firstName") String firstName, Pageable pageable);

    @Query("select p from Person  p where lower(p.lastName) = lower(:lastName) ")
    Page<Person> findByLastName(@Param("lastName") String lastName, Pageable pageable);

    @Query("select p from Person  p where lower(p.firstName) like lower(concat('%', concat(:name, '%'))) ")
    Page<Person> findByFirstNameNotPrecise(@Param("name") String firstName, Pageable pageable);

    @Query("select p from Person  p where lower(p.lastName) like lower(concat('%', concat(:name, '%')))")
    Page<Person> findByLastNameNotPrecise(@Param("name") String name, Pageable pageable);

    @Query("select p from Person p where p.address.id = :address_id")
    Page<Person> findByAddressId(@Param("address_id") Long addressId, Pageable pageable);
}

