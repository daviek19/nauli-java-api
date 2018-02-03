package rest.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import javax.validation.Valid;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import rest.entities.Merchant;
import rest.entities.Trip;
import rest.repositories.TripRepository;
import rest.utils.responses.CustomResponse;
import rest.utils.responses.ErrorResponse;
import rest.utils.responses.SuccessResponse;

@Controller
@RequestMapping(path = "api/trips",
        headers = "Accept=application/json",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class TripController {

    @Autowired
    TripRepository tripRepository;

    @GetMapping("/")
    public ResponseEntity<CustomResponse> getTrips() {
        Iterable<Trip> trips = tripRepository.findAll();
        return new SuccessResponse(trips).send(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getSingleTrip(@PathVariable(value = "id") UUID tripId) {
        Trip trip = tripRepository.findByConversationId(tripId);
        if (trip == null) {
            HashMap<String, String> response = new HashMap<>();
            response.put("conversationId", tripId.toString());
            return new ErrorResponse(response)
                    .send(HttpStatus.NOT_FOUND, "Trip not found");
        }
        return new SuccessResponse(trip).send(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> updateTrip(
            @PathVariable(value = "id") UUID conversationId,
            @Valid @RequestBody Trip trip) {

        Trip foundTrip = tripRepository.findByConversationId(conversationId);

        if (foundTrip == null) {
            HashMap<String, String> response = new HashMap<>();
            response.put("conversationId", conversationId.toString());
            return new ErrorResponse(response)
                    .send(HttpStatus.NOT_FOUND, "Trip not found");
        }

        //overide old details
        foundTrip.setFareAmount(trip.getFareAmount());
        foundTrip.setTripFrom(trip.getTripFrom());
        foundTrip.setTripTo(trip.getTripTo());

        Trip updatedTrip = tripRepository.save(foundTrip);

        HashMap<String, String> response = new HashMap<>();
        response.put("conversationId", updatedTrip.getConversationId().toString());
        response.put("timestamp", new Date().toString());

        return new SuccessResponse(response).send(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteTrip(
            @PathVariable(value = "id") UUID conversationId) {
        Trip trip = tripRepository.findByConversationId(conversationId);

        if (trip == null) {
            HashMap<String, String> response = new HashMap<>();
            response.put("conversationId", conversationId.toString());
            return new ErrorResponse(response)
                    .send(HttpStatus.NOT_FOUND, "Trip not found");
        }

        tripRepository.delete(trip);
        return new SuccessResponse().send(HttpStatus.OK);
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<CustomResponse> startTrip(@PathVariable(value = "id") UUID conversationId) {

        Trip foundTrip = tripRepository.findByConversationId(conversationId);

        if (foundTrip == null) {
            HashMap<String, String> response = new HashMap<>();
            response.put("conversationId", conversationId.toString());
            return new ErrorResponse(response)
                    .send(HttpStatus.NOT_FOUND, "Trip not found");
        }

        //Dont start trip when we have another ongoing one.
        Iterable<Trip> onGoingTrips = tripRepository
                .findByTripStatusAndDeviceId(1, foundTrip.getDevice().getDeviceId());

        String ongoingError = "This trip could not be started. "
                + "Stop any ongoing trip first.";

        if (onGoingTrips != null) {
            return new ErrorResponse()
                    .send(HttpStatus.BAD_REQUEST, ongoingError);
        }

        //Save the details
        foundTrip.setTripStatus(1);
        Trip updatedTrip = tripRepository.save(foundTrip);

        HashMap<String, String> response = new HashMap<>();
        response.put("conversationId", updatedTrip.getConversationId().toString());
        response.put("timestamp", new Date().toString());

        return new SuccessResponse(response).send(HttpStatus.OK);
    }

    @PostMapping("/{id}/stop")
    public ResponseEntity<CustomResponse> stopTrip(@PathVariable(value = "id") UUID conversationId) {
        Trip foundTrip = tripRepository.findByConversationId(conversationId);

        if (foundTrip == null) {
            HashMap<String, String> response = new HashMap<>();
            response.put("conversationId", conversationId.toString());
            return new ErrorResponse(response)
                    .send(HttpStatus.NOT_FOUND, "Trip not found");
        }

        //overide old details
        foundTrip.setTripStatus(0);
        Trip updatedTrip = tripRepository.save(foundTrip);

        HashMap<String, String> response = new HashMap<>();
        response.put("conversationId", updatedTrip.getConversationId().toString());
        response.put("timestamp", new Date().toString());

        return new SuccessResponse(response).send(HttpStatus.OK);
    }

}
