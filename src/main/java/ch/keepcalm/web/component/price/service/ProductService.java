package ch.keepcalm.web.component.price.service;

import ch.keepcalm.web.component.price.model.Product;
import ch.keepcalm.web.component.price.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcelwidmer on 22/03/16.
 */
// tag::ProductService[]
@Service
@Transactional
public class ProductService {

    private ProductRepository repository;
    @Autowired
    public void setProductRepository(ProductRepository repository) {
        this.repository = repository;
    }

    public Product getProductById(int id) {
        return repository.findOne(id);
    }

    public List<Product> getProductByCustomerId(int customerId) {
        return repository.getProductByCustomerId(customerId);
    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<Product>();
        for (Product model : repository.findAll()) { // TODO: 12/07/16  no better solution
            products.add(model);
        }
        return products;
    }

    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    public Product updateProduct(Product product) {
        return repository.saveAndFlush(product);
    }
}
// end::ProductService[]
