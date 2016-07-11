package ch.helsana.web.priceservice.api.product;

import ch.helsana.web.priceservice.model.Product;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * Created by marcelwidmer on 10/07/16.
 */
@Component
public class ProductResourceAssembler extends ResourceAssemblerSupport<Product, ProductResource> {
    /**
     * Constructor
     */
    public ProductResourceAssembler() {
        super(ProductController.class, ProductResource.class);
    }

    /**
     * Convert domain product to resource product
     *
     * @param product
     * @return
     */
    @Override
    public ProductResource toResource(Product product) {
        ProductResource productResource = createResourceWithId(product.getId(), product); // adds a "self" link



        // TODO: copy properties from product to productResource
        productResource.setProductId(product.getProductId());
        productResource.setDescription(product.getDescription());
        productResource.setUnfall(product.getUnfall());
        productResource.setFranchise(product.getFranchise());
        productResource.setDrittesKin(product.getDrittesKind());
        productResource.setDoctor(product.getDoctor());
        productResource.setPrice(product.getPrice());


        return productResource;
    }
}

