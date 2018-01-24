package rest.repositories;

import java.io.Serializable;
import org.springframework.data.repository.CrudRepository;
import rest.models.Device;

public interface DeviceRepository extends CrudRepository<Device, Integer> {

}
