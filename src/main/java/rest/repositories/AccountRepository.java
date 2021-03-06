package rest.repositories;

import org.springframework.data.repository.CrudRepository;
import rest.entities.Account;

public interface AccountRepository extends CrudRepository<Account, Integer> {

    public Account findByUsername(String userName);
}
