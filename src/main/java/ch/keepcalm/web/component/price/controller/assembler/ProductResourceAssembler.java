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

        /*ProductResource productResource = createResourceWithId(product.getId(), product);
        Link customerLink = new Link(linkTo(ProductController.class).slash(product.getId()).slash("customers").toUriComponentsBuilder().build().toUriString(), "customers");
        productResource.add(customerLink);*/
 //
            // TODO http://localhost:8080/api/customers/1/products/1
/*
        resource.add(linkTo(methodOn(CustomerAggregateController.class).getProductsFromCustomer(product.getId())).withSelfRel());
        Link productsLink = new Link(linkTo(CustomerAggregateController.class)
                    .slash(customer.getId())
                    .slash("products").toUriComponentsBuilder().build().toUriString(), "products");
            productResource.add(productsLink);
*/
        //productResource.add(linkTo(methodOn(CustomerAggregateController.class).getProductsFromCustomer(product.getId())));
       //productResource.add(linkTo(methodOn(CustomerController.class).getCustomer(2)).withRel("products"));
/*
        Link customerLink = new Link(linkTo(ProductController.class).slash(product.getId()).slash("customers").toUriComponentsBuilder().build().toUriString(), "product");
        productResource.add(customerLink);
*/
        productResource.setProduct(product);
        return productResource;
    }


}
