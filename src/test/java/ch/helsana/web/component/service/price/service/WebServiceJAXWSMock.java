package ch.helsana.web.component.service.price.service;

import ch.helsana.services.spezialfunktionen.tarif.v2.TarifPortType;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnebesterpreisrequest.BerechneBesterPreisRequest;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnebesterpreisresponse.BerechneBesterPreisResponse;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemierequest.BerechnePraemieRequest;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemieresponse.BerechnePraemieResponse;
import ch.helsana.services.spezialfunktionen.tarif.v2.filtereprodukterequest.FiltereProdukteRequest;
import ch.helsana.services.spezialfunktionen.tarif.v2.filtereprodukteresponse.FiltereProdukteResponse;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import javax.jws.WebMethod;
import javax.jws.WebService;

@Component("WebServiceJAXWSMock")
@WebService//(serviceName = "TarifPortType", portName = "TarifPort", targetNamespace = "http://services.sbi.ch/system/productengine/tarif/v2" ,
  //      endpointInterface = "ch.sbi.services.system.productengine.tarif.v2.TarifPortType")
@ActiveProfiles("junit")
@IntegrationTest
public class WebServiceJAXWSMock implements TarifPortType {

    @WebMethod
    @Override
    public BerechneBesterPreisResponse berechneBesterPreis(BerechneBesterPreisRequest parameters) {
        BerechneBesterPreisResponse response = new BerechneBesterPreisResponse();
        return response;
    }

    @WebMethod
    @Override
    public BerechnePraemieResponse berechnePraemie(BerechnePraemieRequest parameters)  {
        BerechnePraemieResponse response = new BerechnePraemieResponse();
        return response;
    }

    @WebMethod
    @Override
    public FiltereProdukteResponse filtereProdukte(FiltereProdukteRequest parameters)  {
        FiltereProdukteResponse response = new FiltereProdukteResponse();
        return response;
    }

}