package rest.repositories;

import java.io.Serializable;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

import rest.entities.Merchant;

public interface MerchantRepository extends CrudRepository<Merchant, Integer> {

    public Merchant findByConversationId(UUID converstaionId);
}
