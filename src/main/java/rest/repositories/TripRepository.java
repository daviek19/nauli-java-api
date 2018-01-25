package rest.repositories;

import org.springframework.data.repository.CrudRepository;
import rest.models.Trip;

public interface TripRepository extends CrudRepository<Trip, Integer> {

}
