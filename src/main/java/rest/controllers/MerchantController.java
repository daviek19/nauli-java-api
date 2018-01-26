package rest.controllers;

import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rest.repositories.MerchantRepository;

import rest.entities.Merchant;
import rest.utils.ApiResponse;

@Controller
@RequestMapping(path = "/merchants")
public class MerchantController {

    @Autowired
    private MerchantRepository merchantRepository;

    /**
     * Fetch all(paginated) merchants.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public @ResponseBody
    Iterable<Merchant> getAllMerchants() {
        return merchantRepository.findAll();
    }

    /**
     * Create a new merchant Add logging. Add response standards.
     */
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/create",
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> createMerchant(@Valid @RequestBody Merchant merchant) {
        merchant.setConversationId(UUID.randomUUID());
        merchantRepository.save(merchant);
        return new ApiResponse(merchant).send(HttpStatus.OK);
    }

}
