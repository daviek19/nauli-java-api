package rest.repositories;

import java.io.Serializable;
import org.springframework.data.repository.CrudRepository;

import rest.models.Merchant;

public interface MerchantRepository extends CrudRepository<Merchant, Integer> {

}
