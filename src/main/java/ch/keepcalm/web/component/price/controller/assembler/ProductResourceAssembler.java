package ch.keepcalm.web.component.price.controller.assembler;

import ch.keepcalm.web.component.price.controller.ProductController;
import ch.keepcalm.web.component.price.model.Product;
import ch.keepcalm.web.component.price.resource.ProductResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProductResourceAssembler extends ResourceAssemblerSupport<Product, ProductResource> {

    public ProductResourceAssembler() {
        super(ProductController.class, ProductResource.class);
    }

    @Override
    public ProductResource toResource(Product product) {
        ProductResource productResource = createResourceWithId(product.getId(), product);
        productResource.setProduct(product);

        return productResource;
    }
}
