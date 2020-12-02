package eu.mrndesign.www.matned.repository;

import eu.mrndesign.www.matned.model.personal.Competence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetenceRepository extends JpaRepository<Competence, Long> {

    @Query("select c from Competence c where :name = c.entityDescription.name and :description = c.entityDescription.description")
    List<Competence> findByNameAndDescription(@Param("name") String name, @Param("description") String description);
}
