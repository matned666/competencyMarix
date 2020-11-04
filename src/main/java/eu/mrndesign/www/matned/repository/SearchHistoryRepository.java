package eu.mrndesign.www.matned.repository;

import eu.mrndesign.www.matned.model.audit.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
}
