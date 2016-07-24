package ch.keepcalm.web.component.price.controller.assembler;

import ch.keepcalm.web.component.price.controller.CustomerAggregateController;
import ch.keepcalm.web.component.price.controller.CustomerController;
import ch.keepcalm.web.component.price.model.Product;
import ch.keepcalm.web.component.price.resource.ProductResource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class ProductResourceAssembler extends ResourceAssemblerSupport<Product, ProductResource> {

    public ProductResourceAssembler() {
        super(CustomerController.class, ProductResource.class);
    }

    @Override
    public ProductResource toResource(Product product) {
        // api/customer/5>;rel="self" // TODO: 22/07/16 missing products/{id} 
        ProductResource productResource = createResourceWithId(product.getId(), product);
        productResource.setProduct(product);
        Link updateProductPriceLink = new Link(linkTo(CustomerAggregateController.class)
                .slash(product.getId())
                .slash("products").slash(product.getId()).toUriComponentsBuilder().build().toUriString(), "update_price");
        productResource.add(updateProductPriceLink);
/*

        // api/customers/1/products/1
       Link selfLink = new Link(linkTo(CustomerAggregateController.class)
                .slash("products").slash(product.getId()).toUriComponentsBuilder().build().toUriString(), "self");
        productResource.add(selfLink);

        Link selfLink1 =(linkTo(methodOn(CustomerAggregateController.class).getProductFromCustomer(id, product.getId())).withSelfRel());
        productResource.add(selfLink1);

       Link updateProductPriceLink = new Link(linkTo(CustomerAggregateController.class)
                .slash(product.getId())
                .slash("products").slash(product.getId()).toUriComponentsBuilder().build().toUriString(), "update_price");
        productResource.add(updateProductPriceLink);
        Link selfLink1 = (linkTo(methodOn(CustomerAggregateController.class).getProductsFromCustomer(0)).withSelfRel());

        Link slef = productResource.getLink("slef");
        Link updateProductPriceLink = new Link(linkTo(CustomerAggregateController.class)
                .slash(product.getId())
                .slash("products").slash(product.getId()).toUriComponentsBuilder().build().toUriString(), "update_price");

*/
        return productResource;
    }

}
