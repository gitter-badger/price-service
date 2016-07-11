package ch.helsana.web.priceservice.repository;

import ch.helsana.web.priceservice.model.Product;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by marcelwidmer on 21/03/16.
 * The Spring Data JPA CRUD Repository
 */
// tag::ProductRepository[]
@RepositoryRestResource(path="products", collectionResourceRel = "products", itemResourceRel = "product")
@Transactional
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {
    Product findByProductId(String id);
// end::ProductRepository[]
}
