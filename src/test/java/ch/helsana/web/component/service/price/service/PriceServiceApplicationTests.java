package ch.helsana.web.component.service.price.service;


import ch.helsana.services.spezialfunktionen.tarif.v2.BerechnePraemieBusinessFaultMessage;
import ch.helsana.services.spezialfunktionen.tarif.v2.BerechnePraemieSystemFaultMessage;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemierequest.BerechnePraemieRequest;
import ch.helsana.services.spezialfunktionen.tarif.v2.berechnepraemieresponse.BerechnePraemieResponse;
import ch.helsana.web.component.service.price.PriceServiceApplication;
import ch.helsana.web.component.service.price.config.WebServiceJAXWSConfig;
import ch.helsana.web.component.service.price.config.WebServiceJAXWSTestConfig;
import ch.helsana.web.component.service.price.exception.BusinessException;
import ch.helsana.web.component.service.price.exception.SystemException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.remoting.jaxws.SimpleJaxWsServiceExporter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@Ignore
@ActiveProfiles(profiles={"junit"})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {PriceServiceApplication.class, WebServiceJAXWSConfig.class, WebServiceJAXWSTestConfig.class})
@WebAppConfiguration
@IntegrationTest
public class PriceServiceApplicationTests {


    @Autowired
    @Qualifier("tarifPortTypeTest")
    public SimpleJaxWsServiceExporter webServiceMock;

    @Autowired
    PriceService priceService;

    @Test
    public void berechnePraemieTest() throws BerechnePraemieSystemFaultMessage, BerechnePraemieBusinessFaultMessage {
        BerechnePraemieRequest request = ServiceRequestHelper.berechnePraemieRequest();
        BerechnePraemieResponse response = priceService.berechnePraemie(request);
        System.out.println(response.getPreis().getBruttoPreis());
    }




}

