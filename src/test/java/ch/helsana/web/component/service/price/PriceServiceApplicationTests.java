package ch.helsana.web.component.service.price;

import ch.helsana.web.component.service.price.service.PriceService;
import ch.sbi.services.system.productengine.tarif.v2.BerechnePraemieBusinessFaultMessage;
import ch.sbi.services.system.productengine.tarif.v2.BerechnePraemieSystemFaultMessage;
import ch.sbi.services.system.productengine.tarif.v2.berechnepraemierequest.BerechnePraemieRequest;
import ch.sbi.services.system.productengine.tarif.v2.berechnepraemierequest.Person;
import ch.sbi.services.system.productengine.tarif.v2.berechnepraemierequest.PersonListType;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@ActiveProfiles("junit")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PriceServiceApplication.class)
@WebAppConfiguration
@Ignore
public class PriceServiceApplicationTests {


	@Autowired
	PriceService service;


	@Test
	public void contextLoads() throws BerechnePraemieBusinessFaultMessage, BerechnePraemieSystemFaultMessage {
		BerechnePraemieRequest request = new BerechnePraemieRequest();
		request.setCorrelationId("42342");
		PersonListType personListType = new PersonListType();
		Person person = new Person();
		person.setGeschlecht("m");
		personListType.getPerson().add(person);
		request.setPersonList(personListType);
		service.berechnePraemie(request);

	}

}


