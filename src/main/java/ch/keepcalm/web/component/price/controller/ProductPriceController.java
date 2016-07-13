package ch.keepcalm.web.component.price.controller;

import ch.helsana.services.spezialfunktionen.tarif.v2.BerechnePraemieBusinessFaultMessage;
import ch.helsana.services.spezialfunktionen.tarif.v2.BerechnePraemieSystemFaultMessage;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemierequest.BerechnePraemieRequest;
import ch.keepcalm.web.component.price.exception.BusinessException;
import ch.keepcalm.web.component.price.exception.SystemException;
import ch.keepcalm.web.component.price.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/products", produces = "application/hal+json")
public class ProductPriceController {

    @Autowired
    private PriceService priceService;

    @Autowired
    public void setPriceService(PriceService priceService) {
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
}
