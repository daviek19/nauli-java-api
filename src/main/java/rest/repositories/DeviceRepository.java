package rest.repositories;

import java.io.Serializable;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import rest.entities.Device;

public interface DeviceRepository extends CrudRepository<Device, Integer> {

    public Device findByConversationId(UUID conversationId);

}
