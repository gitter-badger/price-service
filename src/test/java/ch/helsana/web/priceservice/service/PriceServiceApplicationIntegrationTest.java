package ch.helsana.web.priceservice.service;

import ch.helsana.services.spezialfunktionen.tarif.v2.BerechnePraemieBusinessFaultMessage;
import ch.helsana.services.spezialfunktionen.tarif.v2.BerechnePraemieSystemFaultMessage;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemierequest.BerechnePraemieRequest;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemieresponse.BerechnePraemieResponse;
import ch.helsana.web.priceservice.PriceServiceApplication;
import ch.helsana.web.priceservice.config.WebServiceJAXWSConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigInteger;

/**
 * Integration test.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = {"junit-integration"})
@SpringApplicationConfiguration(classes = {PriceServiceApplication.class, WebServiceJAXWSConfig.class})
@WebAppConfiguration
@IntegrationTest
public class PriceServiceApplicationIntegrationTest {

    @Autowired
    PriceService priceService;

    @Test
    public void berechnePraemieTest() throws BerechnePraemieSystemFaultMessage, BerechnePraemieBusinessFaultMessage {
        BerechnePraemieRequest request = ServiceRequestHelper.berechnePraemieRequest("priceRequest-PRO_P0BEPH_HEL_IG.json");
        BerechnePraemieResponse response = priceService.berechnePraemie(request);
        // TODO: 08.07.2016 Check better soulution
        Assert.assertEquals(new BigInteger("288"), response.getPreis().getBruttoPreis().toBigInteger());

    }

}
