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
import ch.keepcalm.web.component.price.exception.BusinessException;
import ch.keepcalm.web.component.price.exception.SystemException;
import ch.keepcalm.web.component.price.model.Product;
import ch.keepcalm.web.component.price.resource.ProductResource;
import ch.keepcalm.web.component.price.service.PriceService;
import ch.keepcalm.web.component.price.service.ProductService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/products", produces = "application/hal+json")
public class ProductPriceController {

    @Autowired
    private ProductService productService;
    @Autowired
    private PriceService priceService;
    @Autowired
    private ProductResourceAssembler productResourceAssembler;
    @Autowired
    public void setPriceService(PriceService priceService, ProductService productService, ProductResourceAssembler productResourceAssembler) {
        this.priceService = priceService;
        this.productService = productService;
        this.productResourceAssembler = productResourceAssembler;
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


    // TODO: 14.07.2016
    /**
     * @param id
     * @return a product
     */
    @RequestMapping(
            value = "{id}/computeprice",
            method = {RequestMethod.PUT, RequestMethod.GET},
            produces = "application/json; charset=utf-8"
    )
    @Deprecated
    public ProductResource getPrice(@PathVariable int id) {

        Product product = productToResource(productService.getProductById(id)).getProduct();

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
            productService.updateProduct(product);
            return productToResource(productService.getProductById(product.getId()));
        } catch (BerechnePraemieSystemFaultMessage berechnePraemieSystemFaultMessage) {
            berechnePraemieSystemFaultMessage.printStackTrace();
        } catch (BerechnePraemieBusinessFaultMessage berechnePraemieBusinessFaultMessage) {
            berechnePraemieBusinessFaultMessage.printStackTrace();
        }

        return  productToResource(productService.getProductById(id));
    }
    /**
     * @param product
     * @return
     */
    private ProductResource productToResource(Product product) {
        return productResourceAssembler.toResource(product);
    }

}
