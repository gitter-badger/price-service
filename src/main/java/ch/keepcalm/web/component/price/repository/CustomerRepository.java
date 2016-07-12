package ch.keepcalm.web.component.price.repository;

import ch.keepcalm.web.component.price.model.Customer;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by hkesq on 20.06.2016.
 */
public interface CustomerRepository extends CrudRepository<Customer,  Integer> {
    Customer findById(String id);

}
