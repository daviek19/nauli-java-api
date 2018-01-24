package rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rest.repositories.MerchantRepository;

import rest.models.Merchant;

@Controller
@RequestMapping(path = "/merchant")
public class MerchantController {

    @Autowired
    private MerchantRepository merchantRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public @ResponseBody
    Iterable<Merchant> getAll() {
        return merchantRepository.findAll();
    }
}
