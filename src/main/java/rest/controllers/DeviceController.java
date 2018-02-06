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
import org.springframework.web.bind.annotation.ResponseBody;
import rest.entities.Device;
import rest.entities.Trip;
import rest.repositories.DeviceRepository;
import rest.repositories.TripRepository;
import rest.utils.responses.ErrorResponse;
import rest.utils.responses.CustomResponse;
import rest.utils.responses.SuccessResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping(path = "api/devices",
        headers = "Accept=application/json",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class DeviceController {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private TripRepository tripRepository;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/")
    public ResponseEntity<CustomResponse> getAllDevices() {
        Iterable<Device> devices = deviceRepository.findAll();
        return new SuccessResponse(devices).send(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public @ResponseBody
    ResponseEntity<CustomResponse> getDevice(
            @PathVariable(value = "id") UUID conversationId) {
        Device device = deviceRepository.findByConversationId(conversationId);
        if (device == null) {
            HashMap<String, String> response = new HashMap<>();
            response.put("conversationId", conversationId.toString());
            return new ErrorResponse(response)
                    .send(HttpStatus.NOT_FOUND, "Device not found");
        }

        return new SuccessResponse(device).send(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteDevice(
            @PathVariable(value = "id") UUID conversationId) {

        Device device = deviceRepository.findByConversationId(conversationId);

        if (device == null) {
            HashMap<String, String> response = new HashMap<>();
            response.put("conversationId", conversationId.toString());
            return new ErrorResponse(response)
                    .send(HttpStatus.NOT_FOUND, "Device not found");
        }

        deviceRepository.delete(device);
        return new SuccessResponse().send(HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<CustomResponse> createDevice(
            @Valid @RequestBody Device device) {

        device.setConversationId(UUID.randomUUID());
        deviceRepository.save(device);

        HashMap<String, String> response = new HashMap<>();
        response.put("conversationId", device.getConversationId().toString());
        response.put("timestamp", device.getDateCreated().toString());

        return new SuccessResponse(response).send(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> updateDevice(
            @PathVariable(value = "id") UUID conversationId,
            @Valid @RequestBody Device device
    ) {

        Device foundDevice = deviceRepository.findByConversationId(conversationId);

        if (foundDevice == null) {
            HashMap<String, String> response = new HashMap<>();
            response.put("conversationId", conversationId.toString());
            return new ErrorResponse(response)
                    .send(HttpStatus.NOT_FOUND, "Device not found");
        }

        //overide old details
        foundDevice.setIsActive(device.getIsActive());
        foundDevice.setPin(device.getPin());

        Device updatedDevice = deviceRepository.save(foundDevice);

        HashMap<String, String> response = new HashMap<>();
        response.put("conversationId", updatedDevice.getConversationId().toString());
        response.put("timestamp", new Date().toString());

        return new SuccessResponse(response).send(HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<CustomResponse> authenticate(
            @RequestBody Device device) {

        if (device.getDeviceName().isEmpty()) {
            return new ErrorResponse().send(HttpStatus.BAD_REQUEST, "Empty device name");
        }

        if (device.getPin().isEmpty()) {
            return new ErrorResponse().send(HttpStatus.BAD_REQUEST, "Empty device pin");
        }

        Device foundDevice = deviceRepository.findByDeviceNameAndPin(device.getDeviceName(),
                device.getPin());

        if (foundDevice == null) {
            return new ErrorResponse()
                    .send(HttpStatus.NOT_FOUND, "Device not found");
        }

        return new SuccessResponse(foundDevice).send(HttpStatus.OK);
    }

    @PostMapping("/{id}/trips")
    public ResponseEntity<CustomResponse> createDeviceTrip(
            @Valid @RequestBody Trip trip,
            @PathVariable(value = "id") UUID conversationId) {

        Device device = deviceRepository.findByConversationId(conversationId);

        if (device == null) {
            HashMap<String, String> response = new HashMap<>();
            response.put("conversationId", conversationId.toString());
            return new ErrorResponse(response)
                    .send(HttpStatus.NOT_FOUND, "Device not found");
        }

        trip.setConversationId(UUID.randomUUID());
        trip.setTripReference(RandomStringUtils
                .random(6, true, true)
                .toUpperCase());
        trip.setTripStatus(0);
        trip.setDevice(device);
        tripRepository.save(trip);
       
        return new SuccessResponse(trip).send(HttpStatus.OK);
    }

    @GetMapping("/{id}/trips")
    public ResponseEntity<CustomResponse> getAllDeviceTrips(
            @PathVariable(value = "id") UUID conversationId) {
        Device device = deviceRepository.findByConversationId(conversationId);

        if (device == null) {
            HashMap<String, String> response = new HashMap<>();
            response.put("conversationId", conversationId.toString());
            return new ErrorResponse(response)
                    .send(HttpStatus.NOT_FOUND, "Device not found");
        }

        return new SuccessResponse(device.getTrips()).send(HttpStatus.OK);
    }

    @GetMapping("/{id}/latest-trips")
    public ResponseEntity<CustomResponse> getAllLatestDeviceTrips(
            @PathVariable(value = "id") UUID conversationId) {
        Device device = deviceRepository.findByConversationId(conversationId);

        if (device == null) {
            HashMap<String, String> response = new HashMap<>();
            response.put("conversationId", conversationId.toString());
            return new ErrorResponse(response)
                    .send(HttpStatus.NOT_FOUND, "Device not found");
        }
        Iterable<Trip> latestTrips = tripRepository.findFirst5ByDeviceOrderByDateCreatedDesc(device);
        return new SuccessResponse(latestTrips).send(HttpStatus.OK);
    }
}
