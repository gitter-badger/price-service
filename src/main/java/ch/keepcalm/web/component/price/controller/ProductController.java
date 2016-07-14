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
    public void setProductService(ProductService productService, ProductResourceAssembler productResourceAssembler,  PriceService priceService) {
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
        Product newProduct = productService.saveProduct(product);

        // TODO: 13.07.2016  HACK
        Product updateProduct = productToResource(productService.getProductById(newProduct.getId())).getProduct();

        BerechnePraemieRequest request = new BerechnePraemieRequest();
        PersonListType personListType = new PersonListType();

        Person person = new Person();
        person.setId("1");
        DateTime birtday = new DateTime(1975, 9, 27, 0, 0, 0, 0); // TODO: 08.07.2016  UI
        person.setGeburtsdatum(CalendarConverter.dateToXMLGregorianCalendar(birtday.toDate()));
        person.setGeschlecht("1");  // TODO: 08.07.2016  UI

        Vertragsbaustein produkt1 = new Vertragsbaustein()
                .withFranchise(updateProduct.getFranchise())
                .withProduktId(updateProduct.getProductNumber())
                .withDrittesKind(updateProduct.getDrittesKind())
                .withUnfall(updateProduct.getUnfall());

        ProduktListType produktListType = new ProduktListType();
        produktListType.getProdukt().add(produkt1);

        person.setProduktList(produktListType);
        personListType.getPerson().add(person);

        // TODO: 08.07.2016  UI
        Versicherungsvertrag versicherungsvertrag = new Versicherungsvertrag();
        versicherungsvertrag.setGemeindeNummer("199");  // TODO: 08.07.2016 UI
        versicherungsvertrag.setPostleitzahl("8307");  // TODO: 08.07.2016 UI
        versicherungsvertrag.setPostleitzahlZusatz("00");  // TODO: 08.07.2016 UI
        DateTime vertragsbeginn = new DateTime(2016, 1, 7, 0, 0, 0, 0); //2016-07-01
        versicherungsvertrag.setVertragsbeginn(CalendarConverter.dateToXMLGregorianCalendar(vertragsbeginn.toDate()));

        request.setPersonList(personListType);

        request.setCorrelationId("32424");
        request.setVersicherungsvertrag(versicherungsvertrag);


        try {
            Preis preis = priceService.berechnePraemie(request).getPreis();
            ProductResource productResource = productToResource(productService.getProductById(newProduct.getId()));
            newProduct.setPrice(preis.getBruttoPreis());
            productService.updateProduct(newProduct);   // TODO: 14.07.2016 update product in DB with price !!!??!!!
            productResource.getProduct().setPrice(preis.getBruttoPreis());
            return productResource;
        } catch (BerechnePraemieSystemFaultMessage berechnePraemieSystemFaultMessage) {
            berechnePraemieSystemFaultMessage.printStackTrace();
        } catch (BerechnePraemieBusinessFaultMessage berechnePraemieBusinessFaultMessage) {
            berechnePraemieBusinessFaultMessage.printStackTrace();
        }
        return  productToResource(productService.getProductById(newProduct.getId()));
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
