package eu.mrndesign.www.matned.repository;

import eu.mrndesign.www.matned.model.Person;
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

    @Query("select p from Person  p where lower(p.firstName) = lower(?1) ")
    List<Person> findByFirstName(String firstName);

    @Query("select p from Person  p where lower(p.lastName) = lower(?1) ")
    List<Person> findByLastName(String lastName);

    @Query("select p from Person  p where lower(p.firstName) like lower(concat('%', concat(:name, '%'))) ")
    List<Person> findByFirstNameNotPrecise(@Param("name") String firstName);

    @Query("select p from Person  p where lower(p.lastName) like lower(concat('%', concat(:name, '%')))")
    List<Person> findByLastNameNotPrecise(@Param("name") String name);

    @Query("select p from Person p where p.address.id = ?1")
    List<Person> findByAddressId(Long addressId);
}

