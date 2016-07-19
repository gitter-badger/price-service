package ch.keepcalm.web.component.price.controller;

import ch.helsana.services.spezialfunktionen.tarif.v2.BerechneBesterPreisBusinessFaultMessage;
import ch.helsana.services.spezialfunktionen.tarif.v2.BerechneBesterPreisSystemFaultMessage;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnebesterpreisrequest.BerechneBesterPreisRequest;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnebesterpreisrequest.Person;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnebesterpreisrequest.ProduktListType;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnebesterpreisrequest.Versicherungsvertrag;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnebesterpreisrequest.Vertragsbaustein;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnebesterpreisresponse.BerechneBesterPreisResponse;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnebesterpreisresponse.Preis;
import ch.keepcalm.web.component.price.controller.assembler.CustomerResourceAssembler;
import ch.keepcalm.web.component.price.controller.assembler.ProductResourceAssembler;
import ch.keepcalm.web.component.price.converter.CalendarConverter;
import ch.keepcalm.web.component.price.model.Customer;
import ch.keepcalm.web.component.price.model.Product;
import ch.keepcalm.web.component.price.resource.ProductListResource;
import ch.keepcalm.web.component.price.resource.ProductResource;
import ch.keepcalm.web.component.price.service.CustomerService;
import ch.keepcalm.web.component.price.service.PriceService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
    private PriceService priceService;
    @Autowired
    private CustomerResourceAssembler customerResourceAssembler;
    @Autowired
    private ProductResourceAssembler productResourceAssembler;

    @Autowired
    public void setCustomerService(CustomerService customerService, CustomerResourceAssembler customerResourceAssembler, ProductResourceAssembler productResourceAssembler, PriceService priceService) {
        this.customerService = customerService;
        this.customerResourceAssembler = customerResourceAssembler;
        this.productResourceAssembler = productResourceAssembler;
        this.priceService = priceService;
    }


    /**
     * Add a product to a customer
     *
     * @param product
     * @param id
     * @return
     */
    @RequestMapping(
            value = "{id}/products",
            method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResource addProductToCustomer(@RequestBody Product product, @PathVariable int id) {
        Customer customer = customerService.getCustomer(id);
        if (customer != null) {
            Preis preis = bestPrice(product, customer);// TODO: 17/07/16 calculate price also
            product.setPrice(preis.getNettoPreis());
            customer.getProducts().add(product);
            customerService.updateCustmer(customer);
            return productToResource(product);
        }
        return productToResource(new Product()); // TODO: 15.07.2016 not nice solution
    }

    private Preis bestPrice(Product product, Customer customer) {
        // FIXME: 18.07.2016 QuickWin
        Preis preis = null;
        try {
            // TODO: 19.07.2016 dummy impl
            BerechneBesterPreisRequest request = new BerechneBesterPreisRequest();
            request.withAlleMarken(false);
            request.withPerson(
                    new Person()
                            .withGeburtsdatum(CalendarConverter.dateToXMLGregorianCalendar(new DateTime(1975, 9, 27, 0, 0, 0, 0).toDate()))
                            .withGeschlecht("1")
                            .withId("1")
                            .withProduktList(new ProduktListType()
                                    .withProdukt(new Vertragsbaustein()
                                            .withFranchise(product.getFranchise())
                                            .withProduktId(product.getProductNumber())
                                            .withUnfall(product.getUnfall()))))
                    .withVertrag(new Versicherungsvertrag()
                            .withGemeindeNummer(customer.getAddress().getMunicipality_nr())
                            .withMarke("H")
                            .withMarke("P")
                            .withMarke("S")
                            .withMarke("A")
                            .withPostleitzahl(customer.getAddress().getPostal_code())
                            .withPostleitzahlZusatz(customer.getAddress().getPostal_code_addition())
                            .withVertragsbeginn(CalendarConverter.dateToXMLGregorianCalendar(customer.getDateOfBirth())));

            BerechneBesterPreisResponse response = priceService.berechneBesterPreis(request);
             preis = response.getProduktList().getProdukt().get(0).getPreis();// TODO: 19.07.2016 get one product
            return preis;

        } catch (BerechneBesterPreisBusinessFaultMessage berechneBesterPreisBusinessFaultMessage) {
            berechneBesterPreisBusinessFaultMessage.printStackTrace();
        } catch (BerechneBesterPreisSystemFaultMessage berechneBesterPreisSystemFaultMessage) {
            berechneBesterPreisSystemFaultMessage.printStackTrace();
        }
        return preis;
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


    @RequestMapping(
            value = "{id}/products/{productId}",
            method = RequestMethod.GET,
            produces = "application/json; charset=utf-8")
    @ResponseStatus(HttpStatus.FOUND)
    public ProductResource getProductFromCustomer(@PathVariable int id, @PathVariable int productId) {
        Customer customer = customerService.getCustomer(id);
        if (customer != null) {
            if (customer.getProducts() != null) {
                return productToResource(customer.getProducts().get(productId - 1)); // List start on 0
            }
        }
        return productToResource(null); // TODO: 15.07.2016 not nice solution
    }


    /**
     * @param products
     * @return
     */
    private ProductListResource productToResource(List<Product> products, int id) {
        ProductListResource productListResource = new ProductListResource();

        List<ProductResource> productResources = productResourceAssembler.toResources(products);
        productListResource.setProductResourceList(productResources);

        // TODO: 17/07/16 http://localhost:8080/api/customers/1/products
        Link productsLink = new Link(linkTo(CustomerAggregateController.class)
                .slash(id)
                .slash("products").toUriComponentsBuilder().build().toUriString(), "list_products");
        productListResource.add(productsLink);

        Link createProductLink = new Link(linkTo(CustomerAggregateController.class)
                .slash(id)
                .slash("products").toUriComponentsBuilder().build().toUriString(), "create_product");
        productListResource.add(createProductLink);


        return productListResource;
    }


    /**
     * @param product
     * @return
     */
    private ProductResource productToResource(Product product) {
        return  productResourceAssembler.toResource(product);
    }


}
