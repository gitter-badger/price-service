package ch.helsana.web.component.service.price.controller;

import ch.helsana.services.spezialfunktionen.tarif.v2.BerechnePraemieBusinessFaultMessage;
import ch.helsana.services.spezialfunktionen.tarif.v2.BerechnePraemieSystemFaultMessage;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnebesterpreisrequest.BerechneBesterPreisRequest;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnebesterpreisresponse.BerechneBesterPreisResponse;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemierequest.BerechnePraemieRequest;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemieresponse.BerechnePraemieResponse;
import ch.helsana.services.spezialfunktionen.tarif.v2.filtereprodukterequest.FiltereProdukteRequest;
import ch.helsana.services.spezialfunktionen.tarif.v2.filtereprodukteresponse.FiltereProdukteResponse;
import ch.helsana.web.component.service.price.exception.BusinessException;
import ch.helsana.web.component.service.price.exception.RestException;
import ch.helsana.web.component.service.price.exception.SystemException;
import ch.helsana.web.component.service.price.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by hkesq on 05.07.2016.
 * This Class would be responsible for Mapping from Request to internal Datamodel (and backwards),
 * for calling Backend-Services and handling Backend-Exceptions
 * So it decouples the WSDL-generated Classes from the internal Classes - for when the former changes,
 * nothing or only the mapping has to be changed
 */
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
    public BerechneBesterPreisResponse berechneBesterPreis(BerechneBesterPreisRequest parameters) {
        // TODO: 07.07.2016 implement me !!
        return null;
    }

    /**
     * https://tools.ietf.org/html/rfc7231#section-8.1.3
     * <p>
     * POST because a new price is created.
     *
     * @param parameters
     * @return
     * @throws RestException
     */
    @RequestMapping(
            value = "/price",
            method = {RequestMethod.POST, RequestMethod.GET}
    )
    public ResponseEntity berechnePraemie(BerechnePraemieRequest parameters) throws Exception {
        BerechnePraemieResponse response = null;
        ResponseEntity responseEntity;
        try {
            response = priceService.berechnePraemie(parameters);
            responseEntity = new ResponseEntity(response.getPreis(), HttpStatus.ACCEPTED);
            return responseEntity;
        } catch (BerechnePraemieSystemFaultMessage systemFaultMessage) {
            throw new SystemException("System business exception : ", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (BerechnePraemieBusinessFaultMessage businessFaultMessage) {
            throw new BusinessException("Price business exception : ", HttpStatus.BAD_REQUEST);
        } finally {
           throw new SystemException("System business exception : ", HttpStatus.BAD_REQUEST);
           // throw new BusinessException("Price business exception.... : ", HttpStatus.BAD_REQUEST);

        }


    }


    @RequestMapping(
            value = "/filter",
            method = {RequestMethod.GET}
    )
    public FiltereProdukteResponse filtereProdukte(FiltereProdukteRequest parameters) {
        // TODO: 07.07.2016 implement me !!
        return null;
    }
}
