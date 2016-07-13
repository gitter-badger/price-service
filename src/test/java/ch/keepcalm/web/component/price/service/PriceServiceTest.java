package ch.keepcalm.web.component.price.service;

/**
 * Created by hkesq on 13.07.2016.
 */

import ch.helsana.services.spezialfunktionen.tarif.v2.BerechnePraemieBusinessFaultMessage;
import ch.helsana.services.spezialfunktionen.tarif.v2.BerechnePraemieSystemFaultMessage;
import ch.helsana.services.spezialfunktionen.tarif.v2.TarifPortType;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemiefaults.BerechnePraemieBusinessFault;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemiefaults.BerechnePraemieSystemFault;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemierequest.BerechnePraemieRequest;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemieresponse.BerechnePraemieResponse;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemieresponse.Preis;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Marcel Widmer on 07.07.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class PriceServiceTest {

    @Mock
    TarifPortType mockService;

    @InjectMocks
    private PriceService priceService;


    /**
     * Test calcutate price
     * @throws Exception
     */
    @Test
    public void price() throws Exception {
        // setup
        BerechnePraemieRequest request = ServiceRequestHelper.berechnePraemieRequest();

        BerechnePraemieResponse response = new BerechnePraemieResponse();
        Preis preis = new Preis();
        preis.setNettoPreis(new BigDecimal("22.22"));
        response.setPreis(preis);

        //stubbing
        when(mockService.berechnePraemie(request)).thenReturn(response);

        // run
        BerechnePraemieResponse serviceResponse = priceService.berechnePraemie(request);
        assertEquals(new BigDecimal("22.22"), serviceResponse.getPreis().getNettoPreis());

    }


    /**
     * Test System Excpetion.
     * @throws Exception
     */
    @Test(expected = ch.helsana.services.spezialfunktionen.tarif.v2.BerechnePraemieSystemFaultMessage.class)
    public void priceSystemException() throws Exception {
        // setup
        TarifPortType mockService = mock(TarifPortType.class); //http://mockito.org/
        // set Mock in Service
        ReflectionTestUtils.setField(priceService, PriceService.class, "tarifPortType", mockService, TarifPortType.class);
        //stubbing
        BerechnePraemieSystemFault systemFault = new BerechnePraemieSystemFault();
        systemFault.setCorrelationId("123412412412424213");

        when(mockService.berechnePraemie(null)).thenThrow(new BerechnePraemieSystemFaultMessage("System Fault: From Mock Service", systemFault ));

        // run
        priceService.berechnePraemie(null);
        fail(); // if the expected don't come from the backend test will fail.
    }



    /**
     * Test Business Exception
     * @throws Exception
     */
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
        fail(); // if the expected don't come from the backend test will fail.
    }

}