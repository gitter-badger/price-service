package ch.helsana.web.component.service.price.controller;

import ch.helsana.web.component.service.price.exception.BusinessException;
import ch.helsana.web.component.service.price.exception.RestException;
import ch.helsana.web.component.service.price.exception.SystemException;
import ch.helsana.web.component.service.price.service.PriceService;
import ch.sbi.services.system.productengine.tarif.v2.berechnebesterpreisrequest.BerechneBesterPreisRequest;
import ch.sbi.services.system.productengine.tarif.v2.berechnebesterpreisresponse.BerechneBesterPreisResponse;
import ch.sbi.services.system.productengine.tarif.v2.berechnepraemierequest.BerechnePraemieRequest;
import ch.sbi.services.system.productengine.tarif.v2.berechnepraemieresponse.BerechnePraemieResponse;
import ch.sbi.services.system.productengine.tarif.v2.filtereprodukterequest.FiltereProdukteRequest;
import ch.sbi.services.system.productengine.tarif.v2.filtereprodukteresponse.FiltereProdukteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by hkesq on 05.07.2016.
 *  This Class would be responsible for Mapping from Request to internal Datamodel (and backwards),
 *  for calling Backend-Services and handling Backend-Exceptions
 *  So it decouples the WSDL-generated Classes from the internal Classes - for when the former changes,
 *  nothing or only the mapping has to be changed
 */
@RestController
@RequestMapping("/product")
public class PriceServiceController {


    private PriceService priceService;
    @Autowired
    public void setPriceService(PriceService priceService) {
        this.priceService = priceService;
    }


    @RequestMapping(
            value = "/price",
            method = {RequestMethod.GET}
    )
    public ResponseEntity berechnePraemie(BerechnePraemieRequest parameters) throws RestException {
        BerechnePraemieResponse response = null;
        try {
            response = priceService.berechnePraemie(parameters);
        } catch (BusinessException e) {
            throw new RestException("Price business exception : ", HttpStatus.BAD_REQUEST);

        } catch (SystemException e) {
            throw new RestException("System business exception : ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        ResponseEntity responseEntity = new ResponseEntity(response.getPreis(), HttpStatus.ACCEPTED);
        return responseEntity;
    }










    @RequestMapping(
            value = "/best-price",
            method = {RequestMethod.GET}
    )
    public BerechneBesterPreisResponse berechneBesterPreis(BerechneBesterPreisRequest parameters) {
        return null;
    }


    @RequestMapping(
            value = "/filter",
            method = {RequestMethod.GET}
    )
    public FiltereProdukteResponse filtereProdukte(FiltereProdukteRequest parameters) {
        return null;
    }
}
