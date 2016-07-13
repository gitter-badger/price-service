package ch.helsana.web.priceservice.api;

import ch.helsana.services.spezialfunktionen.tarif.v2.BerechnePraemieBusinessFaultMessage;
import ch.helsana.services.spezialfunktionen.tarif.v2.BerechnePraemieSystemFaultMessage;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnebesterpreisrequest.BerechneBesterPreisRequest;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemierequest.BerechnePraemieRequest;
import ch.helsana.services.spezialfunktionen.tarif.v2.filtereprodukterequest.FiltereProdukteRequest;
import ch.keepcalm.web.component.price.exception.BusinessException;
import ch.keepcalm.web.component.price.exception.SystemException;
import ch.helsana.web.priceservice.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by marcelwidmer on 05.07.2016.
 * This Class would be responsible for Mapping from Request to internal Datamodel (and backwards),
 * for calling Backend-Services and handling Backend-Exceptions
 * So it decouples the WSDL-generated Classes from the internal Classes - for when the former changes,
 * nothing or only the mapping has to be changed.
 * <p>
 * price-service
 * - get product (service side configuration / config-server or own DB)
 * - create resource product/s
 * - create resource form entries (Plz/birthday/gender)
 * - compute result
 */
@Deprecated
@RestController
@RequestMapping("/product")
public class PriceServiceController {


    private PriceService priceService;

    @Autowired
    public void setPriceService(PriceService priceService) {
        this.priceService = priceService;
    }


    /**
     * https://tools.ietf.org/html/rfc7231#section-8.1.3
     * <p>
     * POST because a new best price is created.
     *
     * @param parameters
     * @return
     */
    @RequestMapping(
            value = "/best-price",
            method = {RequestMethod.POST}
    )
    public ResponseEntity berechneBesterPreis(@RequestBody BerechneBesterPreisRequest parameters) {
        // TODO: 07.07.2016 implement me !!
        return null;
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
    public ResponseEntity berechnePraemie(@RequestBody BerechnePraemieRequest request) throws Exception {
        try {
            return new ResponseEntity(priceService.berechnePraemie(request).getPreis(), HttpStatus.ACCEPTED);
        } catch (BerechnePraemieSystemFaultMessage systemFaultMessage) {
            throw new SystemException("System business exception : ", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (BerechnePraemieBusinessFaultMessage businessFaultMessage) {
            throw new BusinessException("Price business exception : ", HttpStatus.BAD_REQUEST);
        }

    }


    @RequestMapping(
            value = "/filter",
            method = {RequestMethod.GET}
    )
    public ResponseEntity filtereProdukte(@RequestBody FiltereProdukteRequest parameters) {
        // TODO: 07.07.2016 implement me !!
        return null;
    }
}