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
import org.springframework.web.bind.annotation.ResponseBody;
import rest.repositories.MerchantRepository;

import rest.entities.Merchant;
import rest.utils.responses.ErrorResponse;
import rest.utils.responses.CustomResponse;
import rest.utils.responses.SuccessResponse;

@Controller
@RequestMapping(path = "api/merchants",
        headers = "Accept=application/json",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class MerchantController {

    @Autowired
    private MerchantRepository merchantRepository;

    @GetMapping("/")
    public @ResponseBody
    ResponseEntity<CustomResponse> getAllMerchants() {
        Iterable<Merchant> merchants = merchantRepository.findAll();
        return new SuccessResponse(merchants).send(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getMerchant(
            @PathVariable(value = "id") UUID conversationId) {

        Merchant merchant = merchantRepository.findByConversationId(conversationId);

        if (merchant == null) {
            HashMap<String, String> response = new HashMap<>();
            response.put("conversationId", conversationId.toString());
            return new ErrorResponse(response)
                    .send(HttpStatus.NOT_FOUND, "Merchant not found");
        }

        return new SuccessResponse(merchant).send(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteMerchant(
            @PathVariable(value = "id") UUID conversationId) {

        Merchant merchant = merchantRepository.findByConversationId(conversationId);

        if (merchant == null) {
            HashMap<String, String> response = new HashMap<>();
            response.put("conversationId", conversationId.toString());
            return new ErrorResponse(response)
                    .send(HttpStatus.NOT_FOUND, "Merchant not found");
        }

        merchantRepository.delete(merchant);
        return new SuccessResponse().send(HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<CustomResponse> createMerchant(
            @Valid @RequestBody Merchant merchant) {

        merchant.setConversationId(UUID.randomUUID());
        merchantRepository.save(merchant);

        HashMap<String, String> response = new HashMap<>();
        response.put("conversationId", merchant.getConversationId().toString());
        response.put("timestamp", merchant.getDateCreated().toString());

        return new SuccessResponse(response).send(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> updateMerchant(
            @PathVariable(value = "id") UUID conversationId,
            @Valid @RequestBody Merchant merchant
    ) {

        Merchant foundMerchant = merchantRepository.findByConversationId(conversationId);

        if (foundMerchant == null) {
            HashMap<String, String> response = new HashMap<>();
            response.put("conversationId", conversationId.toString());
            return new ErrorResponse(response)
                    .send(HttpStatus.NOT_FOUND, "Merchant not found");
        }

        //overide old details
        foundMerchant.setAddress(merchant.getAddress());
        foundMerchant.setEmail(merchant.getEmail());
        foundMerchant.setName(merchant.getName());
        foundMerchant.setWebsite(merchant.getWebsite());
        foundMerchant.setPhoneNumber(merchant.getPhoneNumber());

        Merchant updatedMerchant = merchantRepository.save(foundMerchant);

        HashMap<String, String> response = new HashMap<>();
        response.put("conversationId", updatedMerchant.getConversationId().toString());
        response.put("timestamp", new Date().toString());

        return new SuccessResponse(response).send(HttpStatus.OK);
    }

    @GetMapping("/{id}/devices")
    public ResponseEntity<CustomResponse> getDevices(
            @PathVariable(value = "id") UUID conversationId) {
        Merchant merchant = merchantRepository.findByConversationId(conversationId);

        if (merchant == null) {
            HashMap<String, String> response = new HashMap<>();
            response.put("conversationId", conversationId.toString());
            return new ErrorResponse(response)
                    .send(HttpStatus.NOT_FOUND, "Merchant not found");
        }
            return new SuccessResponse(merchant.getDevices()).send(HttpStatus.OK);
    }
    
    @PostMapping("/{id}/devices")
     public ResponseEntity<CustomResponse> createDevice(){
         throw new UnsupportedOperationException("Not supported yet.");
     }
}
