package ch.helsana.web.component.service.price.service;

import ch.sbi.services.system.productengine.tarif.v2.BerechneMarkenPreiseBusinessFaultMessage;
import ch.sbi.services.system.productengine.tarif.v2.BerechneMarkenPreiseSystemFaultMessage;
import ch.sbi.services.system.productengine.tarif.v2.BerechnePraemieBusinessFaultMessage;
import ch.sbi.services.system.productengine.tarif.v2.BerechnePraemieSystemFaultMessage;
import ch.sbi.services.system.productengine.tarif.v2.FiltereProdukteBusinessFaultMessage;
import ch.sbi.services.system.productengine.tarif.v2.FiltereProdukteSystemFaultMessage;
import ch.sbi.services.system.productengine.tarif.v2.TarifPortType;
import ch.sbi.services.system.productengine.tarif.v2.berechnebesterpreisrequest.BerechneBesterPreisRequest;
import ch.sbi.services.system.productengine.tarif.v2.berechnebesterpreisresponse.BerechneBesterPreisResponse;
import ch.sbi.services.system.productengine.tarif.v2.berechnepraemierequest.BerechnePraemieRequest;
import ch.sbi.services.system.productengine.tarif.v2.berechnepraemieresponse.BerechnePraemieResponse;
import ch.sbi.services.system.productengine.tarif.v2.filtereprodukterequest.FiltereProdukteRequest;
import ch.sbi.services.system.productengine.tarif.v2.filtereprodukteresponse.FiltereProdukteResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hkesq on 23.11.2015.
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
    public BerechneBesterPreisResponse berechneBesterPreis(BerechneBesterPreisRequest parameters) {
        BerechneBesterPreisResponse response = null;
        try {
            response = tarifPortType.berechneBesterPreis(parameters);
        } catch (BerechneMarkenPreiseSystemFaultMessage berechneMarkenPreiseSystemFaultMessage) {
            berechneMarkenPreiseSystemFaultMessage.printStackTrace();
        } catch (BerechneMarkenPreiseBusinessFaultMessage berechneMarkenPreiseBusinessFaultMessage) {
            berechneMarkenPreiseBusinessFaultMessage.printStackTrace();
        }
        return response;
    }

    @Override
    public BerechnePraemieResponse berechnePraemie(BerechnePraemieRequest parameters) {
        BerechnePraemieResponse response = null;
        try {
            response = tarifPortType.berechnePraemie(parameters);
        } catch (BerechnePraemieSystemFaultMessage berechnePraemieSystemFaultMessage) {
            berechnePraemieSystemFaultMessage.printStackTrace();
        } catch (BerechnePraemieBusinessFaultMessage berechnePraemieBusinessFaultMessage) {
            berechnePraemieBusinessFaultMessage.printStackTrace();
        }
        return response;
    }

    @Override
    public FiltereProdukteResponse filtereProdukte(FiltereProdukteRequest parameters) {
        FiltereProdukteResponse response = null;
        try {
            response = tarifPortType.filtereProdukte(parameters);
        } catch (FiltereProdukteBusinessFaultMessage filtereProdukteBusinessFaultMessage) {
            filtereProdukteBusinessFaultMessage.printStackTrace();
        } catch (FiltereProdukteSystemFaultMessage filtereProdukteSystemFaultMessage) {
            filtereProdukteSystemFaultMessage.printStackTrace();
        }
        return response;

    }
    // end::implementPortTypeMethod[]

}
