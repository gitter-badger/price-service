package ch.keepcalm.web.component.price.repository;

import ch.keepcalm.web.component.price.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hkesq on 20.06.2016.
 */
public interface CustomerRepository extends JpaRepository<Customer,  Integer> {
    Customer findById(String id);

}
