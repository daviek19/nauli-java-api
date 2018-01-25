package rest.repositories;

import java.io.Serializable;
import org.springframework.data.repository.CrudRepository;
import rest.models.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Integer> {

}
