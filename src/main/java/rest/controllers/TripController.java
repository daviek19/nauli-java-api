package rest.controllers;

import java.util.UUID;
import javax.validation.Valid;
import javax.xml.ws.Response;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.ResponseBody;
import rest.entities.Trip;
import rest.repositories.TripRepository;
import rest.utils.ApiResponse;

@Controller
@RequestMapping(path = "api/trips",
        headers = "Accept=application/json",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class TripController {

    @Autowired
    TripRepository tripRepository;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getTrips() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getSingleTrip(@PathVariable(value = "id") UUID tripId) {
        return null;
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse> createTrip(@Valid @RequestBody Trip trip) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateTrip(
            @PathVariable(value = "id") UUID tripId,
            @Valid @RequestBody Trip trip) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteTrip(
            @PathVariable(value = "id") UUID tripId) {
        return null;
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<ApiResponse> startTrip(@PathVariable(value = "id") UUID tripId) {
        return null;
    }

    @PostMapping("/{id}/stop")
    public ResponseEntity<ApiResponse> stopTrip(@PathVariable(value = "id") UUID tripId) {
        return null;
    }
}
