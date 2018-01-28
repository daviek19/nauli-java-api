package rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import rest.entities.Merchant;
import rest.repositories.DeviceRepository;
import rest.utils.ApiResponse;

@Controller
@RequestMapping(path = "api/device",
        headers = "Accept=application/json",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class DeviceController {

    @Autowired
    private DeviceRepository deviceRepository;

    @GetMapping("/")
    public @ResponseBody
    Iterable<Merchant> getAllDevices() {
        return deviceRepository.findAll();
    }
}
