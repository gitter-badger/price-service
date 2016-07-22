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
import ch.keepcalm.web.component.price.controller.assembler.ProductResourceAssembler;
import ch.keepcalm.web.component.price.converter.CalendarConverter;
import ch.keepcalm.web.component.price.exception.BusinessException;
import ch.keepcalm.web.component.price.exception.SystemException;
import ch.keepcalm.web.component.price.model.Customer;
import ch.keepcalm.web.component.price.model.Product;
import ch.keepcalm.web.component.price.resource.ProductListResource;
import ch.keepcalm.web.component.price.resource.ProductResource;
import ch.keepcalm.web.component.price.service.CustomerService;
import ch.keepcalm.web.component.price.service.PriceService;
import ch.keepcalm.web.component.price.service.ProductService;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

import static ch.keepcalm.web.component.price.converter.UiConverter.convertGender;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/api/customers", produces = "application/hal+json")
public class CustomerAggregateController {

    @Autowired
    Environment environment;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProductService productService;
    @Autowired
    private PriceService priceService;
    @Autowired
    private ProductResourceAssembler productResourceAssembler;

    @Autowired
    public void setCustomerService(ProductService productService, CustomerService customerService, ProductResourceAssembler productResourceAssembler, PriceService priceService, Environment environment) {
        this.productService = productService;
        this.customerService = customerService;
        this.productResourceAssembler = productResourceAssembler;
        this.priceService = priceService;
        this.environment = environment;
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
    public ResponseEntity addProductToCustomer(@RequestBody Product product, @PathVariable int id) throws Exception {
        Customer customer = customerService.getCustomer(id);
        if (customer != null) {
            product.setPrice(new BigDecimal(00.00)); // TODO: 22/07/16 default value
            Product savedProduct = productService.saveProduct(product);
            customer.getProducts().add(savedProduct);
            customerService.updateCustmer(customer);
            ProductResource productResource = productToResource(savedProduct, customer);
            return new ResponseEntity<ProductResource>(productResource, HttpStatus.CREATED);
        }
        return new ResponseEntity<ProductResource>(HttpStatus.INTERNAL_SERVER_ERROR);
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
    public ResponseEntity getProductsFromCustomer(@PathVariable int id) {
        Customer customer = customerService.getCustomer(id);
        if (customer != null) {
            ProductListResource productListResource = productToResource(customer.getProducts(), id);
            return new ResponseEntity<ProductListResource>(productListResource, HttpStatus.FOUND);
        }
        return new ResponseEntity<ProductListResource>(HttpStatus.NOT_FOUND);
    }


    /**
     * @param id
     * @param productId
     * @return
     */
    @RequestMapping(
            value = "{id}/products/{productId}",
            method = RequestMethod.GET,
            produces = "application/json; charset=utf-8")
    public ResponseEntity getProductFromCustomer(@PathVariable int id, @PathVariable int productId) {
        Customer customer = customerService.getCustomer(id);
        if (customer != null) {
            if (customer.getProducts() != null) {
                ProductResource productResource = productToResource(customer.getProducts().get(productId - 1));// List start on 0
                return new ResponseEntity<ProductResource>(productResource, HttpStatus.FOUND);
            }
        }
        return new ResponseEntity<ProductResource>(HttpStatus.NOT_FOUND);
    }

    /**
     * @param id
     * @param productId
     * @return
     */
    @RequestMapping(
            value = "{id}/products/{productId}",
            method = RequestMethod.PATCH,
            produces = "application/json; charset=utf-8")
    public ResponseEntity updatePricesOnProductFromCustomer(@PathVariable int id, @PathVariable int productId) throws Exception {
        Customer customer = customerService.getCustomer(id);
        if (customer != null) {
            if (customer.getProducts() != null) {

                Product product = customer.getProducts().get(productId - 1);
               if (!environment.acceptsProfiles("junit")) {
                   Preis preis = getBestPrice(product, customer);   // service call
                   product.setPrice(preis.getNettoPreis());
                }

                productService.updateProduct(product);


                customerService.updateCustmer(customer);
                ProductResource productResource = productToResource(product);
                return new ResponseEntity<ProductResource>(productResource, HttpStatus.OK);
            }
        }

        return new ResponseEntity<ProductResource>(HttpStatus.INTERNAL_SERVER_ERROR); // TODO: 21.07.2016  excpetion message ..
    }


    /**
     * @param product
     * @param customer
     * @return
     * @throws Exception
     */
    private Preis getBestPrice(Product product, Customer customer) throws Exception {
        // FIXME: 18.07.2016 QuickWin
        Preis preis = null;
        try {
            // TODO: 19.07.2016 dummy impl
            BerechneBesterPreisRequest request = new BerechneBesterPreisRequest();
            request.withAlleMarken(false);
            request.withPerson(
                    new Person()
                            .withGeburtsdatum(CalendarConverter.dateToXMLGregorianCalendar(customer.getDateOfBirth()))
                            .withGeschlecht(convertGender(customer.getGender()))
                            .withId(String.valueOf(customer.getId()))
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
                            .withVertragsbeginn(CalendarConverter.dateToXMLGregorianCalendar(new LocalDate().plusMonths(1).dayOfMonth().withMinimumValue().toDate())));

            BerechneBesterPreisResponse response = priceService.berechneBesterPreis(request);
            preis = response.getProduktList().getProdukt().get(0).getPreis();// TODO: 19.07.2016 get one product
            return preis;

        } catch (BerechneBesterPreisBusinessFaultMessage berechneBesterPreisBusinessFaultMessage) {
            throw new BusinessException("Business exception : ", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (BerechneBesterPreisSystemFaultMessage berechneBesterPreisSystemFaultMessage) {
            throw new SystemException("System exception : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param product
     * @return
     */
    private ProductResource productToResource(Product product) {
        return productResourceAssembler.toResource(product);

    }

    private ProductResource productToResource(Product product, Customer customer) {
        ProductResource productResource = new ProductResource();
        productResource.setProduct(product);

      /*  Link selfLink = new Link(linkTo(CustomerAggregateController.class)
                .slash(id)
                .slash("products").slash(product.getId()).toUriComponentsBuilder().build().toUriString(), "self");
        productResource.add(selfLink)*/;

        //getProductFromCustomer
      /*  Link linkToMethod = linkBuilderFactory.linkTo(parameter.getMethod(), new Object[0]).withSelfRel();
        UriComponents fromUriString = UriComponentsBuilder.fromUriString(linkToMethod.getHref()).build();*/

       /* Link selfLink1 =(linkTo(methodOn(CustomerAggregateController.class).getProductFromCustomer(id, product.getId())).withSelfRel());
        productResource.add(selfLink1);*/

        Link selfLink = new Link(linkTo(CustomerAggregateController.class)
                .slash(customer.getId())
                .slash("products").slash(product.getId()).toUriComponentsBuilder().build().toUriString(), "self");
        productResource.add(selfLink);


        // TODO: 17/07/16 http://localhost:8080/api/customers/1/products/1
        Link updateProductPriceLink = new Link(linkTo(CustomerAggregateController.class)
                .slash(customer.getId())
                .slash("products").slash(product.getId()).toUriComponentsBuilder().build().toUriString(), "update_price");
        productResource.add(updateProductPriceLink);
        return productResource;
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
        Link listProductsLink = new Link(linkTo(CustomerAggregateController.class)
                .slash(id)
                .slash("products").toUriComponentsBuilder().build().toUriString(), "list_products");
        productListResource.add(listProductsLink);

        Link createProductLink = new Link(linkTo(CustomerAggregateController.class)
                .slash(id)
                .slash("products").toUriComponentsBuilder().build().toUriString(), "create_product");
        productListResource.add(createProductLink);

        return productListResource;
    }

}
