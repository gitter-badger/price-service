package ch.helsana.web.component.service.price.service;


import ch.helsana.web.component.service.price.PriceServiceApplication;
import ch.helsana.web.component.service.price.config.WebServiceJAXWSConfig;
import ch.sbi.services.system.productengine.tarif.v2.BerechnePraemieBusinessFaultMessage;
import ch.sbi.services.system.productengine.tarif.v2.BerechnePraemieSystemFaultMessage;
import ch.sbi.services.system.productengine.tarif.v2.berechnepraemierequest.BerechnePraemieRequest;
import ch.sbi.services.system.productengine.tarif.v2.berechnepraemieresponse.BerechnePraemieResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.remoting.jaxws.JaxWsPortProxyFactoryBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {PriceServiceApplication.class, WebServiceJAXWSConfig.class})
@WebAppConfiguration
@IntegrationTest
public class PriceServiceApplicationIntegrationTest {


    @Autowired // this is the mock of SPF SOAP required by the application tests to run.
    @Qualifier("tarifPortType")
    JaxWsPortProxyFactoryBean tarifPortType;

    @Autowired
    PriceService priceService;

    @Test
    public void berechnePraemieTest() throws BerechnePraemieBusinessFaultMessage, BerechnePraemieSystemFaultMessage {
        BerechnePraemieRequest request = ServiceRequestHelper.berechnePraemieRequest();
        BerechnePraemieResponse response = priceService.berechnePraemie(request);
        System.out.println(response.getPreis().getBruttoPreis());
    }

}
