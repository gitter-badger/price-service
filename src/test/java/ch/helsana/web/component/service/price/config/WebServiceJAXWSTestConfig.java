package ch.helsana.web.component.service.price.config;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.jaxws.SimpleJaxWsServiceExporter;


/**
 * Created by panter on 06/01/16.
 */
public class WebServiceJAXWSTestConfig {

    @Value("${soap.endpoint.url}")
    public String ENDPOINT_URL;

    @Bean(name = "tarifPortTypeTest", autowire = Autowire.BY_NAME)
    public SimpleJaxWsServiceExporter jaxWsServiceExporter() {
        SimpleJaxWsServiceExporter exporter = new SimpleJaxWsServiceExporter();
        exporter.setBaseAddress(ENDPOINT_URL + "/");//Works only if the jax-ws runtime supports publishing of endpoints with an address.
        return exporter;
    }
}
