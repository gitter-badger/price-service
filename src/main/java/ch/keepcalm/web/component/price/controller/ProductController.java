package ch.keepcalm.web.component.price.controller;

import ch.helsana.services.spezialfunktionen.tarif.v2.BerechnePraemieBusinessFaultMessage;
import ch.helsana.services.spezialfunktionen.tarif.v2.BerechnePraemieSystemFaultMessage;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemierequest.BerechnePraemieRequest;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemierequest.Person;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemierequest.PersonListType;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemierequest.ProduktListType;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemierequest.Versicherungsvertrag;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemierequest.Vertragsbaustein;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemieresponse.Preis;
import ch.keepcalm.web.component.price.controller.assembler.ProductResourceAssembler;
import ch.keepcalm.web.component.price.converter.CalendarConverter;
import ch.keepcalm.web.component.price.model.Product;
import ch.keepcalm.web.component.price.resource.ProductListResource;
import ch.keepcalm.web.component.price.resource.ProductResource;
import ch.keepcalm.web.component.price.service.PriceService;
import ch.keepcalm.web.component.price.service.ProductService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    public ProductResource createProduct(@PathVariable int id) {
        return productToResource(productService.getProductById(id));
    }


    @RequestMapping(
            value = "",
            method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResource addProduct(@RequestBody Product product) {

        BerechnePraemieRequest request = new BerechnePraemieRequest();
        request.setPersonList(new PersonListType()
                .withPerson(new Person()
                        .withProduktList(new ProduktListType()
                                .withProdukt(new Vertragsbaustein()
                                        .withFranchise(product.getFranchise())
                                        .withProduktId(product.getProductNumber())
                                        .withDrittesKind(product.getDrittesKind())
                                        .withUnfall(product.getUnfall())))
                        .withGeburtsdatum(CalendarConverter.dateToXMLGregorianCalendar(new DateTime(1975, 9, 27, 0, 0, 0, 0).toDate()))
                        .withGeschlecht("1")
                        .withId("1")));

        request.setCorrelationId("32424");
        request.setVersicherungsvertrag(new Versicherungsvertrag()
                .withGemeindeNummer("199")
                .withPostleitzahl("8307")
                .withPostleitzahlZusatz("00")
                .withVertragsbeginn(CalendarConverter.dateToXMLGregorianCalendar(new DateTime(2016, 1, 7, 0, 0, 0, 0).toDate())));

        try {
            Preis preis = priceService.berechnePraemie(request).getPreis();
            product.setPrice(preis.getBruttoPreis());
            productService.saveProduct(product);
            return productToResource(productService.getProductById(product.getId()));
        } catch (BerechnePraemieSystemFaultMessage berechnePraemieSystemFaultMessage) {
            berechnePraemieSystemFaultMessage.printStackTrace();
        } catch (BerechnePraemieBusinessFaultMessage berechnePraemieBusinessFaultMessage) {
            berechnePraemieBusinessFaultMessage.printStackTrace();
        }
        return productToResource(productService.getProductById(product.getId()));
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
