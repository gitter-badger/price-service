package ch.keepcalm.web.component.price.controller;

import ch.keepcalm.web.component.price.controller.assembler.ProductResourceAssembler;
import ch.keepcalm.web.component.price.model.Product;
import ch.keepcalm.web.component.price.resource.ProductListResource;
import ch.keepcalm.web.component.price.resource.ProductResource;
import ch.keepcalm.web.component.price.service.PriceService;
import ch.keepcalm.web.component.price.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/products", produces = "application/hal+json")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private PriceService priceService;
    @Autowired
    private ProductResourceAssembler productResourceAssembler;

    @Autowired
    public void setProductService(ProductService productService, ProductResourceAssembler productResourceAssembler, PriceService priceService) {
        this.productService = productService;
        this.productResourceAssembler = productResourceAssembler;
        this.priceService = priceService;
    }

    /**
     * @return all product
     */
    @RequestMapping(method = {RequestMethod.GET})
    public ResponseEntity getProducts() {
        ProductListResource productListResource = productToResource(productService.getProducts());
        return new ResponseEntity<ProductListResource>(productListResource, HttpStatus.FOUND);
    }

    /**
     * @param id
     * @return a product
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity getProduct(@PathVariable int id) {
        ProductResource productResource = productToResource(productService.getProductById(id));
        return new ResponseEntity<ProductResource>(productResource, HttpStatus.FOUND);
    }

    /**
     * @param products
     * @return
     */
    private ProductListResource productToResource(List<Product> products) {
        ProductListResource productListResource = new ProductListResource();
        productListResource.add(linkTo(methodOn(ProductController.class).getProducts()).withSelfRel());
        List<ProductResource> productResources = productResourceAssembler.toResources(products);
        productListResource.setProductResourceList(productResources);
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
