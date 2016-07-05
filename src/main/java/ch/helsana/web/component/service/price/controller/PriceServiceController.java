package ch.helsana.web.component.service.price.controller;

import ch.sbi.services.system.productengine.tarif.v2.berechnebesterpreisrequest.BerechneBesterPreisRequest;
import ch.sbi.services.system.productengine.tarif.v2.berechnebesterpreisresponse.BerechneBesterPreisResponse;
import ch.sbi.services.system.productengine.tarif.v2.berechnepraemierequest.BerechnePraemieRequest;
import ch.sbi.services.system.productengine.tarif.v2.berechnepraemieresponse.BerechnePraemieResponse;
import ch.sbi.services.system.productengine.tarif.v2.filtereprodukterequest.FiltereProdukteRequest;
import ch.sbi.services.system.productengine.tarif.v2.filtereprodukteresponse.FiltereProdukteResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by hkesq on 05.07.2016.
 *  This Class would be responsible for Mapping from Request to internal Datamodel (and backwards),
 *  for calling Backend-Services and handling Backend-Exceptions
 *  So it decouples the WSDL-generated Classes from the internal Classes - for when the former changes,
 *  nothing or only the mapping has to be changed
 */
@RestController
@RequestMapping("/api")
public class PriceServiceController {


    public BerechneBesterPreisResponse berechneBesterPreis(BerechneBesterPreisRequest parameters) {
        return null;
    }

    public BerechnePraemieResponse berechnePraemie(BerechnePraemieRequest parameters) {
        return null;
    }

    public FiltereProdukteResponse filtereProdukte(FiltereProdukteRequest parameters) {
        return null;
    }
}
