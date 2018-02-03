package rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rest.entities.Account;
import rest.repositories.AccountRepository;
import rest.utils.responses.ErrorResponse;
import rest.utils.responses.CustomResponse;

@Controller
@RequestMapping(path = "/account")
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/create",
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomResponse> createAccount(@RequestBody Account account) {

        if (account == null) {
            return new ErrorResponse().send(HttpStatus.BAD_REQUEST, "Empty obj");
        }

        if (account.getUsername().isEmpty()) {
            return new ErrorResponse().send(HttpStatus.BAD_REQUEST, "Empty user name");
        }

        if (account.getPassword().isEmpty()) {
            return new ErrorResponse().send(HttpStatus.BAD_REQUEST, "Empty user password");
        }

        accountRepository.save(account);
        return new ErrorResponse(account).send(HttpStatus.OK);
    }

}
