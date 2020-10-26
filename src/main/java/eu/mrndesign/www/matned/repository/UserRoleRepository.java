package eu.mrndesign.www.matned.repository;

import eu.mrndesign.www.matned.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query("select case when count(ur)> 0 then true else false end from UserRole ur where lower(ur.roleName) like lower(?1)")
    boolean roleExists(String roleCheck);

    @Query("select ur from UserRole ur where lower(ur.roleName) = lower(?1)")
    UserRole findByRoleName(String userRole);

    @Query("select ur from User u join u.roles ur where u.login = :user_login")
    List<UserRole> findByUserLogin(@Param("user_login") String login);
}
