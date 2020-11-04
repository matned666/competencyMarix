package eu.mrndesign.www.matned.repository;

import eu.mrndesign.www.matned.model.personal.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
