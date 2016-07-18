package ch.keepcalm.web.component.price.controller;

import ch.keepcalm.web.component.price.controller.assembler.CustomerResourceAssembler;
import ch.keepcalm.web.component.price.controller.assembler.ProductResourceAssembler;
import ch.keepcalm.web.component.price.model.Customer;
import ch.keepcalm.web.component.price.model.Product;
import ch.keepcalm.web.component.price.resource.ProductListResource;
import ch.keepcalm.web.component.price.resource.ProductResource;
import ch.keepcalm.web.component.price.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/api/customers", produces = "application/hal+json")
public class CustomerAggregateController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerResourceAssembler customerResourceAssembler;
    @Autowired
    private ProductResourceAssembler productResourceAssembler;

    @Autowired
    public void setCustomerService(CustomerService customerService, CustomerResourceAssembler customerResourceAssembler, ProductResourceAssembler productResourceAssembler) {
        this.customerService = customerService;
        this.customerResourceAssembler = customerResourceAssembler;
        this.productResourceAssembler = productResourceAssembler;
    }


    /**
     * Get all products form customer
     *
     * @param id
     * @return
     */
    @RequestMapping(
            value = "{id}/products",
            method = RequestMethod.GET,
            produces = "application/json; charset=utf-8")
    @ResponseStatus(HttpStatus.FOUND)
    public ProductListResource getProductsFromCustomer(@PathVariable int id) {
        Customer customer = customerService.getCustomer(id);
        if (customer != null) {
            return productToResource(customer.getProducts(), id);
        }
        return productToResource(new ArrayList<Product>(), 0); // TODO: 15.07.2016 not nice solution
    }

    /**
     * @param products
     * @return
     */
    private ProductListResource productToResource(List<Product> products, int id) {
        ProductListResource productListResource = new ProductListResource();

       // productListResource.add(linkTo(CustomerAggregateController.class).withSelfRel());
        List<ProductResource> productResources = productResourceAssembler.toResources(products);
        productListResource.setProductResourceList(productResources);

            // TODO: 17/07/16 http://localhost:8080/api/customers/1/products
            Link productsLink = new Link(linkTo(CustomerAggregateController.class)
                    .slash(id)
                    .slash("products").toUriComponentsBuilder().build().toUriString(), "products");
         productListResource.add(productsLink);



        return productListResource;
    }


    /**
     *
     * @param product
     * @return
     */
    private ProductResource productToResource(Product product) {
        return productResourceAssembler.toResource(product);
    }



}
