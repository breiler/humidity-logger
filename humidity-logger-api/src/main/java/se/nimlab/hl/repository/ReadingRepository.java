package se.nimlab.hl.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.nimlab.hl.model.Reading;

import java.util.List;

@Repository
public interface ReadingRepository extends CrudRepository<Reading, Long>, ReadingRepositoryCustom {
    List<Reading> findByDeviceId(Long deviceId);
}

