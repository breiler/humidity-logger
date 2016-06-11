package se.nimlab.hl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.nimlab.hl.model.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
}

