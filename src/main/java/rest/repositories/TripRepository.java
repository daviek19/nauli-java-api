package rest.repositories;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import rest.entities.Device;
import rest.entities.Trip;

public interface TripRepository extends CrudRepository<Trip, Integer> {

    public Trip findByConversationId(UUID tripId);

    /*Find all trips by device based on (Active/Stopped)Status*/
    public Iterable<Trip> findByTripStatusAndDevice(Integer status, Device device);

    /*Find all trips by device*/
    public Iterable<Trip> findByDeviceOrderByDateCreatedDesc(Device device);

    /*Find latest trips by id*/
    public Iterable<Trip> findFirst5ByDeviceOrderByDateCreatedDesc(Device device);
}
