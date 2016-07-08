package ch.helsana.web.priceservice.service;

import ch.helsana.services.spezialfunktionen.tarif.v2.BerechnePraemieBusinessFaultMessage;
import ch.helsana.services.spezialfunktionen.tarif.v2.BerechnePraemieSystemFaultMessage;
import ch.helsana.services.spezialfunktionen.tarif.v2.TarifPortType;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemiefaults.BerechnePraemieBusinessFault;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemiefaults.BerechnePraemieSystemFault;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemierequest.BerechnePraemieRequest;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemieresponse.BerechnePraemieResponse;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemieresponse.Preis;
import ch.helsana.web.priceservice.PriceServiceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by hkesq on 07.07.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {PriceServiceApplication.class})
public class PriceServiceTest {

    private PriceService priceService;
    @Autowired
    public void setPriceService(PriceService priceService) {
        this.priceService = priceService;
    }


    @Test
    public void berechnePraemie() throws Exception {
        // setup
        BerechnePraemieRequest request = ServiceRequestHelper.berechnePraemieRequest();
        TarifPortType mockService = mock(TarifPortType.class); //http://mockito.org/

        BerechnePraemieResponse response = new BerechnePraemieResponse();
        Preis preis = new Preis();
        preis.setNettoPreis(new BigDecimal("22.22"));
        response.setPreis(preis);
        // set Mock in Service // TODO: 07.07.2016 search for a better solution to remove the package private setter method
        ReflectionTestUtils.setField(priceService, PriceService.class, "tarifPortType", mockService, TarifPortType.class);

        //stubbing
        when(mockService.berechnePraemie(request)).thenReturn(response);

        // run
        BerechnePraemieResponse serviceResponse = priceService.berechnePraemie(request);
        assertEquals(preis.getNettoPreis(), serviceResponse.getPreis().getNettoPreis());

    }




    @Test(expected = ch.helsana.services.spezialfunktionen.tarif.v2.BerechnePraemieSystemFaultMessage.class)
    public void priceSystemException() throws Exception {
        // setup
        BerechnePraemieRequest request = ServiceRequestHelper.berechnePraemieRequest();
        TarifPortType mockService = mock(TarifPortType.class); //http://mockito.org/
        // set Mock in Service
        ReflectionTestUtils.setField(priceService, PriceService.class, "tarifPortType", mockService, TarifPortType.class);
        //stubbing
        BerechnePraemieSystemFault systemFault = new BerechnePraemieSystemFault();
        systemFault.setCorrelationId("123412412412424213");

        when(mockService.berechnePraemie(null)).thenThrow(new BerechnePraemieSystemFaultMessage("System Fault: From Mock Service", systemFault ));

        // run
        priceService.berechnePraemie(null);
    }

    @Test(expected = ch.helsana.services.spezialfunktionen.tarif.v2.BerechnePraemieBusinessFaultMessage.class)
    public void priceBusinessException() throws Exception {
        // setup
        BerechnePraemieRequest request = ServiceRequestHelper.berechnePraemieRequest();
        TarifPortType mockService = mock(TarifPortType.class); //http://mockito.org/
        // set Mock in Service
        ReflectionTestUtils.setField(priceService, PriceService.class, "tarifPortType", mockService, TarifPortType.class);
        //stubbing
        BerechnePraemieBusinessFault systemFault = new BerechnePraemieBusinessFault();
        systemFault.setCorrelationId("321654987");

        when(mockService.berechnePraemie(null)).thenThrow(new BerechnePraemieBusinessFaultMessage("Business Fault: From Mock Service", systemFault ));

        // run
        BerechnePraemieResponse berechnePraemieResponse = priceService.berechnePraemie(null);
        System.out.println(berechnePraemieResponse);

    }

}