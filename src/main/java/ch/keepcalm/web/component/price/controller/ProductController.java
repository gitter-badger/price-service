package ch.keepcalm.web.component.price.controller;

import ch.keepcalm.web.component.price.controller.assembler.ProductResourceAssembler;
import ch.keepcalm.web.component.price.model.Product;
import ch.keepcalm.web.component.price.resource.ProductListResource;
import ch.keepcalm.web.component.price.resource.ProductResource;
import ch.keepcalm.web.component.price.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/product", produces = "application/hal+json")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductResourceAssembler productResourceAssembler;
    @Autowired
    public void setProductService(ProductService productService, ProductResourceAssembler productResourceAssembler) {
        this.productService = productService;
        this.productResourceAssembler = productResourceAssembler;
    }


    /**
     * @param customerId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ProductListResource getProductByCustomerId(@RequestParam("customerId") int customerId) {
        return productToResource(productService.getProductByCustomerId(customerId), customerId);

    }

    /**
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ProductResource getProductById(@PathVariable int id) {
        Product product = productService.getProductById(id);
        return productToResource(product);

    }

    /**
     * @param productResources
     * @param customerId
     * @return
     */
    private ProductListResource productToResource(List<Product> productResources, int customerId) {
        ProductListResource productListResource = new ProductListResource();
        productListResource.add(linkTo(methodOn(ProductController.class).getProductByCustomerId(customerId)).withSelfRel());
        List<ProductResource> customerResources = productResourceAssembler.toResources(productResources);
        productListResource.setProductResourceList(customerResources);
        return productListResource;
    }

    /**
     * @param product
     * @return
     */
    private ProductResource productToResource(Product product) {
        return productResourceAssembler.toResource(product);
    }
}
