package rest.repositories;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import rest.entities.Trip;

public interface TripRepository extends CrudRepository<Trip, Integer> {

    public Trip findByConversationId(UUID tripId);

}
