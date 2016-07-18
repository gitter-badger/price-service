package ch.keepcalm.web.component.price.service;

import ch.helsana.services.spezialfunktionen.tarif.v2.BerechneBesterPreisBusinessFaultMessage;
import ch.helsana.services.spezialfunktionen.tarif.v2.BerechneBesterPreisSystemFaultMessage;
import ch.helsana.services.spezialfunktionen.tarif.v2.BerechnePraemieBusinessFaultMessage;
import ch.helsana.services.spezialfunktionen.tarif.v2.BerechnePraemieSystemFaultMessage;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnebesterpreisrequest.BerechneBesterPreisRequest;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnebesterpreisresponse.BerechneBesterPreisResponse;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemierequest.BerechnePraemieRequest;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemieresponse.BerechnePraemieResponse;
import ch.keepcalm.web.component.price.PriceServiceApplication;
import ch.keepcalm.web.component.price.config.WebServiceJAXWSConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.Assert.fail;

/**
 * Integration test.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = {"junit-integration"})
@SpringApplicationConfiguration(classes = {PriceServiceApplication.class, WebServiceJAXWSConfig.class})
@WebAppConfiguration
@IntegrationTest
public class PriceServiceIntegrationTest {

    @Autowired
    PriceService priceService;

    @Test
    public void berechnePraemieTest() throws BerechnePraemieSystemFaultMessage, BerechnePraemieBusinessFaultMessage {
        BerechnePraemieRequest request = ServiceRequestHelper.berechnePraemieRequest("priceRequest-PRO_P0BEPH_HEL_IG.json");
        BerechnePraemieResponse response = priceService.berechnePraemie(request);
        // TODO: 08.07.2016 Check better soulution
        Assert.assertEquals(new BigInteger("288"), response.getPreis().getBruttoPreis().toBigInteger());
    }

    /**
     * Test System Excpetion.
     * @throws Exception
     */
    @Test(expected = ch.helsana.services.spezialfunktionen.tarif.v2.BerechnePraemieSystemFaultMessage.class)
    public void priceSystemException() throws Exception {
        priceService.berechnePraemie(null);
        fail(); // if the expected don't come from the backend test will fail.
    }




    @Test
    public void berechneBesterPreisTest() throws BerechneBesterPreisBusinessFaultMessage, BerechneBesterPreisSystemFaultMessage {
        BerechneBesterPreisRequest request = ServiceRequestHelper.berechneBesterPreisRequest("bestPriceRequest-PRO_x0BENE_HEL_IG.json");
        BerechneBesterPreisResponse response = priceService.berechneBesterPreis(request);
        // TODO: 08.07.2016 Check better soulution
        Assert.assertEquals(new BigDecimal("466.00"), response.getProduktList().getProdukt().get(1).getPreis().getNettoPreis());
    }


}
