package eu.mrndesign.www.matned.repository;

import eu.mrndesign.www.matned.dto.UserDTO;
import eu.mrndesign.www.matned.model.Person;
import eu.mrndesign.www.matned.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select case when count(u)> 0 then true else false end from User u where lower(u.login) like lower(?1)")
    boolean existsByLogin(String login);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Query("select u from User u where lower(u.login) = lower(?1) ")
    Optional<User> findAuditorByLogin(String login);

    @Query("select u from User u where lower(u.login) = lower(?1) ")
    User findByLogin(String login);
}
