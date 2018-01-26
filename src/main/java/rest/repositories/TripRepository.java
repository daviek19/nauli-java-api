package rest.repositories;

import org.springframework.data.repository.CrudRepository;
import rest.entities.Trip;

public interface TripRepository extends CrudRepository<Trip, Integer> {

}
