package eu.mrndesign.www.matned.repository;

import eu.mrndesign.www.matned.model.PersonCompetence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonCompetenceRepository extends JpaRepository<PersonCompetence, Long> {
}
