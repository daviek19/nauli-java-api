package rest.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import javax.validation.Valid;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import rest.entities.Device;
import rest.repositories.DeviceRepository;
import rest.utils.ApiResponse;

@Controller
@RequestMapping(path = "api/devices",
        headers = "Accept=application/json",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class DeviceController {

    @Autowired
    private DeviceRepository deviceRepository;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAllDevices() {
        Iterable<Device> devices = deviceRepository.findAll();
        return new ApiResponse(devices).send(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public @ResponseBody
    ResponseEntity<ApiResponse> getDevice(@PathVariable(value = "id") UUID conversationId) {
        Device device = deviceRepository.findByConversationId(conversationId);
        if (device == null) {
            HashMap<String, String> response = new HashMap<>();
            response.put("conversationId", conversationId.toString());
            return new ApiResponse(response)
                    .send(HttpStatus.NOT_FOUND, "Device not found");
        }

        return new ApiResponse(device).send(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteDevice(
            @PathVariable(value = "id") UUID conversationId) {

        Device device = deviceRepository.findByConversationId(conversationId);

        if (device == null) {
            HashMap<String, String> response = new HashMap<>();
            response.put("conversationId", conversationId.toString());
            return new ApiResponse(response)
                    .send(HttpStatus.NOT_FOUND, "Device not found");
        }

        deviceRepository.delete(device);
        return new ApiResponse().send(HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse> createDevice(@Valid @RequestBody Device device) {

        device.setConversationId(UUID.randomUUID());
        deviceRepository.save(device);

        HashMap<String, String> response = new HashMap<>();
        response.put("conversationId", device.getConversationId().toString());
        response.put("timestamp", device.getDateCreated().toString());

        return new ApiResponse(response).send(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateDevice(
            @PathVariable(value = "id") UUID conversationId,
            @Valid @RequestBody Device device
    ) {

        Device foundDevice = deviceRepository.findByConversationId(conversationId);

        if (foundDevice == null) {
            HashMap<String, String> response = new HashMap<>();
            response.put("conversationId", conversationId.toString());
            return new ApiResponse(response)
                    .send(HttpStatus.NOT_FOUND, "Device not found");
        }

        //overide old details
        foundDevice.setIsActive(device.getIsActive());
        foundDevice.setPin(device.getPin());

        Device updatedDevice = deviceRepository.save(foundDevice);

        HashMap<String, String> response = new HashMap<>();
        response.put("conversationId", updatedDevice.getConversationId().toString());
        response.put("timestamp", new Date().toString());

        return new ApiResponse(response).send(HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponse> authenticate(@RequestBody Device device) {

        if (device.getDeviceName().isEmpty()) {
            return new ApiResponse().send(HttpStatus.BAD_REQUEST, "Empty device name");
        }

        if (device.getPin().isEmpty()) {
            return new ApiResponse().send(HttpStatus.BAD_REQUEST, "Empty device pin");
        }

        Device foundDevice = deviceRepository.findByDeviceNameAndPin(device.getDeviceName(),
                device.getPin());

        if (foundDevice == null) {
            return new ApiResponse()
                    .send(HttpStatus.NOT_FOUND, "Device not found");
        }

        return new ApiResponse(foundDevice).send(HttpStatus.OK);
    }
}
