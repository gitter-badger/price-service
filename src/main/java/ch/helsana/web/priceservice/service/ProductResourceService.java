package ch.helsana.web.priceservice.service;

import ch.helsana.web.priceservice.api.product.ProductResource;
import ch.helsana.web.priceservice.api.product.ProductResourceAssembler;
import ch.helsana.web.priceservice.model.Product;
import ch.helsana.web.priceservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcelwidmer on 22/03/16.
 */
// tag::ProductResourceService[]
@Service
public class ProductResourceService {


    private ProductRepository repository;
    private ProductResourceAssembler resourceAssembler;

    @Autowired
    public void setProductRepository(ProductRepository repository, ProductResourceAssembler resourceAssembler) {
        this.repository = repository;
        this.resourceAssembler = resourceAssembler;
    }


    /**
     * Convert model to Resource
     *
     * @param id
     * @return  one ProductResource
     */
    @Transactional
    public ProductResource findOne(Integer id) {
        Product model = repository.findOne(id);
        ProductResource resource = resourceAssembler.toResource(model);
        return resource;
    }

    /**
     * Convert model to resource
     *
     * @return a list of ProductResources
     */
    @Transactional
    public List<ProductResource> listAll() {
        List<ProductResource> products = new ArrayList<ProductResource>();
        for (Product model : repository.findAll()) {
            products.add(resourceAssembler.toResource(model));
        }
        return products;
    }

}
// end::ProductResourceService[]
