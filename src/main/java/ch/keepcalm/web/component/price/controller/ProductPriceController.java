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

    /**
     * @param id
     * @return a product
     */
    @RequestMapping(
            value = "{id}/price",
            method = RequestMethod.PUT,
            produces = "application/json; charset=utf-8"
    )
    public ProductResource getPrice(@PathVariable int id) {
        // TODO: 13.07.2016  HACK
        Product product = productToResource(productService.getProductById(id)).getProduct();

        BerechnePraemieRequest request = new BerechnePraemieRequest();


        PersonListType personListType = new PersonListType();

        Person person = new Person();
        person.setId("1");
        DateTime birtday = new DateTime(1975, 9, 27, 0, 0, 0, 0); // TODO: 08.07.2016  UI
        person.setGeburtsdatum(CalendarConverter.dateToXMLGregorianCalendar(birtday.toDate()));
        person.setGeschlecht("1");  // TODO: 08.07.2016  UI


        Vertragsbaustein produkt1 = new Vertragsbaustein()
                .withFranchise(product.getFranchise())
                .withProduktId(product.getProductNumber())
                .withDrittesKind(product.getDrittesKind())
                .withUnfall(product.getUnfall());

        ProduktListType produktListType = new ProduktListType();
        produktListType.getProdukt().add(produkt1);

        person.setProduktList(produktListType);
        personListType.getPerson().add(person);
/*
        ProduktListType produktListType = new ProduktListType()
                .withProdukt(new Vertragsbaustein()
                        .withFranchise(product.getFranchise())
                        .withProduktId(product.getProductNumber())
                        .withDrittesKind(product.getDrittesKind())
                        .withUnfall(product.getUnfall()));
*/



       // person.setProduktList(produktListType);



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
            ProductResource productResource = productToResource(productService.getProductById(id));
            productResource.getProduct().setPrice(preis.getBruttoPreis());
            return productResource;
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
