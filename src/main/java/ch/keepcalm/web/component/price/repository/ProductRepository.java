package ch.keepcalm.web.component.price.repository;

import ch.keepcalm.web.component.price.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by marcelwidmer on 21/03/16.
 * The Spring Data JPA CRUD Repository
 */
// tag::ProductRepository[]
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> getProductByCustomerId(int customerId);
// end::ProductRepository[]
}
