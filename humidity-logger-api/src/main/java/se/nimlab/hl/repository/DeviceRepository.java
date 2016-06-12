package se.nimlab.hl.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.nimlab.hl.model.Device;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Long> {
}

