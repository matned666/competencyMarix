package eu.mrndesign.www.matned.repository;

import eu.mrndesign.www.matned.model.audit.AuditHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditHistoryRepository extends JpaRepository<AuditHistory, Long> {
}
