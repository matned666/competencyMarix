package eu.mrndesign.www.matned.repository;

import eu.mrndesign.www.matned.model.company.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {


}
