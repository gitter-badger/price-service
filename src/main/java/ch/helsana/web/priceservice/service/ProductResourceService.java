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


    private ProductRepository productRepository;
    private ProductResourceAssembler productResourceAssembler;

    @Autowired
    public void setProductRepository(ProductRepository productRepository, ProductResourceAssembler productResourceAssembler) {
        this.productRepository = productRepository;
        this.productResourceAssembler = productResourceAssembler;
    }


    /**
     * Convert model to Resource
     *
     * @param productId
     * @return  one ProductResource
     */
    @Transactional
    public ProductResource findOne(Integer productId) {
        Product model = productRepository.findOne(productId);
        ProductResource productResource = productResourceAssembler.toResource(model);
        return productResource;
    }

    /**
     * Convert model to resource
     *
     * @return a list of ProductResources
     */
    @Transactional
    public List<ProductResource> listAll() {
        List<ProductResource> products = new ArrayList<ProductResource>();
        for (Product model : productRepository.findAll()) {
            products.add(productResourceAssembler.toResource(model));
        }
        return products;
    }

}
// end::ProductResourceService[]
