package ch.helsana.web.priceservice.service;

import ch.helsana.services.spezialfunktionen.tarif.v2.BerechneBesterPreisBusinessFaultMessage;
import ch.helsana.services.spezialfunktionen.tarif.v2.BerechneBesterPreisSystemFaultMessage;
import ch.helsana.services.spezialfunktionen.tarif.v2.BerechnePraemieBusinessFaultMessage;
import ch.helsana.services.spezialfunktionen.tarif.v2.BerechnePraemieSystemFaultMessage;
import ch.helsana.services.spezialfunktionen.tarif.v2.FiltereProdukteBusinessFaultMessage;
import ch.helsana.services.spezialfunktionen.tarif.v2.FiltereProdukteSystemFaultMessage;
import ch.helsana.services.spezialfunktionen.tarif.v2.TarifPortType;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnebesterpreisrequest.BerechneBesterPreisRequest;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnebesterpreisresponse.BerechneBesterPreisResponse;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemierequest.BerechnePraemieRequest;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemieresponse.BerechnePraemieResponse;
import ch.helsana.services.spezialfunktionen.tarif.v2.filtereprodukterequest.FiltereProdukteRequest;
import ch.helsana.services.spezialfunktionen.tarif.v2.filtereprodukteresponse.FiltereProdukteResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Marcel Widmer on 08.07.2016.
 */
// tag::PriceService[]
@Service
public class PriceService implements TarifPortType {
// end::PriceService[]

    // tag::portType[]
    @Resource
    TarifPortType tarifPortType;
    // end::portType[]

    // tag::implementPortTypeMethod[]
    @Override
    public BerechneBesterPreisResponse berechneBesterPreis(BerechneBesterPreisRequest parameters) throws BerechneBesterPreisBusinessFaultMessage, BerechneBesterPreisSystemFaultMessage {
         return  tarifPortType.berechneBesterPreis(parameters);
    }

    @Override
    public BerechnePraemieResponse berechnePraemie(BerechnePraemieRequest parameters) throws BerechnePraemieSystemFaultMessage, BerechnePraemieBusinessFaultMessage {
            return tarifPortType.berechnePraemie(parameters);
    }

    @Override
    public FiltereProdukteResponse filtereProdukte(FiltereProdukteRequest parameters) throws FiltereProdukteBusinessFaultMessage, FiltereProdukteSystemFaultMessage {
        return tarifPortType.filtereProdukte(parameters);
    }
    // end::implementPortTypeMethod[]
}