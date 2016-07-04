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
// end::serviceTag[]

    // tag::portType[]
    @Resource
    TarifPortType tarifPortType;
    // end::portType[]

    // tag::implementPortTypeMethod[]
   /* @Override
    public UebermittleAntragResponse uebermittleAntrag(UebermittleAntragRequest uebermittleAntragRequest) throws UebermittleAntragBusinessFaultMessage, UebermittleAntragSystemFaultMessage {
        return (UebermittleAntragResponse) antragPortType.uebermittleAntrag(uebermittleAntragRequest);
    }
*/
    @Override
    public BerechneBesterPreisResponse berechneBesterPreis(BerechneBesterPreisRequest parameters) throws BerechneMarkenPreiseBusinessFaultMessage, BerechneMarkenPreiseSystemFaultMessage {
        return null;
    }

    @Override
    public BerechnePraemieResponse berechnePraemie(BerechnePraemieRequest parameters) throws BerechnePraemieBusinessFaultMessage, BerechnePraemieSystemFaultMessage {
        return null;
    }

    @Override
    public FiltereProdukteResponse filtereProdukte(FiltereProdukteRequest parameters) throws FiltereProdukteBusinessFaultMessage, FiltereProdukteSystemFaultMessage {
        return null;
    }
    // end::implementPortTypeMethod[]

}
// end::PriceService[]
