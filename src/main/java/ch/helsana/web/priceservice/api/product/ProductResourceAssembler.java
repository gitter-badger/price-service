package ch.helsana.web.priceservice.api.product;

import ch.helsana.web.priceservice.model.Product;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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
        // TODO: 11.07.2016  exception handling here ??!!!
        try {
            Link products = linkTo(methodOn(ProductController.class).price(null)).withRel("price");
            productResource.add(products);
        } catch (Exception e) {
            e.printStackTrace();
        }

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

