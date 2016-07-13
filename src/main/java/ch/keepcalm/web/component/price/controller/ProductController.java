package ch.keepcalm.web.component.price.controller;

import ch.helsana.services.spezialfunktionen.tarif.v2.BerechnePraemieBusinessFaultMessage;
import ch.helsana.services.spezialfunktionen.tarif.v2.BerechnePraemieSystemFaultMessage;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemierequest.BerechnePraemieRequest;
import ch.keepcalm.web.component.price.controller.assembler.ProductResourceAssembler;
import ch.keepcalm.web.component.price.exception.BusinessException;
import ch.keepcalm.web.component.price.exception.SystemException;
import ch.keepcalm.web.component.price.model.Product;
import ch.keepcalm.web.component.price.resource.ProductListResource;
import ch.keepcalm.web.component.price.resource.ProductResource;
import ch.keepcalm.web.component.price.service.PriceService;
import ch.keepcalm.web.component.price.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/customers/products", produces = "application/hal+json")
public class ProductController {

    @Autowired
    private PriceService priceService; // TODO: 13.07.2016  refator ? use ProductService
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductResourceAssembler productResourceAssembler;

    @Autowired
    public void setProductService(ProductService productService, ProductResourceAssembler productResourceAssembler, PriceService priceService) {
        this.productService = productService;
        this.productResourceAssembler = productResourceAssembler;
        this.priceService = priceService;
    }


    /**
     * https://tools.ietf.org/html/rfc7231#section-8.1.3
     * <p>
     * POST because a new price is created.
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(
            value = "/price",
            method = {RequestMethod.POST},
            produces = "application/json; charset=utf-8"
    )
    @Deprecated
    public ResponseEntity price(@RequestBody BerechnePraemieRequest request) throws Exception {
        try {
            return new ResponseEntity(priceService.berechnePraemie(request).getPreis(), HttpStatus.ACCEPTED);
        } catch (BerechnePraemieSystemFaultMessage systemFaultMessage) {
            throw new SystemException("System business exception : ", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (BerechnePraemieBusinessFaultMessage businessFaultMessage) {
            throw new BusinessException("Price business exception : ", HttpStatus.BAD_REQUEST);
        }

    }


    /**
     * @param customerId
     * @return
     */
  /*  @RequestMapping(method = RequestMethod.GET)
    public ProductListResource getProductByCustomerId(@RequestParam("customerId") int customerId) {
        return productToResource(productService.getProductByCustomerId(customerId), customerId);

    }*/


    /**
     * @return all product
     */
    @RequestMapping(method = {RequestMethod.GET})
    public ProductListResource getProducts() {
        return productToResource(productService.getProducts());
    }

    /**
     * @param id
     * @return a product
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ProductResource getProduct(@PathVariable int id) {
        return productToResource(productService.getProductById(id));
    }


    /**
     * @param productResources
     * @param customerId
     * @return
     */
   /* private ProductListResource productToResource(List<Product> productResources, int customerId) {
        ProductListResource productListResource = new ProductListResource();
        productListResource.add(linkTo(methodOn(ProductController.class).getProductByCustomerId(customerId)).withSelfRel());
        List<ProductResource> customerResources = productResourceAssembler.toResources(productResources);
        productListResource.setProductResourceList(customerResources);
        return productListResource;
    }*/


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
