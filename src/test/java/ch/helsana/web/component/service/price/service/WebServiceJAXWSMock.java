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
import org.springframework.boot.test.IntegrationTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import javax.jws.WebMethod;
import javax.jws.WebService;

@Component("WebServiceJAXWSMock")
@WebService(serviceName = "Tarif", portName = "TarifPort", targetNamespace = "http://services.sbi.ch/system/productengine/tarif/v2"
        , endpointInterface = "ch.sbi.services.system.productengine.tarif.v2.TarifPortType")
@ActiveProfiles("junit")
@IntegrationTest
public class WebServiceJAXWSMock implements TarifPortType {

    @WebMethod
    @Override
    public BerechneBesterPreisResponse berechneBesterPreis(BerechneBesterPreisRequest parameters) throws BerechneMarkenPreiseBusinessFaultMessage, BerechneMarkenPreiseSystemFaultMessage {
        BerechneBesterPreisResponse response = new BerechneBesterPreisResponse();
        return response;
    }

    @WebMethod
    @Override
    public BerechnePraemieResponse berechnePraemie(BerechnePraemieRequest parameters) throws BerechnePraemieBusinessFaultMessage, BerechnePraemieSystemFaultMessage {
        BerechnePraemieResponse response = new BerechnePraemieResponse();
        return response;
    }

    @WebMethod
    @Override
    public FiltereProdukteResponse filtereProdukte(FiltereProdukteRequest parameters) throws FiltereProdukteBusinessFaultMessage, FiltereProdukteSystemFaultMessage {
        FiltereProdukteResponse response = new FiltereProdukteResponse();
        return response;
    }

}